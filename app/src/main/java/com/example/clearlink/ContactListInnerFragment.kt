package com.example.clearlink

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clearlink.adapter.ContactListAdapter
import com.example.clearlink.databinding.FragmentContactListInnerBinding
import com.example.clearlink.model.UserModel

class ContactListInnerFragment : Fragment() {

    companion object {
        fun newInstance() = ContactListInnerFragment()
    }

    private var _binding: FragmentContactListInnerBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        ContactListAdapter()
    }

    private val testList = arrayListOf<UserModel>()

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

        // for test
        for (i in 0 until 2) {
            testList.add(
                UserModel(
                    Uri.parse("android.resource://" + context?.packageName + "/" + R.drawable.sample_0),
                    "연락처 ${i+1}",
                    "010-1234-1234",
                    "team15@gmail.com",
                    R.drawable.ic_star,
                    false,
                    "event text"
                )
            )
        }

        listAdapter.addItems(testList)

//         별 클릭시 즐겨찾기에 연락처 추가 로직
        listAdapter.itemClick = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val item = testList[position]
                val position = position
                if(!item.favorites) {
                    setFragmentResult("requestKey", bundleOf("item" to item, "position" to position))
                }
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        // 별 클릭시 즐겨찾기에 연락처 추가 로직
//        listAdapter.itemClick = object : ContactListAdapter.ItemClick {
//            override fun onClick(view: View, position: Int) {
//                val item = testList[position]
//                val position = position
//                if(!item.favorites) {
//                    setFragmentResult("requestKey", bundleOf("item" to item, "position" to position))
//                }
//            }
//        }
//    }

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
            dialog.setDialogResult(object : AddContactDialog.DailogResult {
                override fun finish(result: UserModel) {
                    listAdapter.addItem(result)
                    testList.add(result)
                }
            })
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}