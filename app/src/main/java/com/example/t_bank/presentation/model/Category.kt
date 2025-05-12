package com.example.t_bank.presentation.model

import com.example.t_bank.data.local.entity.CategoryEntity
import android.os.Parcel
import android.os.Parcelable

data class Category(
    val name: String,
    val iconResId: Int,
    var colorResId: Int,
    var percentage: Float
) : Parcelable {

    // Конструктор для чтения из Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "", // name
        parcel.readInt(),         // iconResId
        parcel.readInt(),         // colorResId
        parcel.readFloat()        // percentage
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(iconResId)
        parcel.writeInt(colorResId)
        parcel.writeFloat(percentage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }

    fun toEntity(id: Int = 0): CategoryEntity {
        return CategoryEntity(
            id = id,
            name = name,
            iconResId = iconResId,
            colorResId = colorResId,
            percentage = percentage
        )
    }
}