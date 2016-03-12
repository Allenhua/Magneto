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
public class HtmlParserMp4 implements HtmlParser {
    @Override
    public String getUrl(String keywords,int page) {
        String url;
        if (page == 1){
            url = NetUrls.MP4_INDEX;
        }else url = NetUrls.MP4_INDEX + NetUrls.MP4_PAGE + page;
        return url;
    }

    @Override
    public ArrayList parseSource(String html) {

        ArrayList<MagnetFilePojo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("#data_list");
        if (elements == null) {
            return list;
        }
        elements = elements.first().children();
        elements.select("tr#ad_list_middle").remove();
        for (Element e: elements) {
            Elements es = e.select("td");
            if (es.size() > 2){
                String url = es.get(2).select("a").attr("href");
                String mage = url.substring(url.indexOf("=")+1);

                MagnetFilePojo magnet = new MagnetFilePojo();
                magnet.setFileName(e.child(2).text());
                magnet.setFileUrl("http://mp4ba.com/"+url);
                magnet.setFileSize(es.get(0).text()+"  "+es.get(3).text());
                magnet.setFileMagnet(NetUrls.MAGNET_TITLE+mage);
                list.add(magnet);
            }

        }
        return list;
    }

    @Override
    public ArrayList getFileFromSource(String html) {
        ArrayList<FileDetailPojo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div.torrent_files");
        if (elements == null) {
            return list;
        }
        elements = elements.select("ul").first().select("li").first().select("li");
        for (Element e:elements) {
            String s = e.select("span").text();
            FileDetailPojo file = new FileDetailPojo(s,e.text().replace("s",""));
            list.add(file);
        }
        return list;
    }
}
