package com.example.chatapp.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.chatapp.R
import de.hdodenhof.circleimageview.CircleImageView

class StatusSquareImageView : CircleImageView {
    private var isActivation = false
    private var isVisibleBorder = false
    private var radiusActivation = 1
    private var borderActivation = 1
    private var colorActivation = Color.GREEN
    private var colorBorderActivation = Color.GREEN
    private var radioActivation = 45

    constructor(context: Context?) : super(context){
        inits()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        inits(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ){
        inits(attrs)
    }



    private fun inits(attrs: AttributeSet? = null){
        isActivation = false
        isVisibleBorder = false
        radiusActivation = context!!.resources.getDimensionPixelOffset(R.dimen.default_radius_activation)
        borderActivation = context!!.resources.getDimensionPixelOffset(R.dimen.default_size_border_activation)
        colorActivation = ContextCompat.getColor(context!!, R.color.default_color_activation)
        colorBorderActivation = ContextCompat.getColor(context!!, R.color.white)
        radioActivation = 45
        if (attrs != null){
            val style = context.obtainStyledAttributes(attrs, R.styleable.StatusSquareImageView, 0, 0)
            isActivation = style.getBoolean(R.styleable.StatusSquareImageView_isActivation, isActivation)
            isVisibleBorder = style.getBoolean(R.styleable.StatusSquareImageView_visibleBorderActivation, isVisibleBorder)
            radiusActivation = style.getDimensionPixelOffset(R.styleable.StatusSquareImageView_radiusActivation,radiusActivation)
            borderActivation = style.getDimensionPixelOffset(R.styleable.StatusSquareImageView_sizeBorderActivation,borderActivation)
            colorActivation = style.getColor(R.styleable.StatusSquareImageView_colorActivation,colorActivation)
            colorBorderActivation = style.getColor(R.styleable.StatusSquareImageView_colorBorderActivation,colorBorderActivation)
            radioActivation = style.getInt(R.styleable.StatusSquareImageView_radioActivation,radioActivation)
            style.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isActivation){
            val p = Paint()
            p.style = Paint.Style.FILL
            p.isAntiAlias=true
            canvas?.rotate(radioActivation.toFloat(), width.toFloat()/2, height.toFloat()/2)
            p.color = colorActivation
            canvas?.drawCircle((width).toFloat()/2,
                0.0f, radiusActivation.toFloat(), p)
            if (isVisibleBorder){
                p.style = Paint.Style.STROKE
                p.strokeWidth = borderActivation.toFloat()
                p.color = colorBorderActivation
                canvas?.drawCircle((width).toFloat()/2,
                    0.0f, radiusActivation.toFloat(), p)
            }
            canvas?.rotate(-radioActivation.toFloat(), width.toFloat()/2, height.toFloat()/2)
        }
    }

}