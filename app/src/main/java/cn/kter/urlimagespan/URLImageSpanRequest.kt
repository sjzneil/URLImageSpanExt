package cn.kter.urlimagespan

import android.graphics.drawable.Drawable
import android.widget.TextView
import java.lang.ref.WeakReference

/**
 * 图片加载请求数据
 * by NIng
 */
class URLImageSpanRequest(
    textView: TextView,
    val url: String?,
    val placeholderDrawable: Drawable?,
    val errorPlaceholder: Drawable?,
    val desiredWidth: Int,
    val desiredHeight: Int,
    val verticalAlignment: Int
) {
    private val viewRef = WeakReference(textView)
    val view get():TextView? = viewRef.get()
    var span: Any? = null
}