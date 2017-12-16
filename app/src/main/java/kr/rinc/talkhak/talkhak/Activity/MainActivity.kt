package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.util.GlideUtil
import kr.rinc.talkhak.talkhak.util.IntentUtil

class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setImage()
    setBtnAction()
  }

  fun setBtnAction() {
    qList.setOnClickListener {
      IntentUtil.moveActivity(this@MainActivity, ListAllActivity::class.java)
    }
    user.setOnClickListener {
      IntentUtil.moveActivity(this@MainActivity, MyPageActivity::class.java)
    }
  }

  fun setImage() {
    GlideUtil.setImage(this@MainActivity, R.drawable.logo_1, logo_1)
    GlideUtil.setImage(this@MainActivity, R.drawable.user, user)
    GlideUtil.setImage(this@MainActivity, R.drawable.logo, logoImg)
    GlideUtil.setImage(this@MainActivity, R.drawable.forma_1, qList)
    GlideUtil.setImage(this@MainActivity, R.drawable.comments, qComment)
    GlideUtil.setImage(this@MainActivity, R.drawable.forma_2, qRank)
    GlideUtil.setImage(this@MainActivity, R.drawable.settings, qSetting)
  }
}
