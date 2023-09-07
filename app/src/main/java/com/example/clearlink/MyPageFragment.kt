package com.example.clearlink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.example.clearlink.databinding.FragmentMyPageBinding


class MyPageFragment : Fragment() {

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

        setFragmentResultListener("mypage") { key, bundle ->
            bundle.getParcelable<UserModel>("senderMyProfile")?.let { myProfile ->
                myPageFragmentProfileIcon.setImageURI(myProfile.profileImg)
                myPageFragmentName.text = myProfile.name
                myPageFragmentTvUserphone.text = myProfile.phoneNumber
                myPageFragmentTvUseremail.text = myProfile.email
                myPageFragmentTvUserevent.text = myProfile.event
            }
        }

        myPageFragmentBtnContactlist.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity.binding.tabLayout.getTabAt(0)?.select()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}