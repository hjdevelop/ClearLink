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
    private val MESSAGE_REQUEST_CODE = 2
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

        //메세지 보내기 버튼

        val messageButton = findViewById<Button>(R.id.contact_detail_fragment_btn_message)

        messageButton.setOnClickListener {
            val phoneNumber = item.phoneNumber

            // SEND_SMS 권한을 확인합니다.
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SEND_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 권한을 요청합니다.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    MESSAGE_REQUEST_CODE
                )
            } else {
                // 권한이 이미 허용된 경우 메시지를 보내기 위한 Intent를 생성합니다.
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("smsto:$phoneNumber") // smsto: 프로토콜을 사용하여 SMS 메시지를 보냅니다

                // 메시지를 보내기 위한 액티비티를 시작합니다.
                startActivity(intent)
            }
        }
    }
}