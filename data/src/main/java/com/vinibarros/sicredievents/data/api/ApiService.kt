package com.vinibarros.sicredievents.data.api

import com.vinibarros.sicredievents.domain.model.Event
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("checkin")
    fun checkin(
        @Field("eventId") eventId: String,
        @Field("name") name: String,
        @Field("email") email: String
    ): Single<Response<Void>>

    @GET("events")
    fun getEvents(): Single<Response<List<Event>>>

    @GET("events/{eventId}")
    fun getEvent(
        @Path("eventId") eventId: String
    ): Single<Response<Event>>
}