package com.example.clearlink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clearlink.adapter.ContactListViewPagerAdapter
import com.example.clearlink.databinding.FragmentContactListBinding
import com.example.clearlink.databinding.FragmentMyPageBinding
import com.example.clearlink.model.UserModel
import com.google.android.material.tabs.TabLayoutMediator
import java.text.DecimalFormat

class MyPageFragment : Fragment() {

    val MyProfile = UserModel(R.drawable.sample_0, "이호식", "010-1234-1234", "team15@gmail.com", R.drawable.ic_star, false)

    companion object {
        fun newInstance() = MyPageFragment()
    }

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        myPageFragmentProfileIcon.setImageResource(MyProfile.profileImg)
        myPageFragmentName.text = MyProfile.name
        myPageFragmentTvUserphone.text = MyProfile.phoneNumber
        myPageFragmentTvUseremail.text = MyProfile.email

        myPageFragmentBtnContactlist.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}