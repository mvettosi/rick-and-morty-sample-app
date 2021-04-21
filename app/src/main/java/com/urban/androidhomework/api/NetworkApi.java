package com.urban.androidhomework.api;

import com.urban.androidhomework.domain.Character;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface NetworkApi {

    @GET("character/")
    Single<Response<Character>> getAllCharacters();

}
