package com.ymm.displaydemo.model;

import com.ymm.displaydemo.ApiConstants;
import com.ymm.lib.network.core.Call;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/5/8.
 */
public interface GitHubService {

    @GET(ApiConstants.API_USER_REPO)
//    @Headers({CustomHeaders.WITH_AUTH_FALSE
//            , CustomHeaders.SET_COOKIE_FALSE})
    Call<List<Repo>> listRepos(@Path("user") String user);
}
