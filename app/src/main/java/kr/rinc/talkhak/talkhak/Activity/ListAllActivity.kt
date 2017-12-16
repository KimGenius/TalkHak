package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_all_list.*
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.adapter.AllListAdapter
import kr.rinc.talkhak.talkhak.model.AllList
import kr.rinc.talkhak.talkhak.network.RetroInit
import kr.rinc.talkhak.talkhak.util.GlideUtil
import kr.rinc.talkhak.talkhak.util.ToastUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by geniusk on 2017. 12. 17..
 */
class ListAllActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_all_list)
    setImage()
    setBtnAction()
  }

  fun setImage() {
    GlideUtil.setImage(this@ListAllActivity, R.drawable.arrow_left, backBtn)
    GlideUtil.setImage(this@ListAllActivity, R.drawable.logo_blue, logo)
  }

  fun setBtnAction() {
    RetroInit.networkList.getAllList().enqueue(object : Callback<AllList> {
      override fun onFailure(call: Call<AllList>?, t: Throwable?) {
        ToastUtil.showShort(this@ListAllActivity, "서버 오류!")
        t!!.printStackTrace()
      }

      override fun onResponse(call: Call<AllList>?, response: Response<AllList>?) {
        if (response!!.isSuccessful) {
          response.body().run {
            val layoutManager = GridLayoutManager(this@ListAllActivity, 1)
            layoutManager.orientation = GridLayoutManager.VERTICAL
            recycler.layoutManager = layoutManager
            recycler.adapter = AllListAdapter(this@ListAllActivity, this!!.allListModel)
          }
        } else {
          ToastUtil.showShort(this@ListAllActivity, "클라이언트 오류!")
        }
      }
    })
    backBtn.setOnClickListener { finish() }
  }
}