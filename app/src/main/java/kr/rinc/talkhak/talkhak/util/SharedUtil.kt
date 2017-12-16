package kr.rinc.talkhak.talkhak.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by young on 2017-11-05/오전 1:53
 * This Project is APICreator
 */
object SharedUtil {
  private val TOKEN = "token"
  private val IDX = "idx"
  private val ID = "id"
  private val NICKNAME = "nickname"

  fun getToken(ctx: Context): String = getSharedPreferences(ctx).getString(TOKEN, "")

  fun setToken(ctx: Context, token: String) {
    val editor = getSharedPreferences(ctx).edit()
    editor.putString(TOKEN, token)
    editor.apply()
  }

  fun getId(ctx: Context): String = getSharedPreferences(ctx).getString(ID, "")
  fun getNickname(ctx: Context): String = getSharedPreferences(ctx).getString(NICKNAME, "")

  fun setUser(ctx: Context, id: String) {
    val editor = getSharedPreferences(ctx).edit()
    editor.putString(ID, id)
    editor.apply()
  }

  private fun getSharedPreferences(ctx: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(ctx)
  }
}