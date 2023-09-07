package com.example.clearlink

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clearlink.adapter.ContactListAdapter
import com.example.clearlink.adapter.ContactListViewPagerAdapter
import com.example.clearlink.databinding.FragmentContactListBinding
import com.example.clearlink.databinding.FragmentContactListInnerBinding
import com.example.clearlink.databinding.FragmentContactListInnerFavoritesBinding
import com.example.clearlink.model.UserModel
import com.google.android.material.tabs.TabLayoutMediator

class ContactListInnerFavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = ContactListInnerFavoritesFragment()
    }

    private var _binding: FragmentContactListInnerFavoritesBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        ContactListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListInnerFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        // for testww
        val testList = arrayListOf<UserModel>()
        for (i in 0 until 3) {
            testList.add(
                UserModel(
                    R.drawable.sample_2,
                    "즐겨찾기"+i,
                    "010-1234-1234",
                    "team15@gmail.com",
                    "event",
                    R.drawable.ic_full_start,
                    true
                )
            )
        }

        listAdapter.addItems(testList)

        listAdapter.addItems(testList)

        listAdapter.itemClick = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val intent = Intent(view.context, ContactDetailFragment::class.java)
                intent.putExtra("item", testList[position])


                view.context.startActivity(intent) // 액티비티 시작


            }
        }
    }

    private fun initView() = with(binding) {

        contactListInnerFavoritesFragmentRecyclerview.adapter = listAdapter
        contactListInnerFavoritesFragmentRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        contactListInnerFavoritesFragmentRecyclerview.addItemDecoration(
            DividerItemDecoration(
                contactListInnerFavoritesFragmentRecyclerview.context,
                LinearLayoutManager.VERTICAL
            )
        )

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}