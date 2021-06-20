import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.net.ssl.SSLHandshakeException;


public class YnetRobot extends BaseRobot implements MapOrder{
    public static void main(String[] args) throws IOException{
//        new YnetRobot("https://www.ynet.co.il/home/0,7340,L-8,00.html").getWordsStatistics();
//            new WallaRobot("https://www.walla.co.il/").getWordsStatistics();
//            new MakoRobot("https://www.mako.co.il/").getWordsStatistics();
        System.out.println(new YnetRobot("https://www.ynet.co.il/home/0,7340,L-8,00.html").countInArticlesTitles("hi"));

    }

    public YnetRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);
    }

    private Map<String, Integer> map = new HashMap<>();

    @Override
    public Map<String, Integer> getWordsStatistics() throws IOException {
        String url;
        ArrayList<String> sitesUrl = new ArrayList<>();
        Document article, ynet = Jsoup.connect(getRootWebsiteUrl()).get();
        //teasers section
        for (Element slotTitle : ynet.getElementsByClass("TopStory1280Componenta basic")) {
            Element element = slotTitle.getElementsByClass("slotTitle").get(0);
            url = element.child(0).attributes().get("href");
            sitesUrl.add(url);
        }
        for (Element teasers : ynet.getElementsByClass("YnetMultiStripComponenta oneRow multiRows")) {
            for (Element textDiv : teasers.getElementsByClass("textDiv")) {
                url = textDiv.child(1).attributes().get("href");
                sitesUrl.add(url);
            }
        }
        //news section
        for (Element slotsContent : ynet.getElementsByClass("MultiArticleRowsManualComponenta").get(0).getElementsByClass("slotsContent")) {
            for (Element slotTitle_medium : slotsContent.getElementsByClass("slotTitle medium")) {
                url = slotTitle_medium.child(0).attributes().get("href");
                sitesUrl.add(url);
            }
            for (Element slotTitle_small : slotsContent.getElementsByClass("slotTitle small")) {
                url = slotTitle_small.child(0).attributes().get("href");
                sitesUrl.add(url);
            }
        }
        //access the sites
        for (String site : sitesUrl) {
            //TODO chuck if the ynet plus is updated


            //String for storage the whole text of the site
            String siteText;
            siteText = accessSite(site);
            siteText = correctWords(siteText);
            String[] wordsOfArticle = siteText.split(" ");
            map = getWordsIntoMap(wordsOfArticle,map);
        }
        return map;
    }

    @Override
    public int countInArticlesTitles(String text) throws IOException {
        int count = 0;
        Document document = Jsoup.connect(getRootWebsiteUrl()).get();
        for (Element slotTitle_small : document.getElementsByClass("slotTitle small")) {
            if(slotTitle_small.text().contains(text)){
                count++;
            }
        }
        System.out.println("*******************");
        for (Element slotTitle_medium : document.getElementsByClass("slotTitle medium")) {
            if(slotTitle_medium.text().contains(text)){
                count++;
            }
        }
        if (document.getElementsByClass("slotSubTitle").get(0).text().contains(text)){
            count++;
        }
        if(document.getElementsByClass("slotTitle").get(0).text().contains(text)){
            count++;
        }
        return count;
    }

    @Override
    public String getLongestArticleTitle() {
        return null;
    }

    @Override
    public String accessSite(String site) throws IOException {
        Document article;
        //String for storage the whole text of the site
        String siteText = "";
        StringBuilder siteTextBuilder = new StringBuilder(siteText);
        article = Jsoup.connect(site).get();
        //title
        siteTextBuilder.append(article.getElementsByClass("mainTitle").text());
        siteTextBuilder.append(" ");
        //sub-title
        siteTextBuilder.append(article.getElementsByClass("subTitle").text());
        siteTextBuilder.append(" ");
        //article body
        for (Element text_editor_paragraph_rtl : article.getElementsByClass("text_editor_paragraph rtl")) {
            siteTextBuilder.append(" ");
            siteTextBuilder.append(text_editor_paragraph_rtl.getElementsByTag("span").text());
        }
        return siteTextBuilder.toString();
    }
}
