package com.example.clearlink.model

import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val profileImg: Int,
    val name: String,
    val phoneNumber: String,
    val email : String,
    val favoritesImg: Int,
    val favorites: Boolean,
    val event: String
) : Parcelable