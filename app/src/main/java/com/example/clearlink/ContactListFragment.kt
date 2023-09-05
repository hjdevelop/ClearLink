package com.example.clearlink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clearlink.adapter.ContactListAdapter
import com.example.clearlink.databinding.FragmentContactListBinding
import com.example.clearlink.model.UserModel

class ContactListFragment : Fragment() {

    companion object {
        fun newInstance() = ContactListFragment()
    }

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        ContactListAdapter()
    }

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

        // for testww
        val testList = arrayListOf<UserModel>()
        for (i in 0 until 10) {
            testList.add(
                UserModel(
                    R.drawable.sample_1,
                    "이름",
                    R.drawable.ic_star,
                    false
                )
            )
        }

        listAdapter.addItems(testList)
    }

    private fun initView() = with(binding) {
        contactListFragmentRecyclerview.adapter = listAdapter
        contactListFragmentRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        contactListFragmentRecyclerview.addItemDecoration(
            DividerItemDecoration(
                contactListFragmentRecyclerview.context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}