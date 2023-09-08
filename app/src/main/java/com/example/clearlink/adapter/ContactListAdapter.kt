package com.example.clearlink.adapter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.clearlink.MainActivity
import com.example.clearlink.R
import com.example.clearlink.databinding.RvItemBinding
import com.example.clearlink.model.UserModel

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ViewHolder>(){

    private val list = ArrayList<UserModel>()
    private val CALL_PHONE_PERMISSION_CODE = 123

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

        val swipeLayout : ConstraintLayout

        init {
            binding.contactListRecyclerviewFavorites.setOnClickListener {
                itemClick?.onClick(it, adapterPosition)
                val isFavorite = list[adapterPosition].favorites
                list[adapterPosition].favorites = !isFavorite
                notifyDataSetChanged()
            }

            swipeLayout = binding.contactListRecyclerviewLayout

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

    // 아이템을 스와이프할 때 호출되는 메서드
    fun onItemSwipe(position: Int, context: Context) {

        // 권한확인
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한요청
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PHONE_PERMISSION_CODE
            )
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${list[position].phoneNumber}")

            try {
                // 전화를 시도
                context.startActivity(intent)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

            // 전화 시도 후, 아이템 레이아웃 초기화 및 RecyclerView 갱신
            notifyDataSetChanged()
        }
    }


}