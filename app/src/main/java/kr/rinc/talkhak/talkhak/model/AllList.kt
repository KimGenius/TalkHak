package kr.rinc.talkhak.talkhak.model

import com.google.gson.annotations.SerializedName

/**
 * Created by geniusk on 2017. 12. 17..
 */
class AllList {
  @SerializedName("list")
  lateinit var allListModel : List<AllListModel>
  inner class AllListModel {
    val idx = 0
    val content = ""
    val writer = "check"
    val check = 0
    val subject = ""
    val created_date = ""
    val img = 0
    /*
    idx: Integer()
    content: String(글 내용)
    writer: String(작성자 닉네임)
    check: Integer(채택을 했는가 ? 0 : 1)
    subject: String(과목)
    created_date: String(작성자 닉네임)
    img: Integer(있으면 1 없으면 0)
    */
  }
}