package inc.r.ens.util

import android.content.Context
import android.widget.Toast

/**
 * Created by young on 2017-07-26/오후 1:58
 * This Project is ENS
 */
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