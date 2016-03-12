package allen.com.rsstest.util;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import allen.com.rsstest.model.HtmlSenderCallback;
import allen.com.rsstest.pojo.FileDetailPojo;
import allen.com.rsstest.pojo.MagnetFilePojo;

/**
 * Created by Allen on 2016/2/14.
 */
public class HtmlParseUtil{
    private String keywords;
    private int position;//搜索引擎源
    private int page = 1;
    private OkhttpUtil okhttpUtil;


    public HtmlParseUtil() {
        okhttpUtil = OkhttpUtil.getInstance();
    }

    public HtmlParseUtil(String keywords, int position) {
        okhttpUtil = OkhttpUtil.getInstance();
        this.keywords = keywords;
        this.position = position;
    }

    public void setPage(int page){
        this.page = page;
    }

    public String getUrl(){
        String url = "";
        switch (position){
            case 0:
                url = NetUrls.SEARCH_1 + keywords + NetUrls.SEARCH_1_PAGE + page;
                break;
           case 1:
                url = NetUrls.SEARCH_2 + keywords + NetUrls.SEARCH_2_PAGE + page;
                break;
            case 2:
                url = NetUrls.SEARCH_3 + keywords + NetUrls.SEARCH_3_PAGE + page;
                break;
            case 3:
                url = NetUrls.SEARCH_4 + keywords + NetUrls.SEARCH_4_PAGE + page;
                break;
            case 9:
                if (page == 1){
                    url = NetUrls.MP4_INDEX;
                }else url = NetUrls.MP4_INDEX + NetUrls.MP4_PAGE + page;
                break;
        }
        Log.d("URL",url);
        return url;
    }

    public void excuteConnect(HtmlSenderCallback htSender, String url){
        okhttpUtil.getResponse(htSender,url);
    }

    public static ArrayList parseSource1(String html){

        ArrayList<MagnetFilePojo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        //Log.d("document",document.body().html());
        Elements elements = document.getElementsByClass("search-item");
        if (elements == null){
            return list;
        }
        //Log.d("Document",elements.html());
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
    public static ArrayList parseSource2(String html){
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

    public static ArrayList parseSource3(String html){
        //Log.d("HTMLPARSER",html);
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

    public static ArrayList parseSource4(String html){
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

    public static ArrayList parseMp4ba(String html){
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

    public static ArrayList getFileFromSource1(String html){
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
    public static ArrayList getFileFromSource2(String html){
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
            Log.d("e",e.html());
            FileDetailPojo file = new FileDetailPojo(last.text(),e.text().replace(last.text(),""));
            Log.d("file",file.toString());
            list.add(file);
        }
        return list;
    }
    public static ArrayList getFileFromSource3(String html){
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
    public static ArrayList getFileFromSource4(String html){
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

    public static ArrayList getFileFromMp4ba(String html){
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
