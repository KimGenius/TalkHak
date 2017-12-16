package kr.rinc.talkhak.talkhak.util


import android.content.Context
import android.widget.Toast

object ToastUtil {
    fun showShort(ctx: Context, str: String) {
        Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show()
    }

    fun notFun(context: Context) {
        ToastUtil.showShort(context, "구현중입니다!")
    }

    fun showLong(ctx: Context, str: String) {
        Toast.makeText(ctx, str, Toast.LENGTH_LONG).show()
    }
}