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
public class HtmlParserSource2 implements HtmlParser {
    @Override
    public String getUrl(String keywords,int page) {
        String url;
        url = NetUrls.SEARCH_2 + keywords + NetUrls.SEARCH_2_PAGE + page;
        return url;
    }

    @Override
    public ArrayList parseSource(String html) {

        ArrayList<MagnetFilePojo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("blockquote");
        if (elements == null){
            return list;
        }
        for (int i = 0; i < elements.size(); i++) {
            Elements es = elements.get(i).children();
            Element first = es.first();
            //Log.d("first",first.html());
            MagnetFilePojo magnet = new MagnetFilePojo();
            magnet.setFileSize(es.last().text().substring(es.last().text().indexOf("，")+1));
            String link = first.select("a").first().attr("href");
            magnet.setFileName(first.text());
            magnet.setFileUrl(NetUrls.SEARCH_2_DETAIL+link);
            magnet.setFileMagnet(NetUrls.MAGNET_TITLE+link.substring(link.lastIndexOf("/")+1));

            list.add(magnet);
        }
        return list;
    }

    @Override
    public ArrayList getFileFromSource(String html) {
        ArrayList<FileDetailPojo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("ul").last().children();
        //Log.d("Elements",elements.html());
        if (elements == null){
            return list;
        }
        for (int i = 1; i < elements.size(); i++) {
            Element e = elements.get(i);
            Element last = e.select("small").first();
            FileDetailPojo file = new FileDetailPojo(last.text(),e.text().replace(last.text(),""));
            list.add(file);
        }
        return list;
    }
}
