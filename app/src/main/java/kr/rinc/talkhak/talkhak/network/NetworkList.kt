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
               @Field("schoolCode") schoolCode: Int) : Call<ErrorModel>

  /*
   id: String(token > 0 ? 토큰값 : 진짜아이디)
    pw: String(token > 0 ? 빈거 보내도 됨 : 진짜비밀번호)
    token: Integer(0 or n > 0)
     */
  @POST("/login")
  @FormUrlEncoded
  fun login(@Field("id") id: String,
            @Field("pw") pw: String,
            @Field("token") token: Int) : Call<Login>

  @GET("/")
  fun getAllList() : Call<AllList>

  @GET("/board/{idx}")
  fun getComment(@Path("idx") idx : Int) : Call<CommentList>

  @POST("/board/{idx}")
  @FormUrlEncoded
  fun addComment(@Path("idx") idx : Int,
                 @Field("content") content : String,
                 @Field("writer") writer : String) : Call<ResponseBody>

}