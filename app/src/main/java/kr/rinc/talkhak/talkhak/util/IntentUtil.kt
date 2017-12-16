package inc.r.ens.util

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity

/**
 * Created by young on 2017-07-27/오후 6:43
 * This Project is ENS
 */
object IntentUtil {
    fun moveActivity(activity: AppCompatActivity, cls: Class<*>) {
        activity.startActivity(Intent(activity, cls))
    }
    fun moveActivity(activity: FragmentActivity, cls: Class<*>) {
        activity.startActivity(Intent(activity, cls))
    }
    fun moveClearActivity(activity: AppCompatActivity, cls: Class<*>) {
        val inte = Intent(activity,cls)
        inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(inte)
        activity.finish()
    }

    fun finishMoveActivity(activity: AppCompatActivity, cls: Class<*>) {
        activity.finish()
        activity.startActivity(Intent(activity, cls))
    }
}