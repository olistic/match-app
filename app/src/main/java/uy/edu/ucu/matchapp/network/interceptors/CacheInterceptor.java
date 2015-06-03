package uy.edu.ucu.matchapp.network.interceptors;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class CacheInterceptor implements Interceptor {
    private static final int MAX_AGE = 60 * 60;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.header("Cache-Control") == null) {
            request = request.newBuilder()
                    .addHeader("Cache-Control", "public, max-age=" + MAX_AGE)
                    .build();
        }

        Response response = chain.proceed(request);
        return response.newBuilder()
                .header("Cache-Control", "public")
                .build();
    }
}
