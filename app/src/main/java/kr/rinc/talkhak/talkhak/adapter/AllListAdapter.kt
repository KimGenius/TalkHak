package kr.rinc.talkhak.talkhak.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_all_list.*
import kotlinx.android.synthetic.main.recycler_all_list.view.*
import kr.rinc.talkhak.talkhak.Activity.BaseActivity
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.holder.GridViewHolder
import kr.rinc.talkhak.talkhak.model.AllList
import kr.rinc.talkhak.talkhak.model.CommentList
import kr.rinc.talkhak.talkhak.network.RetroInit
import kr.rinc.talkhak.talkhak.util.GlideUtil
import kr.rinc.talkhak.talkhak.util.SharedUtil
import kr.rinc.talkhak.talkhak.util.ToastUtil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllListAdapter(activity: BaseActivity, gsonData: List<AllList.AllListModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private val mGson: List<AllList.AllListModel> = gsonData
  private val activity = activity
  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    val gridViewHolder = holder as GridViewHolder
    val item = mGson[position]
    gridViewHolder.itemView.run {
      content.text = item.content
      writer.text = item.writer
      date.text = item.created_date
      subject.text = item.subject
      GlideUtil.setImage(context, R.drawable.user_profile, profile)
      if (item.img != 0) {
        GlideUtil.setImage(context, "http://appjam14.rinc.kr/" + item.idx.toString() + "/a.png", listImg)
      } else {
        listImg.minimumHeight = 0
      }
      val slide_down = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
      val slide_up = AnimationUtils.loadAnimation(activity, R.anim.slide_up);

      // Start animation
      var toggle = true
      commentBtn.setOnClickListener {
        if (toggle) {
          RetroInit.networkList.getComment(item.idx).enqueue(object : Callback<CommentList> {
            override fun onFailure(call: Call<CommentList>?, t: Throwable?) {
              ToastUtil.showShort(activity, "클라이언트 오류")
              t!!.printStackTrace()
            }

            override fun onResponse(call: Call<CommentList>?, response: Response<CommentList>?) {
              if (response!!.isSuccessful) {
                Log.d("test", response.body()!!.list.toString())
                response.body().run {
                  val layoutManager = GridLayoutManager(activity, 1)
                  layoutManager.orientation = GridLayoutManager.VERTICAL
                  activity.commentRecycler.layoutManager = layoutManager
                  activity.commentRecycler.adapter = CommentAdapter(activity, this!!.list)
                }
              } else {
                ToastUtil.showShort(activity, "클라이언트 오류")
              }
            }
          })

          activity.commentView.startAnimation(slide_up)
          activity.commentView.visibility = View.VISIBLE
        }
        toggle = !toggle
      }
      activity.commentX.setOnClickListener {
        activity.commentView.startAnimation(slide_down)
        activity.commentView.visibility = View.GONE
      }
      Log.d("nickname",SharedUtil.getNickname(activity)+"asdf")
      activity.commentSend.setOnClickListener {
        RetroInit.networkList.addComment(item.idx, activity.commentEdit.text.toString(), SharedUtil.getNickname(activity))
            .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            ToastUtil.showShort(activity, "서버 오류!")
            t!!.printStackTrace()
          }

          override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
            if (response!!.isSuccessful) {
              ToastUtil.showShort(activity, "등록되었습니다!")
              activity.recreate()
            } else {
              ToastUtil.showShort(activity, "클라이언트 오류")
            }
          }

        })
      }
    }
  }

  override fun getItemCount(): Int {
    return mGson.size
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
    val viewHolder: RecyclerView.ViewHolder
    val view = LayoutInflater.from(activity).inflate(R.layout.recycler_all_list, parent, false)
    viewHolder = GridViewHolder(view)
    return viewHolder
  }
}