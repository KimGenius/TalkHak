package kr.rinc.talkhak.talkhak.dialog

import android.app.Dialog
import android.content.Context
import kotlinx.android.synthetic.main.dialog_join_portfolio.*
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.network.RetroInit
import kr.rinc.talkhak.talkhak.util.SharedUtil
import kr.rinc.talkhak.talkhak.util.ToastUtil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CustomDialog(context: Context, idx: Int, commentIdx: Int) : Dialog(context) {
  var idx = idx
  var commentIdx = commentIdx
  override fun onCreate(savedInstanceState: android.os.Bundle?) {
    super.onCreate(savedInstanceState)
    val IpWindow = android.view.WindowManager.LayoutParams()
    IpWindow.flags = android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND
    IpWindow.dimAmount = 0.8f
    window!!.attributes = IpWindow
    setContentView(R.layout.dialog_join_portfolio)
    dialog_wrap.setOnClickListener {
      this.dismiss()
    }
    no.setOnClickListener {
      this.dismiss()
    }
    yes.setOnClickListener {
      RetroInit.networkList.checkComment(SharedUtil.getId(context), idx, commentIdx)
          .enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
              ToastUtil.showShort(context, "서버 오류")
              t!!.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
              if (response!!.isSuccessful) {
                ToastUtil.showShort(context, "채택하였습니다!")
              } else {
                ToastUtil.showShort(context, "클라이언트 오류")
              }
            }
          })
      this.dismiss()
    }
  }
}
