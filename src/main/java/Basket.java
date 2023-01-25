import com.google.gson.Gson;

import java.io.*;

public class Basket {

    protected int[] prices;

    protected String[] goods;

    protected int[] quantity;

    protected int[] totalPrice = new int[3];

    protected int sumProducts = 0;

    protected Basket(int[] prices, String[] goods) {
        this.prices = prices;
        this.goods = goods;
        this.quantity = new int[goods.length];
    }

    protected Basket() {
    }

    ClientLog clientLog = new ClientLog();

    protected void addToCart(int productNum, int amount) {
        quantity[productNum] = amount;
        int currentPrice = prices[productNum] * quantity[productNum];
        totalPrice[productNum] = currentPrice;
        sumProducts = sumProducts + currentPrice;
        clientLog.log(productNum,amount);
        System.out.println("Вы добавили " + goods[productNum] + " в кол-ве " + amount + " штук(и). Итоговая цена: " + totalPrice[productNum]);
    }

    protected void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < totalPrice.length; i++) {
            if (totalPrice[i] == 0) {
                continue;
            }
            System.out.println(goods[i] + " " + quantity[i] + " шт " + prices[i] + " " + "руб/шт " + totalPrice[i] + " руб " + "в сумме");
        }
        System.out.println("Итого: " + sumProducts + " руб");
    }

    protected void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile);) {
            out.println(goods.length);
            for (int i = 0; i < goods.length; i++) {
                out.println(goods[i] + "\t" + prices[i] + "\t" + quantity[i]);
            }
        }
    }

    public void saveJson(File file) throws IOException{
        try (PrintWriter out = new PrintWriter(file);){
            Gson gson = new Gson();
            String json = gson.toJson(this);
            out.println(json);
        }
    }

    public static Basket loadFromJson(File file) throws IOException{
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));){
            Gson gson = new Gson();
            String json = bufferedReader.readLine();
            return gson.fromJson(json, Basket.class);
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        String[] goods = null;
        int[] prices = null;
        int[] quantity = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));) {
            int size = Integer.parseInt(bufferedReader.readLine());
            goods = new String[size];
            prices = new int[size];
            quantity = new int[size];

            for (int i = 0; i < size; i++) {
                String line = bufferedReader.readLine();
                String[] parts = line.split("\t");
                goods[i] = parts[0];
                prices[i] = Integer.parseInt(parts[1]);
                quantity[i] = Integer.parseInt(parts[2]);
            }
        }

        Basket basket = new Basket();
        basket.goods = goods;
        basket.prices = prices;
        basket.quantity = quantity;

        return basket;
    }
}
