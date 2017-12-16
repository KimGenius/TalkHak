package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import inc.r.ens.util.IntentUtil
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.util.SharedUtil

/**
 * Created by geniusk on 2017. 12. 17..
 */
class SplashActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
    Log.d("test", "ㅁㄴㅇㄹㄴㅁㅇㄹ"+SharedUtil.getToken(this@SplashActivity))
    Log.d("test", "ㅁㄴㅇㄹㄴㅁㅇㄹ"+SharedUtil.getToken(this@SplashActivity).isEmpty().toString())
    Handler().postDelayed({
      if (SharedUtil.getToken(this@SplashActivity) == "" && SharedUtil.getId(this@SplashActivity) == "") {
        IntentUtil.finishMoveActivity(this@SplashActivity, LoginActivity::class.java)
      } else {
        IntentUtil.finishMoveActivity(this@SplashActivity, MainActivity::class.java)
      }
    }, 1500)
  }
}