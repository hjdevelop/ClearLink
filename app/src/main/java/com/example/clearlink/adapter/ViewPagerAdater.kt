package com.example.clearlink.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.clearlink.model.Tabs

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = ArrayList<Tabs>()

    init {
        // 뷰페이저에 넣을 프래그먼트
        fragments.add(
            Tabs(com.example.clearlink.ContactListFragment(), "연락처")
        )
        fragments.add(
            Tabs(com.example.clearlink.MyPageFragment(), "마이 페이지")
        )
    }
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].fragment
    }

    fun getTitle(position: Int): String {
        return fragments[position].title
    }
}