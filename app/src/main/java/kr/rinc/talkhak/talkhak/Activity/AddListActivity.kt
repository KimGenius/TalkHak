package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_list.*
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.model.ErrorModel
import kr.rinc.talkhak.talkhak.network.RetroInit
import kr.rinc.talkhak.talkhak.util.SharedUtil
import kr.rinc.talkhak.talkhak.util.ToastUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by geniusk on 2017. 12. 17..
 */
class AddListActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_list)
    addListBtn.setOnClickListener {
      RetroInit.networkList.addList(SharedUtil.getId(this@AddListActivity),
          SharedUtil.getNickname(this@AddListActivity),
          listEdit.text.toString()).enqueue(object : Callback<ErrorModel> {
        override fun onResponse(call: Call<ErrorModel>?, response: Response<ErrorModel>?) {
          if (response!!.isSuccessful) {
            ToastUtil.showShort(this@AddListActivity, "성공적으로 질문을 등록하였습니다")
            finish()
          } else {
            ToastUtil.showShort(this@AddListActivity, "클라이언트 오류")
          }
        }

        override fun onFailure(call: Call<ErrorModel>?, t: Throwable?) {
          ToastUtil.showShort(this@AddListActivity, "서버 오류")
        }

      })
    }
  }
}