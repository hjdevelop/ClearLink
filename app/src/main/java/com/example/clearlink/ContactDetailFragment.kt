package com.example.clearlink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.clearlink.databinding.FragmentContactDetailBinding
import com.example.clearlink.model.UserModel


class ContactDetailFragment : Fragment() {

    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {

        val selectedItemIndex = arguments?.getInt("ITEM_INDEX", -1)
        val selectedItem = arguments?.getParcelable<UserModel>("ITEM_OBJECT")

        // 데이터 배치
        if (selectedItemIndex != null && selectedItemIndex != -1 && selectedItem != null) {
            selectedItem?.let {
                contactDetailFragmentProfileIcon.setImageResource(it.profileImg)
                contactDetailFragmentName.text=it.name
                contactDetailFragmentTvPhoneNumber.text=it.phoneNumber
                contactDetailFragmentTvUseremail.text=it.email
                contactDetailFragmentTvUserevent.text=it.event
            }
        }

        if (selectedItem != null) {

            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager2)
            val newFragment = ContactDetailFragment() //  표시하려는 프래그먼트

            // 프래그먼트 전환
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(viewPager.id, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}