package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import inc.r.ens.util.GlideUtil
import inc.r.ens.util.ToastUtil
import kotlinx.android.synthetic.main.activity_join.*
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.model.SchoolList
import kr.rinc.talkhak.talkhak.network.RetroInit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by geniusk on 2017. 12. 16..
 */
class JoinActivity : BaseActivity() {
  var schools = ArrayList<String>()
  lateinit var sAdapter : ArrayAdapter<String>
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_join)
    setImage()
    setBtnAction()
  }

  fun setBtnAction() {
    backBtn.setOnClickListener {
      finish()
    }
    school.onFocusChangeListener = View.OnFocusChangeListener { view, bFocus ->
      if (!bFocus) {
        RetroInit.networkList.getSchools(school.text.toString())
            .enqueue(object : Callback<SchoolList> {
              override fun onResponse(call: Call<SchoolList>?, response: Response<SchoolList>?) {
                if (response!!.isSuccessful) {
                  schools = ArrayList<String>()
                  response.body()!!.schoolList.mapTo(schools) { it.place + ", " + it.name }
                  sAdapter = getScools(schools)
                  schoolList.adapter = sAdapter
                } else {
                  ToastUtil.showShort(this@JoinActivity, "클라이언트 오류!")
                }
              }

              override fun onFailure(call: Call<SchoolList>?, t: Throwable?) {
                ToastUtil.showShort(this@JoinActivity, "서버 오류!")
                t!!.printStackTrace()
              }
            })
        schools.add("학교를 입력해주세요!")
        sAdapter = getScools(schools)
        schoolList.adapter = sAdapter
        schoolList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
          override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Toast.makeText(this@JoinActivity, sAdapter.getItem(position), Toast.LENGTH_SHORT).show()
          }

          override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
      }
    }
  }

  fun getScools(list : ArrayList<String>) : ArrayAdapter<String> {
    return ArrayAdapter<String>(this, R.layout.spinner_item, list)
  }

  fun setImage() {
    GlideUtil.setImage(this@JoinActivity, R.drawable.login_back, login_back)
    GlideUtil.setImage(this@JoinActivity, R.drawable.arrow_left, backBtn)
  }
}