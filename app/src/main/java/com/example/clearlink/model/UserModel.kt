package com.example.clearlink.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val profileImg: Int,
    val name: String,
    val phoneNumber: String,
    val email : String,
    val event :String,
    val favoritesImg: Int,
    val favorites: Boolean
):Parcelable