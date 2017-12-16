package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import inc.r.ens.util.GlideUtil
import inc.r.ens.util.IntentUtil
import inc.r.ens.util.ToastUtil
import kotlinx.android.synthetic.main.activity_join.*
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.model.ErrorModel
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
  var schoolCodes = ArrayList<Int>()
  lateinit var sAdapter: ArrayAdapter<String>
  var schoolCode = 0
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_join)
    setImage()
    setBtnAction()
  }

  fun setBtnAction() {
    joinBtn.setOnClickListener {
      if(password.text.toString() == passwordCheck.text.toString()) {
        RetroInit.networkList.register(id.text.toString(),
            password.text.toString(), 0,
            nickname.text.toString(), schoolCode).enqueue(object : Callback<ErrorModel> {
          override fun onFailure(call: Call<ErrorModel>?, t: Throwable?) {
            ToastUtil.showShort(this@JoinActivity, "서버 오류!")
            t!!.printStackTrace()
          }

          override fun onResponse(call: Call<ErrorModel>?, response: Response<ErrorModel>?) {
            if (response!!.code() == 200) {
              ToastUtil.showShort(this@JoinActivity, "회원가입에 성공하셨습니다!")
              IntentUtil.finishMoveActivity(this@JoinActivity, MainActivity::class.java)
            } else {
              ToastUtil.showShort(this@JoinActivity, "입력 값을 확인해주세요")
            }
          }
        })
      }else {
        ToastUtil.showShort(this@JoinActivity, "비밀번호와 비밀번호 확인을 잘 입력해주세요!")
      }
    }
    backBtn.setOnClickListener {
      finish()
    }
    school.onFocusChangeListener = View.OnFocusChangeListener { _, bFocus ->
      if (!bFocus) {
        RetroInit.networkList.getSchools(school.text.toString())
            .enqueue(object : Callback<SchoolList> {
              override fun onResponse(call: Call<SchoolList>?, response: Response<SchoolList>?) {
                if (response!!.isSuccessful) {
                  schools = ArrayList<String>()
                  for (item in response.body()!!.schoolList) {
                    schoolCodes.add(item.no)
                  }
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
            if(schoolCodes.size !== 0)
            schoolCode = schoolCodes[position]
            Toast.makeText(this@JoinActivity, sAdapter.getItem(position), Toast.LENGTH_SHORT).show()
          }

          override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
      }
    }
  }

  fun getScools(list: ArrayList<String>): ArrayAdapter<String> {
    return ArrayAdapter<String>(this, R.layout.spinner_item, list)
  }

  fun setImage() {
    GlideUtil.setImage(this@JoinActivity, R.drawable.login_back, login_back)
    GlideUtil.setImage(this@JoinActivity, R.drawable.arrow_left, backBtn)
  }
}