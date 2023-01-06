import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Молоко", "Хлеб", "Гречка"};
        int[] prices = {78, 50, 64};

        System.out.println("Список возможных доваров для покупки");

        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i] + " " + prices[i] + " руб/шт");
        }

        File file = new File("basket.bin");

        Basket basket1 = Basket.loadFromBinFile(new File("basket.bin"));

        Basket basket = new Basket(prices, products);

        while (true) {
            System.out.println("Выберите товар и количество через пробел или введите `end`");
            int productNumber;
            int productCount;
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                basket.printCart();
                break;
            }

            String[] parts = input.split(" ");
            productNumber = Integer.parseInt(parts[0]) - 1;
            productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);

            basket.saveBin(file);

        }

    }
}
