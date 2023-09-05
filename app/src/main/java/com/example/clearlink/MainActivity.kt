package com.example.clearlink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clearlink.adapter.ViewPagerAdapter
import com.example.clearlink.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(this)
    }

    private val tabIcon = arrayListOf(
        R.drawable.ic_contact_list,
        R.drawable.ic_mypage
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Viewpager 어댑터 설정
        binding.viewPager2.adapter = viewPagerAdapter

        // 탭레이아웃, 뷰페이저 합치기
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
            tab.setIcon(tabIcon[position])
        }.attach()


    }
}