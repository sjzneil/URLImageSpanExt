package test

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.kter.urlimagespan.htmlToSpannable
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.util.CoilUtils
import com.benio.urlimagespan.R
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text)
        val imageLoader = ImageLoader.Builder(this.applicationContext)
            .componentRegistry {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder(applicationContext))
                } else {
                    add(GifDecoder())
                }
            }
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)
        textView.htmlToSpannable("<img src='http://cms-bucket.nosdn.127.net/catchpic/6/66/66d220bc87b442f90205d8aa50bd6bf4.gif?imageView&thumbnail=550x0&tostatic=0'/><img src='https://www.baidu.com/img/flexible/logo/pc/result.png'/><img src='https://image.niwoxuexi.com/blog/content/5c0d4b1972-loading.gif'/><font color='red' size='24'>你好</font>")

    }
}