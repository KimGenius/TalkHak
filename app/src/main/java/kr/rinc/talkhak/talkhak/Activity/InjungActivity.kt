package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_injung.*
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.network.RetroInit
import kr.rinc.talkhak.talkhak.util.SharedUtil
import kr.rinc.talkhak.talkhak.util.ToastUtil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by geniusk on 2017. 12. 17..
 */
class InjungActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_injung)
    RetroInit.networkList.getPdf(SharedUtil.getSchool(this@InjungActivity),
        SharedUtil.getNickname(this@InjungActivity),
        SharedUtil.getId(this@InjungActivity))
        .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            ToastUtil.showShort(this@InjungActivity, "서버 오류")
          }

          override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
            if (response!!.isSuccessful) {
              webView.settings.setSupportZoom(true)
              webView.webViewClient = WebViewClient()
              webView.loadUrl("http://appjam14.rinc.kr/" + SharedUtil.getId(this@InjungActivity) + "/temp.html")
            } else {
              ToastUtil.showShort(this@InjungActivity, "클라이언트 오류")
            }
          }
        })
  }
}