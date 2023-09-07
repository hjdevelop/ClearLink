package com.example.clearlink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clearlink.R
import com.example.clearlink.databinding.RvItemBinding
import com.example.clearlink.model.UserModel

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ViewHolder>(){

    private val list = ArrayList<UserModel>()

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    // 좋아요 클릭
    var itemClick: ItemClick? = null
    // 페이지 이동 클릭
    var itemClick2: ItemClick? = null
    // 연락처 삭제 클릭
    var itemLongClick: ItemClick? = null

    fun addItems(items: List<UserModel>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(items: UserModel) {
        list.add(items)
        notifyDataSetChanged()
    }

    fun addItemNS(userModel: UserModel?) {
        if (userModel == null) {
            return
        }
        list.add(userModel)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int?){
        if (position != null) {
            list.removeAt(position)
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(
        private val binding: RvItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.contactListRecyclerviewFavorites.setOnClickListener {
                itemClick?.onClick(it, adapterPosition)
                val isFavorite = list[adapterPosition].favorites
                list[adapterPosition].favorites = !isFavorite
                notifyDataSetChanged()
            }

            binding.root.setOnLongClickListener {
                itemLongClick?.onClick(it, adapterPosition)
                true
            }
        }

        fun bind(item: UserModel) = with(binding) {
            contactListRecyclerviewProfileIcon.setImageURI(item.profileImg)
            contactListRecyclerviewProfileName.text = item.name
            if(item.favorites) {
                contactListRecyclerviewFavorites.setImageResource(R.drawable.ic_full_start)
            } else {
                contactListRecyclerviewFavorites.setImageResource(R.drawable.ic_star)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //클릭 이벤트 추가
        holder.itemView.setOnClickListener {
            itemClick2?.onClick(it, position)
        }

        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}