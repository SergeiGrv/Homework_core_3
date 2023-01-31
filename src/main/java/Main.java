import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Молоко", "Хлеб", "Гречка"};
        int[] prices = {78, 50, 64};

        System.out.println("Список возможных доваров для покупки");

        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i] + " " + prices[i] + " руб/шт");
        }

        File file = new File("basket.json");

        File textFile = new File("basket.txt");

        File txtFile = new File("log.csv");

        DocumentBuilderFactory factory = DocumentBuilderFactory. newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse( new File("shop.xml"));

        XPath xPath = XPathFactory.newInstance().newXPath();

        boolean isLoadedEnabled = Boolean.parseBoolean(xPath
                .compile("/config/load/enabled")
                .evaluate(doc));
        String loadFileName = xPath
                .compile("/config/load/fileName")
                .evaluate(doc);
        String loadFormat = xPath
                .compile("/config/load/format")
                .evaluate(doc);

        Basket basket= new Basket(prices, products);
        ClientLog clientLog = new ClientLog();
        if(isLoadedEnabled){
            switch (loadFormat) {
                case "json" -> basket = Basket.loadFromJson(new File(loadFileName));
                case "text" -> basket = Basket.loadFromTxtFile(new File(loadFileName));
            }
        } else{
            basket = new Basket();
        }

        while (true) {
            System.out.println("Выберите товар и количество через пробел или введите `end`");
            int productNumber;
            int productCount;
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                basket.printCart();
                clientLog.exportAsCSV(txtFile);
                break;
            }

            String[] parts = input.split(" ");
            productNumber = Integer.parseInt(parts[0]) - 1;
            productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            clientLog.log(productNumber,productCount);
            basket.saveTxt(textFile);
            basket.saveJson(file);
        }

    }
}
