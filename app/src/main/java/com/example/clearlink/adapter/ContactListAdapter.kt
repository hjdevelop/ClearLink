package com.example.clearlink.adapter

import android.view.LayoutInflater
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
            contactListRecyclerviewProfileIcon.setImageURI(item.profileImg)
            contactListRecyclerviewProfileName.text = item.name
            contactListRecyclerviewFavorites.setImageResource(item.favoritesImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}