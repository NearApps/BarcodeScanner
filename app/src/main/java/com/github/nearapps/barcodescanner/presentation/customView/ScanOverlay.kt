package com.github.nearapps.barcodescanner.presentation.customView

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toRect
import com.github.nearapps.barcodescanner.R

class ScanOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        const val RATIO = 0.7f
    }

    private val viewfinderRadius: Float

    private val backgroundPaint: Paint

    private val viewfinderPaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    private val viewfinderCornerPaint: Paint

    private val viewfinderRect: RectF = RectF()

    fun getViewfinderRect(): Rect = viewfinderRect.toRect()

    init{
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        context.theme.obtainStyledAttributes(attrs, R.styleable.ScanOverlay, defStyleAttr, defStyleRes).apply {
            try {
                viewfinderRadius = getDimension(R.styleable.ScanOverlay_viewfinder_radius, getDP(40f))
                viewfinderCornerPaint = Paint(ANTI_ALIAS_FLAG).apply {
                    color = getColor(R.styleable.ScanOverlay_viewfinder_corner_color, Color.WHITE)
                    style = Paint.Style.STROKE
                    strokeWidth = getDimension(R.styleable.ScanOverlay_viewfinder_corner_thickness, getDP(2f))
                }
                backgroundPaint = Paint().apply {
                    color = getColor(R.styleable.ScanOverlay_overlay_mask_color, Color.parseColor("#80000000"))
                    style = Paint.Style.FILL
                }
            } finally {
                recycle()
            }
        }
    }

    private fun getDP(value: Float): Float = value * Resources.getSystem().displayMetrics.density

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        calculateRectangleDimension(measuredWidth, measuredHeight)
    }

    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Background
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

        // Viewfinder
        canvas.drawRoundRect(viewfinderRect, viewfinderRadius, viewfinderRadius, viewfinderPaint)

        // Corner
        val cornerSize = viewfinderRadius * 2f

        // Top Left
        canvas.drawArc(viewfinderRect.left, viewfinderRect.top, viewfinderRect.left+cornerSize, viewfinderRect.top+cornerSize, -90f, -90f, false, viewfinderCornerPaint)

        //Top Right
        canvas.drawArc(viewfinderRect.right-cornerSize, viewfinderRect.top, viewfinderRect.right, viewfinderRect.top+cornerSize, -90f, 90f, false, viewfinderCornerPaint)

        // Bottom Right
        canvas.drawArc(viewfinderRect.right-cornerSize, viewfinderRect.bottom-cornerSize, viewfinderRect.right, viewfinderRect.bottom, 90f, -90f, false, viewfinderCornerPaint)

        // Bottom Left
        canvas.drawArc(viewfinderRect.left, viewfinderRect.bottom-cornerSize, viewfinderRect.left+cornerSize, viewfinderRect.bottom, 90f, 90f, false, viewfinderCornerPaint)
    }

    private fun calculateRectangleDimension(width: Int, height: Int) {
        val overlayWidth = width.toFloat()
        val overlayHeight = height.toFloat()

        val viewfinderSize = overlayHeight.coerceAtMost(overlayWidth) * RATIO

        val centerX = overlayWidth / 2f
        val centerY = overlayHeight / 2f

        val left = centerX - viewfinderSize / 2f
        val right = centerX + viewfinderSize / 2f
        val top = centerY - viewfinderSize / 2f
        val bottom = centerY + viewfinderSize / 2f

        viewfinderRect.set(left, top, right, bottom)
    }
}