package com.brm.adviceslip

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Rakhimjonov Shokhsulton on 21,февраль,2022
 * at Mayasoft LLC,
 * Tashkent, UZB.
 */
interface HttpApi {

    @GET("advice")
    fun getAdvice(): Call<Advice>
}