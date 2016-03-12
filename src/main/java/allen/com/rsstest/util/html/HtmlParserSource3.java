package allen.com.rsstest.util.html;

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
public class HtmlParserSource3 implements HtmlParser {
    @Override
    public String getUrl(String keywords,int page) {
        String url = NetUrls.SEARCH_3 + keywords + NetUrls.SEARCH_3_PAGE + page;
        return url;
    }

    @Override
    public ArrayList parseSource(String html) {
        ArrayList<MagnetFilePojo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("search-item");
        //Log.d("Search_Item",elements.html());
        if (elements == null){
            return list;
        }
        for (int i = 0; i < elements.size(); i++) {
            Element es = elements.get(i);
            Element first = es.getElementsByClass("item-title").first();
            Elements lasts = es.getElementsByClass("item-bar").get(0).children();
            String link = es.select("a").first().attr("href");


            MagnetFilePojo magnet = new MagnetFilePojo();
            magnet.setFileName(first.text());
            magnet.setFileMagnet(NetUrls.MAGNET_TITLE + link.substring(1,41));
            magnet.setFileUrl(NetUrls.SEARCH_3 + link.substring(1));
            magnet.setFileSize(lasts.get(1).text()+lasts.get(3).text());
            list.add(magnet);
        }

        return list;
    }

    @Override
    public ArrayList getFileFromSource(String html) {
        ArrayList<FileDetailPojo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("ol").first().children();
        //Log.d("elements",elements.html());
        if (elements == null){
            return list;
        }
        for (Element e:elements) {
            Element es = e.select("span").first();
            FileDetailPojo file = new FileDetailPojo(es.text(),e.text().replace(es.text(),""));

            list.add(file);
        }
        return list;
    }
}
