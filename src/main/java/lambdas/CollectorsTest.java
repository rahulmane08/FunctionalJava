package lambdas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

class Item {

    private String name;
    private int qty;
    private BigDecimal price;
    private List<String> varities = new ArrayList<>();
    private List<String> categories = new ArrayList<>();

    public Item(String name, int qty, BigDecimal price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public int getQty() {
        return qty;
    }

    public Item setQty(int qty) {
        this.qty = qty;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Item setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public List<String> getVarities() {
        return varities;
    }

    public Item addVariety(String i) {
        if(i!=null)
            varities.add(i);
        return this;
    }

    public List<String> getCategories() {
        return categories;
    }

    public Item addCategory(String category) {
        if(category != null)
            categories.add(category);
        return this;
    }
}

class Result {
    private String category;
    private String itemName;
    private Integer price;

    public String getItemName() {
        return itemName;
    }

    public Result setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public Result setPrice(BigDecimal price) {
        this.price = price.intValue();
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Result setCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "category='" + category + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                '}';
    }
}

public class CollectorsTest {
    public static void main(String[] args) {
        Item apple =  new Item("apple", 10, new BigDecimal("10"));
        apple.addVariety("California").addVariety("Kashmiri");
        apple.addCategory("Fruits").addCategory("Vegetables");

        Item mango =  new Item("mango", 10, new BigDecimal("500"));
        mango.addVariety("Alphonso").addVariety("Raw").addVariety("American");
        mango.addCategory("Fruits").addCategory("Vegetables");

        Item mango2 =  new Item("mango", 10, new BigDecimal("1000"));
        mango2.addVariety("Alphonso").addVariety("Raw").addVariety("American");
        mango2.addCategory("Fruits").addCategory("Vegetables");

        List<Item> items = Arrays.asList(apple, mango, mango2);
        Map<String, Integer> map = items.stream().collect(
                groupingBy(item -> item.getName(), Collectors.summingInt(item -> item.getVarities().size())));
        System.out.println(map);

        List<Result> result = new ArrayList<>();
        items.stream().forEach( item ->{
                    item.getCategories().forEach(category -> {
                        Result r = new Result().setCategory(category).setItemName(item.getName()).setPrice(item.getPrice());
                        result.add(r);
                    });
                }
        );
        System.out.println(result);

        Map<String, Map<String, Integer>> collect = result.stream().collect(
                groupingBy(Result::getCategory,
                        groupingBy(Result::getItemName, summingInt(Result::getPrice)))
        );
        System.out.println(collect);
    }
}
