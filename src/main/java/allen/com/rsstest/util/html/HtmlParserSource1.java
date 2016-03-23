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
public class HtmlParserSource1 implements HtmlParser {
    @Override
    public String getUrl(String keywords,int page) {
        String url = NetUrls.SEARCH_1 + keywords + NetUrls.SEARCH_1_PAGE + page;
        return url;
    }

    @Override
    public ArrayList parseSource(String html) {
        ArrayList<MagnetFilePojo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("search-item");
        if (elements == null){
            return list;
        }
        for (int i = 0; i < elements.size(); i++) {
            Elements es = elements.get(i).children();
            // Log.d("ElementsFor",es.html());
            MagnetFilePojo magnet = new MagnetFilePojo();
            Element first = es.select("a").first();
            Elements bs = es.select("b");
            magnet.setFileName(first.text());
            es.get(2).select("a[href]").remove();
            magnet.setFileSize(bs.text());
            String ht = first.attr("href");
            //Log.d("ht",ht);
            magnet.setFileMagnet(NetUrls.MAGNET_TITLE+ ht.substring(ht.lastIndexOf("/")+1,ht.lastIndexOf("/")+41));
            magnet.setFileUrl(NetUrls.SEARCH_1_DETAIL+ ht);

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
            //Log.d("File",file.toString());
            list.add(file);
        }
        return list;
    }

}
