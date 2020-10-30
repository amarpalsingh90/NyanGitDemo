package com.nayangitdemo.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nayangitdemo.R
import com.nayangitdemo.callback.IAdapterCallback
import com.nayangitdemo.model.Item
import kotlinx.android.synthetic.main.pop_repo_row_item.view.*


class PopularGitRepoAdapter(
    private var items: MutableList<Item>,
    private val listener: IAdapterCallback
) :
    RecyclerView.Adapter<PopularGitRepoAdapter.IssueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IssueViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.pop_repo_row_item, parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun swapData(list: List<Item>) {
        if (!items.isEmpty()) {
            items.clear()
        }
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun appendData(list: List<Item>) {
        val previousSize: Int = items.size
        items.addAll(list)
        notifyItemRangeInserted(previousSize, items.size)
    }

    inner class IssueViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: Item) {
            with(view) {
                Glide.with(itemView)
                    .load(item.owner.avatar_url)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .fallback(R.mipmap.ic_launcher)
                    .into(itemView.iv_user_avtar)
                txt_repo_owner.text = item.full_name
                txt_repo_url.text = item.owner.organizations_url
            }

            view.setOnClickListener {
                val userName: List<String> = item.owner.url.split("/")
                Log.d("item.owner.url", userName.last())
                listener.onRepoDetailClick(item.description, userName.last())
            }
        }
    }
}