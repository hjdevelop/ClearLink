package com.example.clearlink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.clearlink.databinding.ActivityContactDetailFragmentBinding
import com.example.clearlink.model.UserModel

class ContactDetailFragment : AppCompatActivity() {

    private lateinit var binding : ActivityContactDetailFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<UserModel>("item") as UserModel

        binding.contactDetailFragmentProfileIcon.setImageResource(item.profileImg)
        binding.contactDetailFragmentName.text = item.name
        binding.contactDetailFragmentTvUserphone.text=item.phoneNumber
        binding.contactDetailFragmentTvUseremail.text=item.email
        binding.contactDetailFragmentTvUserevent.text=item.event

        val myButton = findViewById<Button>(R.id.contact_detail_fragment_btn)


        myButton.setOnClickListener {
            finish()
        }

    }
}