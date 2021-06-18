package cn.kter.urlimagespan

import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.style.ImageSpan
import coil.drawable.ScaleDrawable
import coil.imageLoader
import coil.request.ImageRequest

/**
 * 使用coil加载gif, 避免gif卡顿
 * by NIng
 */
class CoilDrawableProvider : DrawableProvider {
    override fun get(request: URLImageSpanRequest): Drawable {
        val drawable = if (request.url.isNullOrEmpty()) {
            request.placeholderDrawable ?: request.errorPlaceholder
        } else {
            execute(request)
            request.placeholderDrawable
        }
        return drawable ?: ColorDrawable()/*Can't be null*/
    }

    fun execute(request: URLImageSpanRequest) {
        val view = request.view ?: return
        val span = request.span
        val onResponse = l@{ drawable: Drawable ->
            with(drawable as? ScaleDrawable) {
                this?.start()
            }
            val rect = Rect(
                0, 0,
                drawable.intrinsicWidth, drawable.intrinsicHeight
            )
            drawable.setBounds(rect)
            val spannable = request.view?.text as? Spannable ?: return@l
            spannable.replaceSpan(
                span,
                ImageSpan(drawable, request.verticalAlignment),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        val request = ImageRequest.Builder(view.context)
            .data(request.url)
            .target(
                onStart = {
                },
                onSuccess = { result ->
                    onResponse(result)
                },
                onError = {
                }
            )
            .build()
        view.context.imageLoader.enqueue(request)
    }

    fun Spannable.replaceSpan(oldSpan: Any?, newSpan: Any?, flags: Int): Boolean {
        if (oldSpan == null || newSpan == null) {
            return false
        }
        val start = getSpanStart(oldSpan)
        val end = getSpanEnd(oldSpan)
        if (start == -1 || end == -1) {
            return false
        }
        removeSpan(oldSpan)
        setSpan(newSpan, start, end, flags)
        return true
    }
}