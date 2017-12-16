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
import inc.r.ens.holder.GridViewHolder
import inc.r.ens.util.GlideUtil
import inc.r.ens.util.ToastUtil
import kotlinx.android.synthetic.main.activity_all_list.*
import kotlinx.android.synthetic.main.recycler_all_list.view.*
import kr.rinc.talkhak.talkhak.Activity.BaseActivity
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.model.AllList
import kr.rinc.talkhak.talkhak.model.CommentList
import kr.rinc.talkhak.talkhak.network.RetroInit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 *
 * Created by young on 2017-09-02/오후 3:33
 * This Project is ENS
 */
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
      activity.commentView.setOnClickListener {
        activity.commentView.startAnimation(slide_down)
        activity.commentView.visibility = View.GONE
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