package com.example.pr13

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serializable

class Supernova(
    @SerializedName("name")
    @Expose
    var name:Array<String>?=null
):Serializable {
}

class Message:Serializable {
    @SerializedName("message")
    @Expose
    val message:String?=null
}

public interface RestSupernovae {
    @GET("catalog") //
    fun getSupernovaeByRaDecRadius( @Query("ra") ra:String,@Query("dec") dec:String,@Query("radius") radius:String): Call<Map<String,Supernova>>
    //fun getSupernovaeByRaDecRadius( @Query("ra") ra:String,@Query("dec") dec:String,@Query("radius") radius:String): Call<Message>

}

























