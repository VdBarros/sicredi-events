package com.vinibarros.sicredievents.data.api

import com.vinibarros.sicredievents.domain.model.Checkin
import com.vinibarros.sicredievents.domain.model.Event
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("checkin")
    fun checkin(
        @Body params: Checkin
    ): Single<Response<Void>>

    @GET("events")
    fun getEvents(): Single<Response<List<Event>>>

    @GET("events/{eventId}")
    fun getEvent(
        @Path("eventId") eventId: String
    ): Single<Response<Event>>
}