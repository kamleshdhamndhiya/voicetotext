package com.test.speechtotext.utility;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {
    private static Retrofit retrofit;
    private static LoadInterface loadInterface;

    public static Retrofit getClient() {
        try {
            if (retrofit == null) {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
                    Request original = chain.request();
                    // Request customization: add request headers
                            Request.Builder requestBuilder = original.newBuilder()
                             .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "")
                            .addHeader("Cookie", "__cf_bm=d.BNney6HdHkeMcaOojV180XPLxl2sEZzQzzfXbpDbo-1713981588-1.0.1.1-je9Q5gjrx1kHjeV7k2Lt.9tuzR.M3aJBpwPc.4KydeOK5z.8jSi0S6nZ2ZSEO0fYUCzjV3C5Jx8JXdduSIuI_A; _cfuvid=EsUKFafBEQ1HKRwrAkpYgV6Py4g6Zuv.KEs.5TQBWDk-1713981588864-0.0.1.1-604800000")
                                    ;
                     Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS)
                        .writeTimeout(100, TimeUnit.SECONDS)
                        .cache(null)
                        .build();
                retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        } catch (Exception e) {
        }
        return retrofit;
    }


    public static LoadInterface getLoadInterface() {
        if (loadInterface == null)
            loadInterface = AppConfig.getClient().create(LoadInterface.class);
        return loadInterface;
    }

    public static LoadInterface api_Interface() {
        return AppConfig.getClient().create(LoadInterface.class);
    }
}
