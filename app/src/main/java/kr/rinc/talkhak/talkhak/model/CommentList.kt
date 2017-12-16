package kr.rinc.talkhak.talkhak.model

/**
 * Created by geniusk on 2017. 12. 17..
 */
class CommentList {
  lateinit var list : List<Comment>
  inner class Comment {
    val content = ""
    val writer = ""
    val check = Int
    val create_date = ""
  }
}