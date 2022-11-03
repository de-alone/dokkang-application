package edu.skku.cs.dokkang;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestAPICaller {
    public static final MediaType JsonType = MediaType.get("application/json; charset=utf-8");
    private String token = null;

    public RestAPICaller() {
    }

    public RestAPICaller(String token) {
        this.token = token;
    }

    public void get(String uri, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(uri).newBuilder();
        String url = urlBuilder.build().toString();
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (token != null) {
            requestBuilder = requestBuilder.addHeader("Authorization", "Bearer " + token);
        }

        Request request = requestBuilder.get().build();
        client.newCall(request).enqueue(callback);
    }

    public void post(String uri, String body, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(uri).newBuilder();
        String url = urlBuilder.build().toString();
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (token != null) {
            requestBuilder = requestBuilder.addHeader("Authorization", "Bearer " + token);
        }

        Request request = requestBuilder.post(RequestBody.create(body, JsonType)).build();
        client.newCall(request).enqueue(callback);
    }

    public void put(String uri, String body, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(uri).newBuilder();
        String url = urlBuilder.build().toString();
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (token != null) {
            requestBuilder = requestBuilder.addHeader("Authorization", "Bearer " + token);
        }

        Request request = requestBuilder.put(RequestBody.create(body, JsonType)).build();
        client.newCall(request).enqueue(callback);
    }

    public static final class ApiCallback<T> implements Callback {
        private final Activity activity;
        private final Class<T> responseTypeClass;
        private final Consumer<T> successConsumer;
        private Consumer<T> failureConsumer = null;

        public ApiCallback (Activity activity, Class<T> responseTypeClass, Consumer<T> successConsumer) {
            this.activity = activity;
            this.responseTypeClass = responseTypeClass;
            this.successConsumer = successConsumer;
        }

        public ApiCallback (Activity activity, Class<T> responseTypeClass, Consumer<T> successConsumer, Consumer<T> failureConsumer) {
            this.activity = activity;
            this.responseTypeClass = responseTypeClass;
            this.successConsumer = successConsumer;
            this.failureConsumer = failureConsumer;
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "failed to request to " + call.request().url(), Toast.LENGTH_SHORT).show();
                }
            });
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            if (response.isSuccessful()) {
                Gson gson = new GsonBuilder().create();
                final T data = gson.fromJson(response.body().string(), responseTypeClass);

                successConsumer.accept(data);
            } else {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "failed to process request to " + call.request().url(), Toast.LENGTH_SHORT).show();
                    }
                });

                if (failureConsumer != null) {
                    Gson gson = new GsonBuilder().create();
                    final T data = gson.fromJson(response.body().string(), responseTypeClass);
                    failureConsumer.accept(data);
                }
            }
        }
    }
}
