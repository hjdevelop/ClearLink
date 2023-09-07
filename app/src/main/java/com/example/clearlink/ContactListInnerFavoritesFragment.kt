package com.example.clearlink

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
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
        val testList = arrayListOf<UserModel>()
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

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val item = bundle.getParcelable<UserModel>("item")
            val position = bundle.getInt("position", 0)

//            listAdapter.addItemNS(item)

            item?.let {
                if (it !in testList) {
                    testList.add(it)
                    listAdapter.addItemNS(it)
                }
            }

            Log.d("item", "$item")
            Log.d("position", "$position")
        }


    }

    override fun onResume() {
        super.onResume()
        Log.d("test3 : resume","")
    }

    override fun onPause() {
        super.onPause()
        Log.d("test4 : Pause","")
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