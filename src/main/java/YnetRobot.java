import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.net.ssl.SSLHandshakeException;


public class YnetRobot extends BaseRobot {
    public static void main(String[] args) {
//        new YnetRobot("https://www.ynet.co.il/home/0,7340,L-8,00.html").getWordsStatistics();
        try {
            new WallaRobot("https://www.walla.co.il/").getWordsStatistics();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public YnetRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);
    }

    @Override
    public Map<String, Integer> getWordsStatistics() {
        //TODO check id to do every article
        String title = "mainTitle",
                subTitle = "subTitle";
        Document document;
        String rrr = "";
        Map<String, Integer> map = null;
        try {
            document = Jsoup.connect(getRootWebsiteUrl()).get();
            for (Element slotTitle : document.getElementsByClass("slotTitle")) {
                for (Element a : slotTitle.getElementsByTag("a")) {
                    rrr = a.attributes().get("href");
                    Document currentSite = Jsoup.connect(a.attributes().get("href")).get();
                    if (currentSite.getElementsByClass(title).text().equals(""))
                        System.out.println(a.attributes().get("href"));
                    else {
                        map = new HashMap<>();
                        String fullArticle = "";
                        fullArticle += currentSite.getElementsByClass(title).text();
                        fullArticle += currentSite.getElementsByClass(subTitle).text();
                        StringBuilder fullArticleBuilder = new StringBuilder(fullArticle);
                        for (Element text : currentSite.getElementsByClass("text_editor_paragraph rtl")) {
                            fullArticleBuilder.append(text.getElementsByTag("span").text());
                        }
                        fullArticle = fullArticleBuilder.toString();
                        for (String s : fullArticle.split(" ")) {
                            if (!map.containsKey(s))
                                map.put(s,1);
                            else
                                map.replace(s,(map.get(s)+1));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("*****************");
            System.out.println("*****************");
            System.out.println("*****************");
            System.out.println("*****************");
            System.out.println(rrr);
        }
        return map;
    }

    @Override
    public int countInArticlesTitles(String text) {
        return 0;
    }

    @Override
    public String getLongestArticleTitle() {
        return null;
    }
}
