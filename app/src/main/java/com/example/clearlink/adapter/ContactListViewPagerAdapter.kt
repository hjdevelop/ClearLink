package com.example.clearlink.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.clearlink.ContactListFragment
import com.example.clearlink.ContactListInnerFavoritesFragment
import com.example.clearlink.ContactListInnerFragment
import com.example.clearlink.model.Tabs
class ContactListViewPagerAdapter(fragmentActivity: ContactListFragment) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = ArrayList<Tabs>()

    init {
        // 뷰페이저에 넣을 프래그먼트
        fragments.add(
            Tabs(ContactListInnerFragment(), "연락처")
        )
        fragments.add(
            Tabs(ContactListInnerFavoritesFragment(), "즐겨찾기")
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
