package com.soundbread.history.data.network;

/**
 * Created by fruitbites on 2017-09-10.
 */


import com.soundbread.history.data.network.model.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fruitbites on 2017-09-03.
 */

public interface SearchHelper {
    @GET("/search/{dic}/{query}")
    Observable<SearchResponse> getSearchResponse(@Path("dic") String dic, @Path("query") String query, @Query("format") String format, @Query("count") int count, @Query("start") int start);

}
