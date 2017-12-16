package kr.rinc.talkhak.talkhak.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.recycler_comment.view.*
import kr.rinc.talkhak.talkhak.Activity.BaseActivity
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.dialog.CustomDialog
import kr.rinc.talkhak.talkhak.holder.GridViewHolder
import kr.rinc.talkhak.talkhak.model.CommentList
import kr.rinc.talkhak.talkhak.util.SharedUtil


class CommentAdapter(idx: Int, activity: BaseActivity, gsonData: List<CommentList.Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private val mGson: List<CommentList.Comment> = gsonData
  private val activity = activity
  private val idx = idx
  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    val gridViewHolder = holder as GridViewHolder
    val item = mGson[position]
    gridViewHolder.itemView.run {
      content.text = item.content
      date.text = item.create_date
      writer.text = item.writer
      if (item.writer !== SharedUtil.getNickname(activity)) {
        commentWrap.setOnClickListener {
          Log.d("teset", idx.toString())
          Log.d("teset", item.idx.toString())
          val mCustomDialog = CustomDialog(activity, idx, item.idx)
          mCustomDialog!!.show()
        }
      }
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