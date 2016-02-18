package allen.com.rsstest.model;

/**
 * Created by Allen on 2016/2/15.
 */
public interface HtmlSenderCallback {
    void onSuccess(String html);
    void OnFailed();
}
