package allen.com.rsstest.util;

import android.util.Log;



import java.io.IOException;
import java.util.concurrent.TimeUnit;

import allen.com.rsstest.model.HtmlSenderCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Allen on 2016/2/14.
 */
public class OkhttpUtil {

    private OkHttpClient mClient;
    private static OkhttpUtil mokHttp;

    private OkhttpUtil(){
        mClient = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .followRedirects(true)
                .build();
    }

    public static synchronized OkhttpUtil getInstance(){
        if (mokHttp == null) {
            mokHttp = new OkhttpUtil();
        }
        return mokHttp;
    }


    public void excuteConnect(final HtmlSenderCallback sender, String url ,Object tag){
        Request request = new Request.Builder().tag(tag).url(url).build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!call.isCanceled()){
                    call.cancel();
                }
                sender.OnFailed();
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                String html = "";
                if (response.isSuccessful()){
                    html = response.body().string();
                    sender.onSuccess(html);
                }
                response.body().close();
            }
        });
    }

    public void cancelAll(){
        if (mClient == null)
            return;
        mClient.dispatcher().cancelAll();
    }

    //取消正在执行的任务或者正在排队的任务
    public boolean cancelCall(Object tag){
        if (tag == null)
            return true;

        if (mClient == null) return true;

        synchronized (mClient.dispatcher().getClass()){


            for (Call call: mClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) call.cancel();
            }

            for (Call call: mClient.dispatcher().runningCalls()){
                if (tag.equals(call.request().tag())) call.cancel();
            }
        }
        return true;
    }
}
