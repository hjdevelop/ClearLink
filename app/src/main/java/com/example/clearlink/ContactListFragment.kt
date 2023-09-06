package com.example.clearlink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.clearlink.adapter.ContactListAdapter
import com.example.clearlink.adapter.ContactListViewPagerAdapter
import com.example.clearlink.databinding.FragmentContactListBinding
import com.example.clearlink.model.UserModel
import com.google.android.material.tabs.TabLayoutMediator

class ContactListFragment : Fragment() {

    val MyProfile = UserModel(R.drawable.sample_0, "이호식", "010-1234-1234", "team15@gmail.com", R.drawable.ic_star, false, "EVENT TEXT")

    companion object {
        fun newInstance() = ContactListFragment()
    }

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

        val bundle = bundleOf("senderMyProfile" to MyProfile)
        setFragmentResult("mypage", bundle)
    }

    private fun initView() = with(binding) {
        // Viewpager 어댑터 설정
        val adapter = ContactListViewPagerAdapter(this@ContactListFragment)
        contactListFragmentViewpager2.adapter = adapter

        // 탭레이아웃, 뷰페이저 합치기
        TabLayoutMediator(contactListFragmentTablayout, contactListFragmentViewpager2) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()

        contactListFragmentProfileIcon.setImageResource(MyProfile.profileImg)
        contactListFragmentProfileName.text = MyProfile.name
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}