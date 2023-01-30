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

    protected void addToCart(int productNum, int amount) {
        quantity[productNum] = amount;
        int currentPrice = prices[productNum] * quantity[productNum];
        totalPrice[productNum] = currentPrice;
        sumProducts = sumProducts + currentPrice;
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
}
