package com.example.clearlink


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clearlink.adapter.ContactListAdapter
import com.example.clearlink.databinding.FragmentContactListInnerBinding
import com.example.clearlink.model.UserModel


interface DataPassListener {
    fun onDataPassed(data: Int)
}

class ContactListInnerFragment : Fragment() {



    private var dataPassListener: DataPassListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 활동(Activity)로부터 인터페이스 구현체를 가져옴
        dataPassListener = context as DataPassListener
    }

    // 데이터를 전달하는 함수
    private fun passData(data: Int) {
        dataPassListener?.onDataPassed(data)
    }

    private var _binding: FragmentContactListInnerBinding? = null
    private val binding get() = _binding!!

    val datalist = arrayListOf<UserModel>()

    private val listAdapter by lazy {
        ContactListAdapter()
    }
    companion object {
        private var count = -1
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

        datalist.add(
            UserModel(
                Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_0),
                "김김김",
                "010-0000-0000",
                "aaa@aaa.com",
                R.drawable.ic_star,
                false,
                "a입니다."
            )
        )
        datalist.add(
            UserModel(
                Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_1),
                "이이이",
                "010-1111-1111",
                "bbb@bbb.com",
                R.drawable.ic_star,
                false,
                "b입니다."
            )
        )
        datalist.add(
            UserModel(
                Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_2),
                "박박박",
                "010-2222-2222",
                "ccc@ccc.com",
                R.drawable.ic_star,
                false,
                "c입니다."
            )
        )
        datalist.add(
            UserModel(
                Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_3),
                "최최최",
                "010-3333-3333",
                "ddd@ddd.com",
                R.drawable.ic_star,
                false,
                "d입니다."
            )
        )
        datalist.add(
            UserModel(
                Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_4),
                "정정정",
                "010-4444-4444",
                "eee@eee.com",
                R.drawable.ic_star,
                false,
                "e입니다."
            )
        )
        datalist.add(
            UserModel(
                Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_5),
                "차차차",
                "010-5555-5555",
                "fff@fff.com",
                R.drawable.ic_star,
                false,
                "f입니다."
            )
        )
        datalist.add(
            UserModel(
                Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_6),
                "조조조",
                "010-6666-6666",
                "ggg@ggg.com",
                R.drawable.ic_star,
                false,
                "g입니다."
            )
        )
        datalist.add(
            UserModel(
                Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_7),
                "장장장",
                "010-7777-7777",
                "hhh@hhh.com",
                R.drawable.ic_star,
                false,
                "h입니다."
            )
        )
        datalist.add(
            UserModel(
                Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_8),
                "추추추",
                "010-8888-9999",
                "yyy@yyy.com",
                R.drawable.ic_star,
                false,
                "y입니다."
            )
        )
        datalist.add(
            UserModel(
                Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_9),
                "손손손",
                "010-8888-9999",
                "xxx@xxx.com",
                R.drawable.ic_star,
                false,
                "x입니다."
            )
        )

        passData(datalist.size)

        listAdapter.addItems(datalist)

        // 데이터 전달 로직
        listAdapter.itemClick2 = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val intent = Intent(view.context, ContactDetailFragment::class.java)
                intent.putExtra("item", datalist[position])

                view.context.startActivity(intent) // 액티비티 시작

            }

        }

        // 별 클릭시 즐겨찾기에 연락처 추가 로직
        listAdapter.itemClick = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val item = datalist[position]
                val position = position
                if(!item.favorites) {
                    count += 1
                    setFragmentResult("requestKey", bundleOf("item" to item, "position" to position))
                }
            }
        }

        // 롱 클릭시 연락처 삭제 로직
        listAdapter.itemLongClick = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("연락처 삭제")
                builder.setMessage("정말 삭제하시겠습니까?")
                builder.setIcon(R.drawable.ic_clearlink)

                val listener = object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        when (p1) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                val item = datalist[position]
                                setFragmentResult("deleteKey", bundleOf("item" to item, "count" to count))
                                listAdapter.deleteItem(position)
                                datalist.removeAt(position)
                            }
                            DialogInterface.BUTTON_NEGATIVE -> return
                        }
                    }
                }
                builder.setPositiveButton("삭제", listener)
                builder.setNegativeButton("취소", null)
                builder.show()


            }
        }
    }

    private fun initView() = with(binding) {

        val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallback(listAdapter))
        itemTouchHelper.attachToRecyclerView(binding.contactListInnerFragmentRecyclerview)

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
                    datalist.add(result)
                    passData(datalist.size)
                }
            })
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}