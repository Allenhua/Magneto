package allen.com.rsstest.util.html;

import allen.com.rsstest.model.HtmlSenderCallback;
import allen.com.rsstest.util.OkhttpUtil;

/**
 * Created by Allen on 2016/3/11.
 */
public class HtmlParseFactory {

    private static OkhttpUtil okhttpUtil = OkhttpUtil.getInstance();

    public static void excuteConnect(HtmlSenderCallback htSender, String url){
        okhttpUtil.getResponse(htSender,url);
    }

    public static HtmlParser getHtmlParser(int position){
        HtmlParser htmlParser = null;
        switch (position){
            case 0:
                htmlParser = new HtmlParserSource1();
                break;
            case 1:
                htmlParser = new HtmlParserSource2();
                break;
            case 2:
                htmlParser = new HtmlParserSource3();
                break;
            case 3:
                htmlParser = new HtmlParserSource4();
                break;
            case 9:
                htmlParser = new HtmlParserMp4();
                break;
        }
        return htmlParser;
    }
}
