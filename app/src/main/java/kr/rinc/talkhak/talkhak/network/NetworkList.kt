package kr.rinc.talkhak.talkhak.network

import kr.rinc.talkhak.talkhak.model.SchoolList
import retrofit2.Call
import retrofit2.http.*

interface NetworkList {
    @GET("/schoolList")
    fun getSchools(@Query("schoolName") scoolName: String): Call<SchoolList>
}