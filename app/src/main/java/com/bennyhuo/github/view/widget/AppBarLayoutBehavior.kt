package com.bennyhuo.github.view.widget

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import com.bennyhuo.github.R

/***
 * 今天的任务
 * 1 点击事件不好用 的处理
 * 2 当前的这个类的的代码
 */
class AppBarLayoutBehavior(context: Context, attr: AttributeSet?) : CoordinatorLayout.Behavior<View>() {
    companion object {
        const val invaild_value = 0
    }

    private var targetTop: Int
    private var targetLeft: Int
    private var originalTop: Int
    private var originalLeft: Int

    private var targetHeight: Int = invaild_value
    private var targetWidth: Int = invaild_value
    private var originalHeight: Int = invaild_value
    private var originalWidth: Int = invaild_value

    private var totalOffsetX = invaild_value
    private var totalOffsetY = invaild_value

    private var offsetRadio = 0f


    init {
        if (attr == null) {
            originalLeft = invaild_value
            originalTop = invaild_value
            targetLeft = invaild_value
            targetTop = invaild_value
        } else {
            val a = context.obtainStyledAttributes(attr, R.styleable.AppBarLayoutBehavior)
            targetTop = a.getDimensionPixelSize(R.styleable.AppBarLayoutBehavior_targetTop, invaild_value)
            targetLeft = a.getDimensionPixelSize(R.styleable.AppBarLayoutBehavior_targetLeft, invaild_value)
            originalTop = a.getDimensionPixelSize(R.styleable.AppBarLayoutBehavior_originalTop, invaild_value)
            originalLeft = a.getDimensionPixelSize(R.styleable.AppBarLayoutBehavior_originalLeft, invaild_value)
            a.recycle()
        }
    }

    private fun initializeProperies(childe: View, appBarLayout: AppBarLayout) {

        if (targetHeight != invaild_value || childe.height == 0) return
        targetHeight = appBarLayout.height - appBarLayout.totalScrollRange - targetTop * 2
        targetWidth = childe.width * targetHeight / childe.height

        originalWidth = childe.width
        originalHeight = childe.height

        if (originalLeft == invaild_value) {
            originalLeft = childe.x.toInt()
        } else {
            childe.x = originalLeft.toFloat()
        }
        if (originalTop == invaild_value) {
            originalTop = childe.y.toInt()
        } else {
            childe.y = originalTop.toFloat()
        }

        if (targetLeft == invaild_value) {
            targetLeft = (originalLeft + (originalWidth - targetWidth) * childe.pivotX / originalWidth).toInt()
        }
        if (targetTop == invaild_value) {
            targetTop = (originalTop + (originalHeight - targetHeight) * childe.pivotY / originalHeight).toInt()
        }
        totalOffsetX = (targetLeft - originalLeft + (targetWidth - originalWidth) * childe.pivotX / originalWidth).toInt()
        totalOffsetY = (targetTop - originalTop + (targetHeight - originalHeight) * childe.pivotY / originalHeight).toInt()
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        (dependency as? AppBarLayout)?.let {
            initializeProperies(child, dependency)
        }
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        (dependency as? AppBarLayout)?.let {
            var offsetRadio = (it.height - it.bottom).toFloat() / it.totalScrollRange
            child.x += totalOffsetX * (offsetRadio - this.offsetRadio)
            child.y += totalOffsetY * (offsetRadio - this.offsetRadio)
            child.scaleX = 1 - (1 - targetWidth.toFloat() / originalWidth) * offsetRadio
            child.scaleY = 1 - (1 - targetHeight.toFloat() / originalHeight) * offsetRadio
            this.offsetRadio = offsetRadio
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}