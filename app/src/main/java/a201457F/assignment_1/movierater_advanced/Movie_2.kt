package a201457F.assignment_1.movierater_advanced

import android.os.Parcel
import android.os.Parcelable
import kotlin.random.Random

class Movie_2 (
    var id:Int = getAutoId(),
    var name:String = "",
    var description:String = "",
    var language:String = "",
    var date:String = "",
    var below13:Boolean,
    var vulgar:Boolean,
    var violence:Boolean,

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(language)
        parcel.writeString(date)
        parcel.writeByte(if (below13) 1 else 0)
        parcel.writeByte(if (vulgar) 1 else 0)
        parcel.writeByte(if (violence) 1 else 0)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie_2> {
        override fun createFromParcel(parcel: Parcel): Movie_2 {
            return Movie_2(parcel)
        }

        override fun newArray(size: Int): Array<Movie_2?> {
            return arrayOfNulls(size)
        }
    }
}
fun getAutoId():Int{
            var random_id:Random = Random.Default
            return random_id.nextInt(100)
        }
