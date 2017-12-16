package kr.rinc.talkhak.talkhak.Activity

import android.os.Bundle
import inc.r.ens.util.GlideUtil
import kotlinx.android.synthetic.main.activity_list.*
import kr.rinc.talkhak.talkhak.R

/**
 * Created by geniusk on 2017. 12. 17..
 */
class ListActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)
    setImage()
    setBtnAction()
  }
  fun setImage() {
    GlideUtil.setImage(this@ListActivity, R.drawable.arrow_left, backBtn)
    GlideUtil.setImage(this@ListActivity, R.drawable.logo_blue, logo)
  }
  fun setBtnAction() {

  }
}