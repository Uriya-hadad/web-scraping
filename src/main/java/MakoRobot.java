import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class MakoRobot extends BaseRobot implements MapOrder {
    private Map<String, Integer> map = new HashMap<>();

    public MakoRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);
    }

    @Override
    public Map<String, Integer> getWordsStatistics() throws IOException {
        String url, begging = "https://www.mako.co.il/";
        ArrayList<String> sitesUrl = new ArrayList<>();
        Document mako = Jsoup.connect(getRootWebsiteUrl()).get();
        //teasers section
        for (Element teasers : mako.getElementsByClass("teasers")) {
            for (Element child : teasers.children()) {
                url = child.child(0).child(0).attributes().get("href");
                if (url.contains(begging)) {
                    sitesUrl.add(url);
                } else {
                    sitesUrl.add(begging + url);
                }
            }
        }
        //news section
        for (Element news : mako.getElementsByClass("neo_ordering scale_image horizontal news")) {
            for (Element h5 : news.getElementsByTag("h5")) {
                url = h5.child(0).attributes().get("href");
                if (url.contains(begging)) {
                    sitesUrl.add(url);
                } else {
                    sitesUrl.add(begging + url);
                }
            }
        }

        //access the sites
        for (String site : sitesUrl) {
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
    public int countInArticlesTitles(String text) {
        return 0;
    }

    @Override
    public String getLongestArticleTitle() {
        return null;
    }

    public String accessSite(String site) throws IOException {
        Document article;
        StringBuilder siteTextBuilder = new StringBuilder();
        article = Jsoup.connect(site).get();
        //title
        siteTextBuilder.append(article.getElementsByTag("h1").get(0).text());
        siteTextBuilder.append(" ");
        //sub-title
        siteTextBuilder.append(article.getElementsByTag("h2").text());
        //article body
        Element articleBody = article.getElementsByClass("article-body").get(0);
        for (Element p : articleBody.getElementsByTag("p")) {
            siteTextBuilder.append(" ");
            siteTextBuilder.append(p.text());
        }
        //getting all the words
        return siteTextBuilder.toString();
       }
}
