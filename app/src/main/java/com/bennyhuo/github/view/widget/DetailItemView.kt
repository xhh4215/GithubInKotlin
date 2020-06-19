package com.bennyhuo.github.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.ToggleButton
import com.bennyhuo.github.R
import com.bennyhuo.github.utils.subscribeIgnoreError
import com.bennyhuo.kotlin.opd.delegateOf
import kotlinx.android.synthetic.main.detail_item.view.*
import org.jetbrains.anko.sdk15.listeners.onClick
import rx.Observable
import kotlin.reflect.KProperty


typealias CheckEvent = (Boolean) -> Observable<Boolean>
class DetailItemView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.detail_item, this)
    }

    var title by delegateOf(titleView::getText, titleView::setText)
    var content by delegateOf(contentView::getText, contentView::setText,"")
    var icon by delegateOf(iconView::setImageResource, 0)
    var operatoricon by delegateOf(operatorIconView::setBackgroundResource, 0)
    var isCheckd by delegateOf(operatorIconView::isChecked, operatorIconView::setChecked)
    var checkEvent: CheckEvent? = null


    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.DetailItemView)
            title = a.getString(R.styleable.DetailItemView_item_title) ?: ""
            content = a.getString(R.styleable.DetailItemView_item_content) ?: ""
            icon = a.getResourceId(R.styleable.DetailItemView_item_icon, 0)
            operatoricon = a.getResourceId(R.styleable.DetailItemView_item_op_icon, 0)
            a.recycle()
        }
        onClick {
            checkEvent?.invoke(isCheckd)
                    ?.subscribeIgnoreError {
                        isCheckd = it
                    }
        }
    }

}