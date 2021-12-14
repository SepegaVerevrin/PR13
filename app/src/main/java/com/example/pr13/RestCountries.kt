package com.example.pr13

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.Serializable

class Country:Serializable
{
    @SerializedName("name")
    @Expose
    var name:String?=null

    @SerializedName("capital")
    @Expose
    var capital:String?=null

    @SerializedName("region")
    @Expose
    var region:String?=null

    @SerializedName("subregion")
    @Expose
    var subregion:String?=null

    @SerializedName("population")
    @Expose
    var population:Int?=null

}

public interface RestCountries {
    @GET("/v2/currency/{currency}") // /rest/v2/currency/
    fun getCountriesByCurrency(@Path("currency") currency:String): Call<List<Country>>
}

























