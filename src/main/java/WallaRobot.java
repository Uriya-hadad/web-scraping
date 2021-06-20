import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WallaRobot extends BaseRobot {
    public WallaRobot() {
        super("https://www.walla.co.il/");
    }

    @Override
    public Map<String, Integer> getWordsStatistics() throws IOException {
        Map<String, Integer> map = new HashMap<>();
        String url;
        ArrayList<String> sitesUrl = new ArrayList<>();
        Document article, walla = Jsoup.connect(getRootWebsiteUrl()).get();
        //teasers section
        for (Element teasers : walla.getElementsByClass("with-roof ")) {
            sitesUrl.add(teasers.child(0).attributes().get("href"));
        }
        //TODO:news section
            System.out.println(walla.html());
        for (Element a : walla.getElementsByTag("a")) {
            System.out.println(a);
        }
        for (Element news : walla.getElementsByClass("css-hlk4c3 full-width full-width-no-narrow")) {
            for (Element bigItem : news.getElementsByClass("big-item")) {

            }
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
