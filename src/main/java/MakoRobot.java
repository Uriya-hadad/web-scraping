import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MakoRobot extends BaseRobot implements MapOrder {
    private Map<String, Integer> map = new HashMap<>();
    private final ArrayList<String> sitesUrl;

    public MakoRobot() throws IOException {
        super("https://www.mako.co.il/");
        String url, begging = "https://www.mako.co.il/";
        sitesUrl = new ArrayList<>();
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
    }

    @Override
    public Map<String, Integer> getWordsStatistics() throws IOException {
        //access the sites
        for (String site : sitesUrl) {
            //String for storage the whole text of the site
            String siteText;
            siteText = accessSite(site);
            siteText = correctWords(siteText);
            String[] wordsOfArticle = siteText.split(" ");
            map = getWordsIntoMap(wordsOfArticle, map);
        }
        return map;
    }


    @Override
    public int countInArticlesTitles(String text) throws IOException {
        Document mako = Jsoup.connect(getRootWebsiteUrl()).get();
        int count = 0;
        for (Element element : mako.getElementsByTag("span")) {
            for (Element title : element.getElementsByAttributeValue("data-type", "title")) {
                if (title.text().contains(text)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String getLongestArticleTitle() throws IOException {
        Document article;
        String longestArticleTitle = "";
        int longest = 0;
        for (String site : sitesUrl) {
            article = Jsoup.connect(site).get();
            //title
            String title = article.getElementsByTag("h1").get(0).text();
            //article body
            StringBuilder siteTextBuilder = new StringBuilder();
            Element articleBody = article.getElementsByClass("article-body").get(0);
            for (Element p : articleBody.getElementsByTag("p")) {
                siteTextBuilder.append(p.text());
            }
            if (longest < siteTextBuilder.length()) {
                longest = siteTextBuilder.length();
                longestArticleTitle = title;
            }
        }
        return longestArticleTitle;
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
