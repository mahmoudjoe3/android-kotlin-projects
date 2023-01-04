package com.mahmoudjoe3.khabeertask.data.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//MobileNumber 11 digit only
// password 19 digit only
@Parcelize
data class UserModel(
    val MobileNumber:String,
    val Password:Long):Parcelable
