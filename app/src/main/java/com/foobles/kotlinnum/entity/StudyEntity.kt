package com.foobles.kotlinnum.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class StudyEntity(

    @SerializedName("question_type") val questionType: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("pdf_link") val pdfLink: String?,
    @SerializedName("pdf_title") val pdfTitle: String?,
    @SerializedName("video_id") val videoId: String?,
    @SerializedName("svg_icon") val svgIcon: String?,
    @SerializedName("bg_color") val bgColor: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(questionType)
        parcel.writeString(name)
        parcel.writeString(pdfLink)
        parcel.writeString(pdfTitle)
        parcel.writeString(videoId)
        parcel.writeString(svgIcon)
        parcel.writeString(bgColor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudyEntity> {
        override fun createFromParcel(parcel: Parcel): StudyEntity {
            return StudyEntity(parcel)
        }

        override fun newArray(size: Int): Array<StudyEntity?> {
            return arrayOfNulls(size)
        }
    }
}