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
import kr.rinc.talkhak.talkhak.model.Login
import kr.rinc.talkhak.talkhak.network.RetroInit
import kr.rinc.talkhak.talkhak.util.GlideUtil
import kr.rinc.talkhak.talkhak.util.IntentUtil
import kr.rinc.talkhak.talkhak.util.SharedUtil
import kr.rinc.talkhak.talkhak.util.ToastUtil
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
          .enqueue(object : retrofit2.Callback<Login> {
            override fun onFailure(call: Call<Login>?, t: Throwable?) {
              ToastUtil.showShort(this@LoginActivity, "서버 오류!")
              t!!.printStackTrace()
            }

            override fun onResponse(call: Call<Login>?, response: Response<Login>?) {
              if (response!!.code() == 200) {
                response.body().run {
                  SharedUtil.setUser(this@LoginActivity, this!!.id)
                  SharedUtil.setNickname(this@LoginActivity, this.name)
                  ToastUtil.showShort(this@LoginActivity, "환영합니다!")
                  IntentUtil.finishMoveActivity(this@LoginActivity, MainActivity::class.java)
                }
              } else if (response!!.code() == 400) {
                ToastUtil.showShort(this@LoginActivity, "로그인 정보를 확인해주세요!")
              } else {
                ToastUtil.showShort(this@LoginActivity, "클라이언트 오류!")
              }
            }
          })
    }
  }

  fun setImage() {
    GlideUtil.setImage(this@LoginActivity, R.drawable.login_back, login_back)
    GlideUtil.setImage(this@LoginActivity, R.drawable.logo, logo)
  }

  fun setLoginAction() {
    callbackManager = CallbackManager.Factory.create()
    faceLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
      override fun onSuccess(loginResult: LoginResult) {
        // App code
        Log.d("login", loginResult.accessToken.token)
        SharedUtil.setToken(this@LoginActivity, loginResult.accessToken.token, 1)
        IntentUtil.moveActivity(this@LoginActivity, JoinTokenActivity::class.java)
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