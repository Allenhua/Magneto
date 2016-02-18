package allen.com.rsstest.util;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import allen.com.rsstest.model.HtmlSenderCallback;

/**
 * Created by Allen on 2016/2/14.
 */
public class OkhttpUtil {

    private OkHttpClient mClient;
    private static OkhttpUtil mokHttp;

    private OkhttpUtil(){
        mClient = new OkHttpClient();
        mClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mClient.setReadTimeout(10,TimeUnit.SECONDS);
        mClient.setWriteTimeout(10,TimeUnit.SECONDS);
    }

    public static synchronized OkhttpUtil getInstance(){
        if (mokHttp == null) {
            mokHttp = new OkhttpUtil();
        }
        return mokHttp;
    }


    public void getResponse(final HtmlSenderCallback sender, String url){
        Log.d("URL",url);
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sender.OnFailed();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String html = "";
                if (response.isSuccessful()){
                    html = response.body().string();
                    Log.d("html",html);
                    sender.onSuccess(html);
                }
            }
        });
    }

}
