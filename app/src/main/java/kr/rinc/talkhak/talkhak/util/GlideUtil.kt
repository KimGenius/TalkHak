package inc.r.ens.util

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.util.concurrent.ExecutionException

/**
 * Created by young on 2017-07-26/오후 1:59
 * This Project is ENS
 */
object GlideUtil {
    fun setImage(ctx: Context, id: Int, image: ImageView) {
        Glide.with(ctx).load(id).into(image)
    }
    fun setImage(ctx: Context, id: String, image: ImageView) {
        Glide.with(ctx).load(id).into(image)
    }
}