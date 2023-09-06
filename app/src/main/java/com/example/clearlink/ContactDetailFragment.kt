package com.example.clearlink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.clearlink.databinding.ActivityContactDetailFragmentBinding
import com.example.clearlink.databinding.ActivityMainBinding
import com.example.clearlink.model.UserModel

class ContactDetailFragment : AppCompatActivity() {

    private lateinit var binding : ActivityContactDetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        // 보내진 데이터를 받기 위해 Intent를 가져옵니다.
//        val receivedIntent = intent
//
//        // Intent에서 데이터를 추출합니다.
//        val dataBundle = receivedIntent.extras

        val item = intent.getParcelableExtra<UserModel>("item") as UserModel

        binding.contactDetailFragmentName.text = item.name


//        if (dataBundle != null) {
//            val position = dataBundle.getInt("ITEM_INDEX", -1)
//            val userModel = dataBundle.getParcelable<UserModel>("ITEM_OBJECT")
//
//            if (position != -1 && userModel != null) {
//                // 역직렬화된 userModel 객체의 데이터를 position을 이용하여 설정
//                val datalist = mutableListOf<UserModel>() // datalist는 여러 UserModel 객체를 가지고 있다고 가정
//                if (position < datalist.size) {
//                    val itemData = datalist[position]
//
//                    // UI 요소에 역직렬화된 데이터를 설정
//                    val profileImageView = findViewById<ImageView>(R.id.contact_detail_fragment_profile_icon)
//                    val nameTextView = findViewById<TextView>(R.id.contact_detail_fragment_name)
//                    val phoneNumberTextView = findViewById<TextView>(R.id.contact_detail_fragment_tv_userphone)
//                    val emailTextView = findViewById<TextView>(R.id.contact_detail_fragment_tv_useremail)
//                    val eventTextView = findViewById<TextView>(R.id.contact_detail_fragment_tv_userevent)
//
//                    profileImageView.setImageResource(itemData.profileImg)
//                    nameTextView.text = itemData.name
//                    phoneNumberTextView.text = itemData.phoneNumber
//                    emailTextView.text = itemData.email
//                    eventTextView.text = itemData.event
//                }
//            }
//        }
    }
}