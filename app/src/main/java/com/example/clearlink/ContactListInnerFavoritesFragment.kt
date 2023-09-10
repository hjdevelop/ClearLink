package com.example.clearlink

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clearlink.ContactListInnerFragment.Companion.deleteList
import com.example.clearlink.adapter.ContactListAdapter
import com.example.clearlink.databinding.FragmentContactListInnerFavoritesBinding
import com.example.clearlink.model.UserModel

class ContactListInnerFavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = ContactListInnerFavoritesFragment()
        val testList = arrayListOf<UserModel>()
        var sendList = arrayListOf<UserModel>()
        val testSet = mutableSetOf<UserModel>()
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

        setFragmentResultListener("deleteKey") { deleteKey, bundle ->
                val item = bundle.getParcelable<UserModel>("item")

                for ( i in 0 until deleteList.size) {
                    testSet.add(deleteList[i])
                }







                deleteList.clear()


                if (item != null) {
                    val position = testList.indexOf(item)
                    listAdapter.deleteItem(position)
                }
            }

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val itemList = bundle.getParcelableArrayList<UserModel>("item")!!
            Log.d("FitemList", itemList.toString())
            sendList.clear()

            if (itemList != null) {
                for (item in itemList) {
                    if (item.favorites == true && item !in testList) {
                        testList.add(item)
                        listAdapter.addFItem(item)
                    } else if (item.favorites == false) {
                        testList.remove(item)
                        listAdapter.deleteFItem(item)
                    }
                }
            }
            Log.d("testList", testList.toString())
        }

        listAdapter.addItems(testList)

        listAdapter.itemClick = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                sendList.add(listAdapter.getlist(position))

                setFragmentResult("favorite", bundleOf("removedata" to sendList))
                Log.d("sendlist", sendList.toString())

                testList.remove(listAdapter.getlist(position))
                Log.d("testList", testList.toString())
                listAdapter.deleteItem(position)
            }
        }

        listAdapter.itemClick2 = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val intent = Intent(view.context, ContactDetailFragment::class.java)
                intent.putExtra("item", testList[position])

                view.context.startActivity(intent) // 액티비티 시작
            }
        }
    }


//    override fun onPause() {
//        super.onPause()
//        setFragmentResultListener("deleteKey") { deleteKey, bundle ->
//            val item = bundle.getParcelable<UserModel>("item")
//            val count = bundle.getInt("count", 0)
//
//            item?.let {
//                if (it in testList) {
//                    testList.removeAt(count)
//                    listAdapter.deleteItem(count)
//                }
//            }
//
//            Log.d("test : count", "$count")
//        }
//    }

    private fun initView() = with(binding) {

        contactListInnerFavoritesFragmentRecyclerview.adapter = listAdapter
        contactListInnerFavoritesFragmentRecyclerview.layoutManager = GridLayoutManager(requireContext(), 1)
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