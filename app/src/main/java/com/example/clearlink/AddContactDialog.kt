package com.example.clearlink

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.clearlink.databinding.DialogAddContactBinding
import com.example.clearlink.model.UserModel
import java.util.regex.Pattern

class AddContactDialog : DialogFragment() {
    private var _binding: DialogAddContactBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null

    interface DailogResult{
        fun finish(result: UserModel)
    }

    fun setDialogResult(dialogResult: DailogResult) {
        DialogResult = dialogResult
    }

    private var DialogResult : DailogResult? = null

    val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == RESULT_OK && it.data != null) {
            val uri = it.data!!.data

            Glide.with(this)
                .load(uri)
                .into(binding.addContactDialogProfileCircleImageView)
            imageUri = uri
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogAddContactBinding.inflate(inflater, container, false)
        val view = binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.addContactDialogAddTextView.setOnClickListener {
            val intent = Intent(Intent(ACTION_PICK))
            intent.type = "image/*"
            activityResult.launch(intent)
        }

        binding.addContactDialogCancelButton.setOnClickListener {
            dismiss()
        }

        binding.addContactDialogSaveButton.setOnClickListener {
            val name = binding.addContactDialogNameEditText.text.toString()
            val phonenumber = binding.addContactDialogTelEditText.text.toString()
            val email = binding.addContactDialogEmailEditText.text.toString()
            val event = binding.addContactDialogEventEditText.text.toString()

            if(imageUri == null){
                val UserProfile = UserModel(Uri.parse("android.resource://" + context?.packageName + "/" + R.drawable.ic_mypage), name, phonenumber, email, R.drawable.ic_star, false, event)
                DialogResult?.finish(UserProfile)
            }else{
                val UserProfile = UserModel(imageUri, name, phonenumber, email, R.drawable.ic_star, false, event)
                DialogResult?.finish(UserProfile)
            }

            dismiss()
        }

        binding.addContactDialogNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkData()
            }
        })

        binding.addContactDialogTelEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkData()
            }
        })

        binding.addContactDialogEmailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkData()
                checkEmail()
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this@AddContactDialog, 0.9f, 0.9f)
    }

    private fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width: Float, height: Float) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {

            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialogFragment.dialog?.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()
            window?.setLayout(x, y)

        } else {

            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }

    private fun checkData() {
        val name = binding.addContactDialogNameEditText.text.toString().trim()
        val phoneNumber = binding.addContactDialogTelEditText.toString().trim()
        val email = binding.addContactDialogEmailEditText.text.toString().trim()

        binding.addContactDialogSaveButton.isEnabled = name.isNotEmpty() && phoneNumber.isNotEmpty() && email.isNotEmpty()
    }

    private fun checkEmail() : Boolean {
        val email = binding.addContactDialogEmailEditText.text.toString().trim()
        val patternCheck = Pattern.matches(emailValidation, email)

        if(patternCheck) {
            binding.addContactDialogEmailCheckTextView.visibility = View.INVISIBLE
            binding.addContactDialogSaveButton.isEnabled = true
            return true
        }
        else {
            binding.addContactDialogEmailCheckTextView.visibility = View.VISIBLE
            binding.addContactDialogSaveButton.isEnabled = false
            return false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
