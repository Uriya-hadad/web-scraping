import java.io.IOException;
import java.util.Scanner;

public class Driver {
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int points = 0;
        BaseRobot site = getSiteSelection();
        for (int i = 0; i < 5; i++) {
            points += guessCommonWords(site);
        }
        String userText = getHeadlinesText();
        System.out.println("how many time it will appears?:");
        int quantity = scanner.nextInt();
        try {
            points += chuckText(quantity, userText, site);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("you achieved " + points + " points!");

    }

    private static int chuckText(int quantity, String userText, BaseRobot site) throws IOException {
        int realQuantity = site.countInArticlesTitles(userText);
            System.out.println(realQuantity);
        if (Math.abs(quantity - realQuantity) <= 2) {
            return 250;
        }
        return 0;
    }

    private static String getHeadlinesText() {
        String userText = "";
        while (userText.length() < 1 || userText.length() > 20) {
            System.out.println("Enter any text that you think should appear in the headlines on the site,");
            System.out.println("the text should be between 1 and 20 chars:");
            userText = scanner.nextLine();
        }
        return userText;
    }

    private static BaseRobot getSiteSelection() {
        int input = 0;
        while (input < 1 || input > 3) {
            System.out.println("which site do you want to scan?");
            System.out.println("\t1. Mako");
            System.out.println("\t2. Ynet");
            System.out.println("\t3. Walla");
            input = scanner.nextInt();
        }
        //clear buffer
        scanner.nextLine();
        //input must be between 1 to 3 so i can return null in case it isn't
        switch (input) {
            case 1:
                try {
                    return new MakoRobot();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case 2:
                try {
                    return new YnetRobot();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            default:
                WallaRobot walla = new WallaRobot();
                return new WallaRobot();
        }
    }

    private static int guessCommonWords(BaseRobot site) {
        String guess;
        int points = 0;
        try {
            String longestArticle = site.getLongestArticleTitle();
            System.out.println("Please guess what the most common words on the site are?");
            System.out.println("hint:\n" + longestArticle);
            guess = scanner.nextLine();
            points = site.countInArticlesTitles(guess);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }

}
