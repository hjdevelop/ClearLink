package com.example.clearlink.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val profileImg: Uri?,
    val name: String,
    val phoneNumber: String,
    val email : String,
    var favoritesImg: Int,
    var favorites: Boolean,
    val memo: String,
    val event: Int
) : Parcelable