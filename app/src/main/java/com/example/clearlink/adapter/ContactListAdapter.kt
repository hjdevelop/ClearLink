package com.example.clearlink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clearlink.databinding.RvItemBinding
import com.example.clearlink.model.UserModel

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ViewHolder>(){

    private val list = ArrayList<UserModel>()

    fun addItems(items: List<UserModel>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: RvItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserModel) = with(binding) {
            contactListRecyclerviewProfileIcon.setImageResource(item.profileImg)
            contactListRecyclerviewProfileName.text = item.name
            contactListRecyclerviewFavorites.setImageResource(item.favoritesImg)
        }
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //클릭 이벤트 추가
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}