package allen.com.rsstest.util.html;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import allen.com.rsstest.pojo.FileDetailPojo;
import allen.com.rsstest.pojo.MagnetFilePojo;
import allen.com.rsstest.util.NetUrls;

/**
 * Created by Allen on 2016/3/12.
 */
public class HtmlParserSource4 implements HtmlParser {
    @Override
    public String getUrl(String keywords,int page) {
        String url = NetUrls.SEARCH_4 + keywords + NetUrls.SEARCH_4_PAGE + page;
        return url;
    }

    @Override
    public ArrayList parseSource(String html) {

        ArrayList<MagnetFilePojo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);

        Elements elements;

        Element element = document.getElementsByClass("data-list").first();

        if (element == null){
            return list;
        }
        elements = element.getElementsByClass("row");
        if (elements == null){
            return list;
        }

        for (int i = 1; i< elements.size(); i++){

            Element e = elements.get(i).select("a").first();
            Log.d("e",elements.get(i).html());

            MagnetFilePojo magnet = new MagnetFilePojo();
            magnet.setFileName(e.child(0).text());
            magnet.setFileUrl("http:"+e.attr("href"));
            magnet.setFileMagnet(NetUrls.MAGNET_TITLE +
                    e.attr("href").substring(e.attr("href").length()-40));


            magnet.setFileSize(e.child(1).text());
            Log.d("magnet",magnet.toString());
            list.add(magnet);
        }

        return list;
    }

    @Override
    public ArrayList getFileFromSource(String html) {
        ArrayList<FileDetailPojo> list = new ArrayList<>();
        Document doucument = Jsoup.parse(html);
        Element element = doucument.getElementsByAttributeValueContaining("class","detail data-list").last();

        if (element == null){
            return list;
        }
        Elements elements = element.getElementsByClass("row");

        if (elements == null){
            return list;
        }
        for (int i = 1; i < elements.size(); i++) {
            Element e = elements.get(i);
            FileDetailPojo file = new FileDetailPojo(e.child(1).text(),e.child(0).text());

            list.add(file);
        }

        return list;
    }
}
