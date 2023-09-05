package com.example.clearlink.model

import androidx.fragment.app.Fragment

data class UserModel(
    val profileImg: Int,
    val name: String,
    val phoneNumber: String,
    val email : String,
    val favoritesImg: Int,
    val favorites: Boolean
)