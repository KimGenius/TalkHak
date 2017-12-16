package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
    var list = listOf<String>("국어", "영어", "수학", "사회", "과학", "그외")
    val sAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, list)
    subjectSpinner.adapter = sAdapter
    subjectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(this@AddListActivity, sAdapter.getItem(position), Toast.LENGTH_SHORT).show()
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
    addListBtn.setOnClickListener {
      RetroInit.networkList.addList(SharedUtil.getId(this@AddListActivity),
          SharedUtil.getNickname(this@AddListActivity),
          subjectSpinner.selectedItem.toString(),
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