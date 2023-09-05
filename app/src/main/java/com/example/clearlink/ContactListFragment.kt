package com.example.clearlink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.widget.ViewPager2
import com.example.clearlink.adapter.ContactListViewPagerAdapter
import com.example.clearlink.databinding.FragmentContactListBinding
import com.example.clearlink.model.UserModel
import com.google.android.material.tabs.TabLayoutMediator

interface ViewPagerProvider {
    fun getViewPager(): ViewPager2
}

class ContactListFragment : Fragment(), ViewPagerProvider {
    private lateinit var viewPager: ViewPager2

    // ViewPager2를 반환하는 getViewPager() 메서드 구현
    override fun getViewPager(): ViewPager2 {
        return viewPager
    }


    val MyProfile = UserModel(R.drawable.sample_0, "이호식", "010-1234-1234", "team15@gmail.com","이벤트", R.drawable.ic_star, false)

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        val bundle = bundleOf("senderKey" to MyProfile.name)
        setFragmentResult("request", bundle)
    }

    private fun initView() = with(binding) {
        // Viewpager 어댑터 설정
        val adapter = ContactListViewPagerAdapter(this@ContactListFragment)
        contactListFragmentViewpager2.adapter = adapter

        // 탭레이아웃, 뷰페이저 합치기
        TabLayoutMediator(contactListFragmentTablayout, contactListFragmentViewpager2) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()

        contactListFragmentProfileName.text = MyProfile.name
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}