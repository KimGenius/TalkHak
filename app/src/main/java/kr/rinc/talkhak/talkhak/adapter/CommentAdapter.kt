package kr.rinc.talkhak.talkhak.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import kr.rinc.talkhak.talkhak.Activity.BaseActivity
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.holder.GridViewHolder
import kr.rinc.talkhak.talkhak.model.CommentList


class CommentAdapter(activity: BaseActivity, gsonData: List<CommentList.Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private val mGson: List<CommentList.Comment> = gsonData
  private val activity = activity
  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    val gridViewHolder = holder as GridViewHolder
    val item = mGson[position]
    gridViewHolder.itemView.run {
      Log.d("test",item.writer)
    }
  }

  override fun getItemCount(): Int {
    return mGson.size
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
    val viewHolder: RecyclerView.ViewHolder
    val view = LayoutInflater.from(activity).inflate(R.layout.recycler_comment, parent, false)
    viewHolder = GridViewHolder(view)
    return viewHolder
  }
}