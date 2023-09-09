package com.example.clearlink


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
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
    private var contactList: ArrayList<Map<String, String>>? = null

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
        ContactListAdapter(datalist)
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



        // 데이터 전달 로직
        listAdapter.itemClick2 = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val intent = Intent(view.context, ContactDetailFragment::class.java)
                intent.putExtra("item", datalist[position])

                view.context.startActivity(intent) // 액티비티 시작

            }

        }


        listAdapter.itemClick = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                datalist[position].favorites = !datalist[position].favorites

                listAdapter.notifyDataSetChanged()

                setFragmentResult("requestKey", bundleOf("item" to datalist))
            }
        }

        setFragmentResultListener("favorite") { requestKey, bundle ->
            val removedata = bundle.getParcelableArrayList<UserModel>("removedata")

            Log.d("Inremovedata", removedata.toString())

            if (removedata != null) {
                for (data in removedata) {
                    Log.d("Indata", data.toString())
                    val poisition = datalist.indexOf(data)
                    Log.d("Inposition", poisition.toString())
                    datalist[poisition].favorites = false
                    listAdapter.updateItem(poisition)
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
                                setFragmentResult("deleteKey", bundleOf("item" to item))
                                listAdapter.deleteItem(position)
                                datalist.removeAt(position)
                                passData(datalist.size)
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

    @SuppressLint("Range")
    private fun initView() = with(binding) {


        //오류 2개 : 연락처에 입력한 메모가 없으면 작동 오류, 버튼 누르면 계속 추가됨..ㅎ

        contactListInnerFragmentFab.setOnClickListener {

            contactList?.clear()

            // MainActivity.kt 파일 내에서 권한 요청
            val permission = Manifest.permission.READ_CONTACTS
            val requestCode = 123 // 원하는 요청 코드

            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
            } else {
                // 권한이 이미 허용되었을 때 실행할 작업을 여기에 추가

                contactList = ArrayList()

                //Content Provider : 어플리케이션 사이에서 Data 를 공유하는 통로 역할, 안드로이드 시스템의 각종 설정값이나 DB 에 접근
                //contentResovler : 결과를 반환하는 브릿지 역할은 컨텐트 리졸버(Content Resolver)

                //Cursor : 데이터베이스에 저장되어 있는 테이블의 행을 참조하여 데이터 가져옴
                //query() : 데이터베이스로부터 데이터를 조회할 때 사용
                //ContactsContract.Contacts.CONTENT_URI : 주소록의 연락처 항목
                //ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc" : 데이터 정렬 방식, 이름을 오름 차순
                val c: Cursor? = requireContext().contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null, null, null,
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc"
                )

                //커서를 다음 항목으로 이동, 항목이 있는 경우에만 실행
                while (c?.moveToNext() == true) {
                    val map = HashMap<String, String>()
                    // 각 연락처 항목의 고유한 식별자인 "아이디(ID)"를 가져오는 코드
                    val id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID))

                    //이름 가져오기
                    val name =
                        c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                    map["name"] = name

                    //ContactsContract.CommonDataKinds.Phone.CONTENT_URI : 연락처의 전화 정보
                    //projection : 열 가져오는 필터, null : 모든 열
                    //ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, : 특정 연락처의 전화번호, 해당 연락처의 ID와 일치하는 데이터만 검색
                    val phoneCursor: Cursor? = requireContext().contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null, null
                    )

                    //첫번째 행으로 이동, 행이 존재하면 true : 해당 연락처가 전화번호를 가지고 있는 경우
                    if (phoneCursor?.moveToFirst() == true) {
                        //전화번호 가져오기
                        val number =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        map["phone"] = number
                    }

                    phoneCursor?.close()

                    // 이메일 정보 가져오기
                    val emailCursor: Cursor? = requireContext().contentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id,
                        null, null
                    )

                    if (emailCursor?.moveToFirst() == true) {
                        val email =
                            emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                        map["email"] = email
                    }

                    emailCursor?.close()

                    // 메모 정보 가져오기
                    val memoCursor: Cursor? = requireContext().contentResolver.query(
                        ContactsContract.Data.CONTENT_URI,
                        null,
                        ContactsContract.Data.CONTACT_ID + " = ? AND " +
                                ContactsContract.Data.MIMETYPE + " = ?",
                        arrayOf(id, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE),
                        null
                    )

                    if (memoCursor != null) {
                        if (memoCursor.moveToFirst()) {
                            val memo =
                                memoCursor.getString(memoCursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE))
                            map["memo"] = memo
                        } else {
                            map["memo"] = "" // 데이터가 없을 때 빈 문자열로 초기화
                        }
                        memoCursor.close()
                    } else {
                        map["memo"] = "" // memoCursor가 null인 경우 빈 문자열로 초기화
                    }

                    memoCursor?.close()

                    contactList?.add(map)
                }
                c?.close()

                Log.d("dataList", contactList.toString())

                for (data in contactList!!) {
                    datalist.add(
                        UserModel(
                            Uri.parse("android.resource://" + "com.example.clearlink" + "/" + R.drawable.sample_0),
                            data["name"].toString(),
                            data["phone"].toString(),
                            data["email"].toString(),
                            R.drawable.ic_star,
                            false,
                            data["memo"].toString(),
                            0
                        )
                    )
                }

                Log.d("userlist", datalist.toString())

                val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallback(listAdapter))
                itemTouchHelper.attachToRecyclerView(binding.contactListInnerFragmentRecyclerview)

                contactListInnerFragmentRecyclerview.adapter = listAdapter
                contactListInnerFragmentRecyclerview.layoutManager =
                    GridLayoutManager(requireContext(), 1)
                contactListInnerFragmentRecyclerview.addItemDecoration(
                    DividerItemDecoration(
                        contactListInnerFragmentRecyclerview.context,
                        LinearLayoutManager.VERTICAL
                    )
                )

                passData(datalist.size)
            }
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}