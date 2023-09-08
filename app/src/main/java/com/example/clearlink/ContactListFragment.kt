package com.example.clearlink

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.clearlink.adapter.ContactListViewPagerAdapter
import com.example.clearlink.databinding.FragmentContactListBinding
import com.example.clearlink.model.UserModel
import com.google.android.material.tabs.TabLayoutMediator

class ContactListFragment : Fragment(){

    val MyProfile = UserModel(Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_2), "이호식", "010-1234-1234", "team15@gmail.com", R.drawable.ic_star, false, "MEMO TEXT", 0)


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

        contactListFragmentProfileIcon.setImageURI(MyProfile.profileImg)
        contactListFragmentProfileName.text = MyProfile.name
//        contactListFragmentFriends.text = "친구 (${receiveData()}명)"
    }

    // ContactListFragment에서 데이터 수신
    fun receiveDataFromInnerFragment(bundle: Bundle): Int {
        val size = bundle.getInt("key")
        return size
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun receiveData(data: Int) {
        binding.contactListFragmentFriends.text = "친구 (${data}명)"
        Log.d("Contact data", data.toString())
    }

}