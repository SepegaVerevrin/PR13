package com.example.pr13

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serializable

class Supernova(
):Serializable {
    @SerializedName("name")
    @Expose
    var name:String?=null
}

public interface RestSupernovae {
    @GET("catalog?{str}") //
    fun getSupernovaeByRaDecRadius( @Query("str") radius:String): Call<Map<String,Map<String,Array<Supernova>>>>
}

























