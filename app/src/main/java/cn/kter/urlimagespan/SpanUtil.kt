package cn.kter.urlimagespan

import android.content.res.Resources
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ImageSpan
import android.widget.TextView
import kotlin.math.roundToInt

/**
 * textview设置html内容, 可以加载gif
 * by NIng
 */
fun TextView.htmlToSpannable(html: String){
    val htmlStr = Html.fromHtml(html)
    this.text = htmlStr
    this.movementMethod = LinkMovementMethod.getInstance()
    val text: CharSequence = this.getText()
    if (text is Spannable) {
        val end = text.length
        val sp = this.text as Spannable
        val imgs = sp.getSpans(0, end, ImageSpan::class.java)
        val style = SpannableStringBuilder(text)
        style.clearSpans()
        for (url in imgs) {
            val urlImageSpan = URLImageSpan.Builder()
                .url(url.source)
                .build(this)

            style.setSpan(
                urlImageSpan,
                sp.getSpanStart(url),
                sp.getSpanEnd(url),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.setText(style, TextView.BufferType.SPANNABLE)
    }
}
internal val Int.dp: Int
    get() = run {
        val metrics = Resources.getSystem().displayMetrics
        return (this * metrics.density).roundToInt()
    }