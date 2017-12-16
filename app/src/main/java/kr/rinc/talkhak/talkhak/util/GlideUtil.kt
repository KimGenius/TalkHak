package kr.rinc.talkhak.talkhak.util

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.util.concurrent.ExecutionException
object GlideUtil {
    fun setImage(ctx: Context, id: Int, image: ImageView) {
        Glide.with(ctx).load(id).into(image)
    }
    fun setImage(ctx: Context, id: String, image: ImageView) {
        Glide.with(ctx).load(id).into(image)
    }
}