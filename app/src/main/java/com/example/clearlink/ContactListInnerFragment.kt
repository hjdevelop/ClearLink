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
import com.example.clearlink.databinding.FragmentContactListInnerBinding
import com.example.clearlink.model.UserModel


class ContactListInnerFragment : Fragment() {

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

        // 더미데이터 생성
        val datalist = arrayListOf<UserModel>()
        datalist.add( UserModel(R.drawable.sample_0,"김김김","010-0000-0000","aaa@aaa.com","a입니다.",R.drawable.ic_star,true))
        datalist.add( UserModel(R.drawable.sample_1,"이이이","010-1111-1111","bbb@bbb.com","b입니다.",R.drawable.ic_star,false))
        datalist.add( UserModel(R.drawable.sample_2,"박박박","010-2222-2222","ccc@ccc.com","c입니다.",R.drawable.ic_star,false))
        datalist.add( UserModel(R.drawable.sample_3,"최최최","010-3333-3333","ddd@ddd.com","d입니다.",R.drawable.ic_star,true))
        datalist.add( UserModel(R.drawable.sample_4,"정정정","010-4444-4444","eee@eee.com","e입니다.",R.drawable.ic_star,true))
        datalist.add( UserModel(R.drawable.sample_5,"차차차","010-5555-5555","fff@fff.com","f입니다.",R.drawable.ic_star,false))
        datalist.add( UserModel(R.drawable.sample_6,"조조조","010-6666-6666","ggg@ggg.com","g입니다.",R.drawable.ic_star,false))
        datalist.add( UserModel(R.drawable.sample_7,"장장장","010-7777-7777","hhh@hhh.com","h입니다.",R.drawable.ic_star,true))
        datalist.add( UserModel(R.drawable.sample_8,"추추추","010-8888-9999","yyy@yyy.com","y입니다.",R.drawable.ic_star,true))
        datalist.add( UserModel(R.drawable.sample_9,"손손손","010-8888-9999","xxx@xxx.com","x입니다.",R.drawable.ic_star,false))

        listAdapter.addItems(datalist)

        listAdapter.itemClick = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val intent = Intent(view.context, ContactDetailFragment::class.java)
                intent.putExtra("item", datalist[position])


                view.context.startActivity(intent) // 액티비티 시작


            }
        }
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
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}