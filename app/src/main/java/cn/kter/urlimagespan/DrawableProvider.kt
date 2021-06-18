package cn.kter.urlimagespan

import android.graphics.drawable.Drawable

/**
 * 加载图片接口, 可以使用glide, coil等实现
 * by NIng
 */
interface DrawableProvider {
    fun get(request: URLImageSpanRequest): Drawable
}