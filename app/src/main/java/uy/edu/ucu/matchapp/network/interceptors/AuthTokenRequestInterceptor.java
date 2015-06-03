package uy.edu.ucu.matchapp.network.interceptors;

import retrofit.RequestInterceptor;

public class AuthTokenRequestInterceptor implements RequestInterceptor {
    private static final String AUTH_HEADER = "X-Auth-Token";
    private static final String AUTH_TOKEN = "d61a70c311d54b6d8f654aabf9e3817c";

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader(AUTH_HEADER, AUTH_TOKEN);
    }
}
