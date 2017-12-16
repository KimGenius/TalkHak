package kr.rinc.talkhak.talkhak.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import inc.r.ens.holder.GridViewHolder
import kr.rinc.talkhak.talkhak.R
import kr.rinc.talkhak.talkhak.model.AllList


/**
 *
 * Created by young on 2017-09-02/오후 3:33
 * This Project is ENS
 */
class AllListAdapter(context: Context, gsonData: List<AllList.AllListModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private val mGson: List<AllList.AllListModel> = gsonData
  private val mCtx: Context = context

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    val gridViewHolder = holder as GridViewHolder
    val item = mGson[position]
    gridViewHolder.itemView.run {
      Log.d("item", item.content)
    }
  }

  override fun getItemCount(): Int {
    return mGson.size
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
    val viewHolder: RecyclerView.ViewHolder
    val view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_all_list, parent, false)
    viewHolder = GridViewHolder(view)
    return viewHolder
  }
}