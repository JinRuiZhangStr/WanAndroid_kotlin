package com.zjrdev.wanandroid.view.circleImageview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView.ScaleType
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import com.zjrdev.wanandroid.R

/**
 *Created by 张金瑞.
 *Data: 2020-12-22
 * 圆形Imageview
 */
private val SCALE_TYPE: ScaleType? = ScaleType.CENTER_CROP

private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
private const val COLORDRAWABLE_DIMENSION = 2

private const val DEFAULT_BORDER_WIDTH = 0
private const val DEFAULT_BORDER_COLOR = Color.BLACK
private const val DEFAULT_CIRCLE_BACKGROUND_COLOR = Color.TRANSPARENT
private const val DEFAULT_IMAGE_ALPHA = 255
private const val DEFAULT_BORDER_OVERLAY = false
class CircleImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defstyle: Int) :
    AppCompatImageView(context, attrs,defstyle) {

    private val mDrawableRect = RectF()
    private val mBorderRect = RectF()

    private val mShaderMatrix = Matrix()
    private val mBitmapPaint = Paint()
    private val mBorderPaint = Paint()
    private val mCircleBackgroundPaint = Paint()
    private var mBorderColor: Int = DEFAULT_BORDER_COLOR
    private var mBorderWidth: Int = DEFAULT_BORDER_WIDTH
    private var mCircleBackgroundColor: Int = DEFAULT_CIRCLE_BACKGROUND_COLOR
    private var mImageAlpha: Int = DEFAULT_IMAGE_ALPHA

    private lateinit var mBitmap: Bitmap
    private lateinit var mBitmapCanvas: Canvas
    private var mDrawableRadius = 0.0f
    private var mBorderRadius = 0.0f

    private lateinit var mColorFilter: ColorFilter
    private var mInitialized = false
    private var mRebuildShader = false
    private var mDrawableDirty = false

    private var mBorderOverlay =  false
    private var mDisableCircularTransformation = false

    init {
        val obtainStyledAttributes = context.obtainStyledAttributes(attrs,R.styleable.CircleImageView)
        mBorderColor = obtainStyledAttributes.getColor(R.styleable.CircleImageView_civ_border_color,
            DEFAULT_BORDER_COLOR)
        mBorderWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width,
            DEFAULT_BORDER_WIDTH)
        mBorderOverlay = obtainStyledAttributes.getBoolean(R.styleable.CircleImageView_civ_border_overlay,
            DEFAULT_BORDER_OVERLAY)
        mCircleBackgroundColor = obtainStyledAttributes.getColor(R.styleable.CircleImageView_civ_circle_background_color,
            DEFAULT_CIRCLE_BACKGROUND_COLOR)
        obtainStyledAttributes.recycle()

        mInitialized = true
        super.setScaleType(SCALE_TYPE)
        mBitmapPaint?.let {
            it.isAntiAlias = true
            it.isDither = true
            it.isFilterBitmap = true
            it.alpha = mImageAlpha
            it.colorFilter = colorFilter
        }

        mBorderPaint?.let {
            it.style = Paint.Style.STROKE
            it.isAntiAlias = true
            it.color = mBorderColor
            it.strokeWidth = mBorderWidth.toFloat()
        }

        mCircleBackgroundPaint?.let {
            it.style = Paint.Style.STROKE
            it.isAntiAlias= true
            it.color = mCircleBackgroundColor
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = OutLineProvider()
        }

    }

    override fun setScaleType(scaleType: ScaleType?) {
        if (scaleType != SCALE_TYPE) {
            throw IllegalArgumentException(
                String.format(
                    "ScaleType %s not supported.",
                    scaleType
                )
            )
        }
    }

    @SuppressLint("CanvasSize")
    override fun onDraw(canvas: Canvas) {
        if (mDisableCircularTransformation) {
            super.onDraw(canvas)
            return
        }
        if (mCircleBackgroundColor != Color.TRANSPARENT) {
            canvas.drawCircle(
                mDrawableRect.centerX(),
                mDrawableRect.centerY(),
                mDrawableRadius,
                mCircleBackgroundPaint
            )
        }
        if (mBitmap != null) {
            if (mDrawableDirty && mBitmapCanvas != null) {
                mDrawableDirty = false
                val drawable = drawable
                drawable.setBounds(0, 0, mBitmapCanvas.width, mBitmapCanvas.height)
                drawable.draw(mBitmapCanvas)
            }
            if (mRebuildShader) {
                mRebuildShader = false
                val bitmapShader =
                    BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                bitmapShader.setLocalMatrix(mShaderMatrix)
                mBitmapPaint.shader = bitmapShader
            }
            canvas.drawCircle(
                mDrawableRect.centerX(),
                mDrawableRect.centerY(),
                mDrawableRadius,
                mBitmapPaint
            )
        }
        if (mBorderWidth > 0) {
            canvas.drawCircle(
                mBorderRect.centerX(),
                mBorderRect.centerY(),
                mBorderRadius,
                mBorderPaint
            )
        }
    }

    override fun invalidateDrawable(dr: Drawable) {
        mDrawableDirty = true
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateDimensions()
        invalidate()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        updateDimensions()
        invalidate()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start, top, end, bottom)
        updateDimensions()
        invalidate()
    }

    fun getBorderColor(): Int {
        return mBorderColor
    }

    fun setBorderColor(@ColorInt borderColor: Int) {
        if (borderColor == mBorderColor) {
            return
        }
        mBorderColor = borderColor
        mBorderPaint.color = borderColor
        invalidate()
    }

    fun getCircleBackgroundColor(): Int {
        return mCircleBackgroundColor
    }

    fun setCircleBackgroundColor(@ColorInt circleBackgroundColor: Int) {
        if (circleBackgroundColor == mCircleBackgroundColor) {
            return
        }
        mCircleBackgroundColor = circleBackgroundColor
        mCircleBackgroundPaint.color = circleBackgroundColor
        invalidate()
    }


    @Deprecated("Use {@link #setCircleBackgroundColor(int)} instead")
    fun setCircleBackgroundColorResource(@ColorRes circleBackgroundRes: Int) {
        setCircleBackgroundColor(context.resources.getColor(circleBackgroundRes))
    }

    fun getBorderWidth(): Int {
        return mBorderWidth
    }

    fun setBorderWidth(borderWidth: Int) {
        if (borderWidth == mBorderWidth) {
            return
        }
        mBorderWidth = borderWidth
        mBorderPaint.strokeWidth = borderWidth.toFloat()
        updateDimensions()
        invalidate()
    }

    fun isBorderOverlay(): Boolean {
        return mBorderOverlay
    }

    fun setBorderOverlay(borderOverlay: Boolean) {
        if (borderOverlay == mBorderOverlay) {
            return
        }
        mBorderOverlay = borderOverlay
        updateDimensions()
        invalidate()
    }

    fun isDisableCircularTransformation(): Boolean {
        return mDisableCircularTransformation
    }



    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        initializeBitmap()
        invalidate()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
        invalidate()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
        invalidate()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        initializeBitmap()
        invalidate()
    }

    override fun setImageAlpha(alpha: Int) {
        var alpha = alpha
        alpha = alpha and 0xFF
        if (alpha == mImageAlpha) {
            return
        }
        mImageAlpha = alpha

        // This might be called during ImageView construction before
        // member initialization has finished on API level >= 16.
        if (mInitialized) {
            mBitmapPaint.alpha = alpha
            invalidate()
        }
    }

    override fun getImageAlpha(): Int {
        return mImageAlpha
    }

    override fun setColorFilter(cf: ColorFilter) {
        if (cf === mColorFilter) {
            return
        }
        mColorFilter = cf

        // This might be called during ImageView construction before
        // member initialization has finished on API level <= 19.
        if (mInitialized) {
            mBitmapPaint.colorFilter = cf
            invalidate()
        }
    }

    override fun getColorFilter(): ColorFilter? {
        return mColorFilter
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else try {
            val bitmap: Bitmap
            bitmap = if (drawable is ColorDrawable) {
                Bitmap.createBitmap(
                    COLORDRAWABLE_DIMENSION,
                    COLORDRAWABLE_DIMENSION,
                    BITMAP_CONFIG
                )
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    BITMAP_CONFIG
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun initializeBitmap() {
        mBitmap = getBitmapFromDrawable(drawable)!!
        if (mBitmap != null && mBitmap.isMutable) {
            mBitmapCanvas = Canvas(mBitmap)
        }
        if (!mInitialized) {
            return
        }
        if (mBitmap != null) {
            updateShaderMatrix()
        } else {
            mBitmapPaint.shader = null
        }
    }

    private fun updateDimensions() {
        mBorderRect.set(calculateBounds())
        mBorderRadius = Math.min(
            (mBorderRect.height() - mBorderWidth) / 2.0f,
            (mBorderRect.width() - mBorderWidth) / 2.0f
        )
        mDrawableRect.set(mBorderRect)
        if (!mBorderOverlay && mBorderWidth > 0) {
            mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f)
        }
        mDrawableRadius =
            Math.min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f)
        updateShaderMatrix()
    }

    private fun calculateBounds(): RectF? {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom
        val sideLength = Math.min(availableWidth, availableHeight)
        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f
        return RectF(left, top, left + sideLength, top + sideLength)
    }

    private fun updateShaderMatrix() {
        if (mBitmap == null) {
            return
        }
        val scale: Float
        var dx = 0f
        var dy = 0f
        mShaderMatrix.set(null)
        val bitmapHeight = mBitmap.height
        val bitmapWidth = mBitmap.width
        if (bitmapWidth * mDrawableRect.height() > mDrawableRect.width() * bitmapHeight) {
            scale = mDrawableRect.height() / bitmapHeight.toFloat()
            dx = (mDrawableRect.width() - bitmapWidth * scale) * 0.5f
        } else {
            scale = mDrawableRect.width() / bitmapWidth.toFloat()
            dy = (mDrawableRect.height() - bitmapHeight * scale) * 0.5f
        }
        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate(
            (dx + 0.5f).toInt() + mDrawableRect.left,
            (dy + 0.5f).toInt() + mDrawableRect.top
        )
        mRebuildShader = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDisableCircularTransformation) {
            super.onTouchEvent(event)
        } else inTouchableArea(event.x, event.y) && super.onTouchEvent(event)
    }

    private fun inTouchableArea(x: Float, y: Float): Boolean {
        return if (mBorderRect.isEmpty) {
            true
        } else Math.pow(
            x - mBorderRect.centerX().toDouble(),
            2.0
        ) + Math.pow(y - mBorderRect.centerY().toDouble(), 2.0) <= Math.pow(
            mBorderRadius.toDouble(),
            2.0
        )
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private inner class OutLineProvider: ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            if (mDisableCircularTransformation) {
                ViewOutlineProvider.BACKGROUND.getOutline(view,outline)
            } else {
                val rect = Rect()
                mBorderRect.roundOut(rect)
                outline?.let {
                    it.setRoundRect(rect,rect.width()/2.0f)
                }
            }
        }

    }
}