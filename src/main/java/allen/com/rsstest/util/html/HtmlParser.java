package allen.com.rsstest.util.html;

import java.util.ArrayList;

/**
 * Created by Allen on 2016/3/11.
 */
public interface HtmlParser {
    String getUrl(String keywords,int page);
    ArrayList parseSource(String html);
    ArrayList getFileFromSource(String html);
}
