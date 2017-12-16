package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mypage.*
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.model.Count
import kr.rinc.talkhak.talkhak.network.RetroInit
import kr.rinc.talkhak.talkhak.util.IntentUtil
import kr.rinc.talkhak.talkhak.util.SharedUtil
import kr.rinc.talkhak.talkhak.util.ToastUtil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by geniusk on 2017. 12. 17..
 */
class MyPageActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_mypage)
    RetroInit.networkList.getCheckList(SharedUtil.getNickname(this@MyPageActivity))
        .enqueue(object : Callback<Count> {
          override fun onFailure(call: Call<Count>?, t: Throwable?) {
            ToastUtil.showShort(this@MyPageActivity, "서버 오류")
          }

          override fun onResponse(call: Call<Count>?, response: Response<Count>?) {
            if (response!!.isSuccessful) {
              text.text = "현재 채택된 문제 수는 " + response.body()!!.count.toString() + "개 입니다"
            } else {
              ToastUtil.showShort(this@MyPageActivity, "클라이언트 오류")
            }
          }
        })
    injung.setOnClickListener {
      IntentUtil.moveActivity(this@MyPageActivity, InjungActivity::class.java)
    }
  }
}