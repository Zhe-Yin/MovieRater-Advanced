package a201457F.assignment_1.movierater_advanced

import android.os.Parcel
import android.os.Parcelable

class RatingModel(
    val movieid:Int,
    val rating: Float,
    val message: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(movieid)
        parcel.writeFloat(rating)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RatingModel> {
        override fun createFromParcel(parcel: Parcel): RatingModel {
            return RatingModel(parcel)
        }

        override fun newArray(size: Int): Array<RatingModel?> {
            return arrayOfNulls(size)
        }
    }
}