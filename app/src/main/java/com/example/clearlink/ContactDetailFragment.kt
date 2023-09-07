package com.example.clearlink

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.clearlink.databinding.ActivityContactDetailFragmentBinding
import com.example.clearlink.model.UserModel

class ContactDetailFragment : AppCompatActivity() {

    private lateinit var binding : ActivityContactDetailFragmentBinding
    private val CALL_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<UserModel>("item") as UserModel

        binding.contactDetailFragmentProfileIcon.setImageURI(
            item.profileImg)
        binding.contactDetailFragmentName.text = item.name
        binding.contactDetailFragmentTvUserphone.text=item.phoneNumber
        binding.contactDetailFragmentTvUseremail.text=item.email
        binding.contactDetailFragmentTvUserevent.text=item.event

        //뒤로가기 버튼
        val myButton = findViewById<Button>(R.id.contact_detail_fragment_btn)
        myButton.setOnClickListener {
            finish()
        }

        //전화걸기 버튼
        val callButton = findViewById<Button>(R.id.contact_detail_fragment_btn_phonecall)

        callButton.setOnClickListener {
            val phoneNumber = item.phoneNumber // 전화를 걸고자 하는 전화번호

            // 권한확인
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 권한요청
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    CALL_REQUEST_CODE
                )
            } else {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:$phoneNumber")

                startActivity(intent)
            }
        }
    }
}