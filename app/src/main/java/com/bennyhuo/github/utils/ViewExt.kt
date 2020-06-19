package com.bennyhuo.github.utils

import android.content.Context
import android.view.ViewManager
import android.widget.TextView
import cn.carbs.android.avatarimageview.library.AvatarImageView
import com.zzhoujay.richtext.RichText
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

/**
 * Created by benny on 10/15/17.
 */
var TextView.markdownText: String
    set(value) {
        RichText.fromMarkdown(value).into(this)
    }
    get() = text.toString()

var TextView.htmlText: String
    set(value) {
        RichText.fromHtml(value).into(this)
    }
    get() = text.toString()

inline fun ViewManager.avatarImageView(): AvatarImageView = avatarImageView() {}
inline fun ViewManager.avatarImageView(init: (@AnkoViewDslMarker AvatarImageView).() -> Unit): AvatarImageView {
    return ankoView({ ctx: Context -> AvatarImageView(ctx) }, theme = 0) { init() }
}