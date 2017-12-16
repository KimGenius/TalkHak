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
import kr.rinc.talkhak.talkhak.kakao.KakaoSignupActivity


/**
 * Created by geniusk on 2017. 12. 16..
 */
class LoginActivity : BaseActivity() {
  lateinit var callbackManager: CallbackManager
  private lateinit var callback: SessionCallback
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
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
    Log.d("signup","redirectSignup")
    startActivity(intent)
    finish()
  }
}