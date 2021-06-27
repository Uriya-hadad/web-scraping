								README

Purpose of the exercise:
Build a program by using the jsoup library, a program that extracts information from popular Israeli news sites.
We defend three classes:
The MakoRobot class - carves information from the mako news site.
The WallaRobot class - carves information from the walla news site.
The YnetRobot class - carves information from the Ynet news site.
All the three classes are extending an abstract class called BaseRobot, this class contain three methods and one field:
In the class there is a field called rootWebsiteUrl, which is initialized with a link to the home page of the news site.
The getWordsStatistics method, will navigate to each one of the news articles within the home page of the site.
There she will take all the content of the article (title + subheading + body of the article) and count how many occurrences each word has.
When done, the method will create a map pairs of words and their occurrences in all the news articles on the site.
The countInArticlesTitles method accepts a specific text (letter, word or sentence), counts how many times this text appears in the titles and subheadings of the website at the home page and returns the numeric value.
The getLongestArticleTitle method will navigate to each of the site's articles and return the title of the article whose content (article body only) is the longest.

What the program does?
The Driver class implements a guessing game:
The program will ask the user which site he would like to scan from the three sites (Mako, Walla, Ynet).
Depending on the user's response, the web scrapping operation will be performed.
The program will ask the user to guess what the most common words on the site are and show him the title of the longest article on the site, as a hint.
If the word appears on the site(even if it's in some article and not in the main page), the user receives a number of points as the number of occurrences of the word on the site.
The user gets five guesses in order to score as many points as possible.
Then the user will be asked to enter some text, which he thinks should appear in the headlines of the articles on the site. This text can be 1-20 characters long. When entering the text, you will be asked to guess how many times the text appears in the headings. If he hits a number up to an accuracy of 2 or less, he gets another 250 points.
Finally, the user will be printed at how many points finished the game.
