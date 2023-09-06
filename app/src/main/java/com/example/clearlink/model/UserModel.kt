package com.example.clearlink.model

import android.net.Uri
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize
import java.net.URI
import java.net.URL

@Parcelize
data class UserModel(
    val profileImg: Uri,
    val name: String,
    val phoneNumber: String,
    val email : String,
    val favoritesImg: Int,
    val favorites: Boolean,
    val event: String
) : Parcelable