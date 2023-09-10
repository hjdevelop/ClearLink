package com.example.clearlink

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.Manifest
import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        binding.contactDetailFragmentTvUsermemo.text=item.memo

        //뒤로가기 버튼
        val myButton = findViewById<Button>(R.id.contact_detail_fragment_btn)
        myButton.setOnClickListener {
            finish()
        }

        //메세지 보내기 버튼
        val messageButton = findViewById<Button>(R.id.contact_detail_fragment_btn_message)

        messageButton.setOnClickListener {
            val phoneNumber = item.phoneNumber

            // SEND_SMS 권한을 확인
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
                // 권한이 이미 허용된 경우 메시지를 보내기 위한 Intent를 생성
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("smsto:$phoneNumber") // smsto: 프로토콜을 사용하여 SMS 메시지를 보내기

                // 메시지를 보내기 위한 액티비티를 시작
                startActivity(intent)
            }
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

        //이메일 보내기 버튼 (권한 필요 x)
        val emailButton = findViewById<Button>(R.id.contact_detail_fragment_btn_email)

        emailButton.setOnClickListener {
            val recipient = item.email // 수신자 이메일 주소
            val subject = "제목"
            val message = "이메일 내용"

            // 이메일 앱을 열기 위한 Intent를 생성
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:${item.email}") // 이메일 앱을 열기 위한 Uri

            // 수신자 이메일 주소, 제목 및 내용을 추가
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, message)

            try {
                // 이메일 앱을 실행
                startActivity(emailIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "이메일 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}