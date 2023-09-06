package com.example.clearlink

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
import com.example.clearlink.model.UserModel
import com.google.android.material.tabs.TabLayoutMediator

class ContactListInnerFragment : Fragment() {

    companion object {
        fun newInstance() = ContactListInnerFragment()
    }

    private var _binding: FragmentContactListInnerBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        ContactListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListInnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        // for testww
        val testList = arrayListOf<UserModel>()


        listAdapter.addItems(testList)
    }

    private fun initView() = with(binding) {

        contactListInnerFragmentRecyclerview.adapter = listAdapter
        contactListInnerFragmentRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        contactListInnerFragmentRecyclerview.addItemDecoration(
            DividerItemDecoration(
                contactListInnerFragmentRecyclerview.context,
                LinearLayoutManager.VERTICAL
            )
        )
        contactListInnerFragmentFab.setOnClickListener {
            val dialog = AddContactDialog()
            dialog.show(requireActivity().supportFragmentManager, "AddContactDialog")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}