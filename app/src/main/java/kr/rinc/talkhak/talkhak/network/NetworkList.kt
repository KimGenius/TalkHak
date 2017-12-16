package kr.rinc.talkhak.talkhak.network

import kr.rinc.talkhak.talkhak.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkList {
  @GET("/schoolList")
  fun getSchools(@Query("schoolName") schoolName: String): Call<SchoolList>

  /*
  id: String(token > 0 ? 토큰값 : 진짜아이디)
  pw: String(token > 0 ? 빈거 보내도 됨 : 진짜비밀번호)
  token: Integer(0 or n > 0)
  name: String(닉네임)
  schoolCode: Integer(학교 번호)
   */
  @POST("/register")
  @FormUrlEncoded
  fun register(@Field("id") id: String,
               @Field("pw") pw: String,
               @Field("token") token: Int = 0,
               @Field("name") name: String,
               @Field("schoolCode") schoolCode: Int): Call<ErrorModel>

  /*
   id: String(token > 0 ? 토큰값 : 진짜아이디)
    pw: String(token > 0 ? 빈거 보내도 됨 : 진짜비밀번호)
    token: Integer(0 or n > 0)
     */
  @POST("/login")
  @FormUrlEncoded
  fun login(@Field("id") id: String,
            @Field("pw") pw: String,
            @Field("token") token: Int): Call<Login>

  @GET("/")
  fun getAllList(): Call<AllList>

  @GET("/board/{idx}")
  fun getComment(@Path("idx") idx: Int): Call<CommentList>

  @POST("/board/{idx}")
  @FormUrlEncoded
  fun addComment(@Path("idx") idx: Int,
                 @Field("content") content: String,
                 @Field("writer") writer: String): Call<ResponseBody>

  @POST("/board/{idx}/{commentIdx}")
  @FormUrlEncoded
  fun checkComment(
      @Field("id") id: String,
      @Path("idx") idx: Int,
      @Path("commentIdx") commentIdx: Int): Call<ResponseBody>

  @POST("/write")
  @FormUrlEncoded
  fun addList(@Field("id") id : String,
              @Field("writer") writer: String,
              @Field("subject") subject: String,
              @Field("content") content: String) : Call<ErrorModel>
  /*
  id: String(token or 진짜아이디)
    writer: String(닉네임)
    title: String(제목)
    content: String(내용)
    subject: String(과목)
   */
  @GET("/checkList")
  fun getCheckList(@Query("writer") writer : String) : Call<Count>

  @POST("/pdf")
  @FormUrlEncoded
  fun getPdf(@Field("schoolName") schoolName : String,
             @Field("name") name : String,
             @Field("id") id : String) : Call<ResponseBody>
  /*
  schoolName: String(학교 이름)
    name: String(이름)
    id: String(이름)
   */
}