package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import android.util.Log
import kr.rinc.talkhak.talkhak.R
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.CallbackManager
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.log.Logger
import inc.r.ens.util.GlideUtil
import inc.r.ens.util.IntentUtil
import inc.r.ens.util.ToastUtil
import kr.rinc.talkhak.talkhak.kakao.KakaoSignupActivity
import kr.rinc.talkhak.talkhak.network.RetroInit
import kr.rinc.talkhak.talkhak.util.SharedUtil
import okhttp3.Callback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


/**
 * Created by geniusk on 2017. 12. 16..
 */
class LoginActivity : BaseActivity() {
  lateinit var callbackManager: CallbackManager
  private lateinit var callback: SessionCallback
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    setLoginAction()
    setImage()
    setBtnAction()
  }

  fun setBtnAction() {
    joinBtn.setOnClickListener {
      IntentUtil.moveActivity(this@LoginActivity, JoinActivity::class.java)
    }
    loginBtn.setOnClickListener {
      RetroInit.networkList.login(id.text.toString(), pw.text.toString(), 0)
          .enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
              ToastUtil.showShort(this@LoginActivity, "서버 오류!")
              t!!.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
              if (response!!.code() == 200) {
                SharedUtil.setUser(this@LoginActivity, id.text.toString())
                ToastUtil.showShort(this@LoginActivity, "환영합니다!")
                IntentUtil.finishMoveActivity(this@LoginActivity, MainActivity::class.java)
              } else {
                ToastUtil.showShort(this@LoginActivity, "클라이언트 오류!")
              }
            }
          })
    }
  }

  fun setImage() {
    GlideUtil.setImage(this@LoginActivity, R.drawable.login_back, login_back)
  }

  fun setLoginAction() {
    callbackManager = CallbackManager.Factory.create()
    faceLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
      override fun onSuccess(loginResult: LoginResult) {
        // App code
        Log.d("login", loginResult.accessToken.token)
      }

      override fun onCancel() {
        // App code
      }

      override fun onError(exception: FacebookException) {
        // App code
      }
    })

    //kakao
    callback = SessionCallback()
    Session.getCurrentSession().addCallback(callback)
    Session.getCurrentSession().checkAndImplicitOpen()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
      return
    }
    callbackManager.onActivityResult(requestCode, resultCode, data)
    super.onActivityResult(requestCode, resultCode, data)
  }

  inner private class SessionCallback : ISessionCallback {

    override fun onSessionOpened() {
      redirectSignupActivity()
    }

    override fun onSessionOpenFailed(exception: KakaoException) {
      if (exception != null) {
        Logger.e(exception)
      }
      setContentView(R.layout.activity_login) // 세션 연결이 실패했을때
    }
  }

  protected fun redirectSignupActivity() {
    val intent = Intent(this, KakaoSignupActivity::class.java)
    Log.d("signup", "redirectSignup")
    startActivity(intent)
    finish()
  }
}