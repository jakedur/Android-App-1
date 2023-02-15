package com.example.lifecyclemanagement

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class User(
    var firstName: String?,
    val middleName: String?,
    var lastName: String?,
    var profilePicture: Bitmap?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Bitmap::class.java.classLoader)
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(middleName)
        parcel.writeString(lastName)
        parcel.writeParcelable(profilePicture, flags)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

object UserSingleton {
    init {}

    var UserObject: User? = null
        get() = field
        set(value) {
            field = value
        }
}
//class UserViewModel : ViewModel() {
//    val currentUser: MutableLiveData<User> by lazy {
//        MutableLiveData<User>()
//    }
//}

//class User {
//    private var firstName: String? = null // The user's first name
//        get() = field
//        set(value) {
//            field = value
//        }
//    private var middleName: String? = null // The user's middle name
//        get() = field
//        set(value) {
//            field = value
//        }
//    private var lastName: String? = null // The user's last name
//        get() = field
//        set(value) {
//            field = value
//        }
//}