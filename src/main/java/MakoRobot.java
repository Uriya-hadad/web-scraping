import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;

public class MakoRobot extends BaseRobot {
    public MakoRobot(String rootWebsiteUrl) {
        super(rootWebsiteUrl);
    }

    @Override
    public Map<String, Integer> getWordsStatistics() throws IOException {
        Map<String, Integer> map = new HashMap<>();
        String url, begging = "https://www.mako.co.il/";
        ArrayList<String> sitesUrl = new ArrayList<>();
        Document article, mako = Jsoup.connect(getRootWebsiteUrl()).get();
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
            //string for storage the whole text of the site
            String siteText = "";
            StringBuilder siteTextBuilder = new StringBuilder(siteText);
            article = Jsoup.connect(site).get();
            //title
            siteTextBuilder.append(article.getElementsByTag("h1").get(0).text());
            //sub-title
            siteTextBuilder.append(article.getElementsByTag("h2").text());
            //article body
            Element articleBody = article.getElementsByClass("article-body").get(0);
            for (Element p : articleBody.getElementsByTag("p")) {
                siteTextBuilder.append(p.text());
            }
            //getting all the words
            char[] symbols = {',', '.', ':'};
            int size = siteTextBuilder.length();
            System.out.println(siteTextBuilder);
            for (int j=0,i = 0; i < size; j++,i++) {
                for (char symbol : symbols) {
                    if (siteTextBuilder.charAt(j) == symbol ||
                            (siteTextBuilder.charAt(j) == '"' &&
                                    (siteTextBuilder.charAt(j - 1) == ' ' ||
                                            siteTextBuilder.charAt(j + 1) == ' '))) {
                        siteTextBuilder.deleteCharAt(j);
                        i++;
                        j--;
                    }

                }
            }
            siteText += siteTextBuilder.toString();
            System.out.println(Arrays.toString(siteText.split(" ")));
            new Scanner(System.in).next();
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
