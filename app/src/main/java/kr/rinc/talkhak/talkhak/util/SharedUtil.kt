package kr.rinc.talkhak.talkhak.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SharedUtil {
  private val TOKEN = "token"
  private val IDX = "idx"
  private val ID = "id"
  private val NICKNAME = "nickname"
  private val TOKENIDX = "tokenidx"
  private val SCHOOL = "school"

  fun getToken(ctx: Context): String = getSharedPreferences(ctx).getString(TOKEN, "")
  fun getTokenIdx(ctx: Context): String = getSharedPreferences(ctx).getString(TOKENIDX, "")

  fun setToken(ctx: Context, token: String, tokenIdx: Int) {
    val editor = getSharedPreferences(ctx).edit()
    editor.putString(TOKEN, token)
    editor.putString(TOKENIDX, tokenIdx.toString())
    editor.apply()
  }

  fun getId(ctx: Context): String = getSharedPreferences(ctx).getString(ID, "")
  fun getSchool(ctx: Context): String = getSharedPreferences(ctx).getString(SCHOOL, "")
  fun setSchool(ctx: Context, school: String) {
    val editor = getSharedPreferences(ctx).edit()
    editor.putString(SCHOOL, school)
    editor.apply()
  }
  fun getNickname(ctx: Context): String = getSharedPreferences(ctx).getString(NICKNAME, "")
  fun setNickname(ctx: Context, nickname: String) {
    val editor = getSharedPreferences(ctx).edit()
    editor.putString(NICKNAME, nickname)
    editor.apply()
  }
  fun setUser(ctx: Context, id: String) {
    val editor = getSharedPreferences(ctx).edit()
    editor.putString(ID, id)
    editor.apply()
  }

  private fun getSharedPreferences(ctx: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(ctx)
  }
}