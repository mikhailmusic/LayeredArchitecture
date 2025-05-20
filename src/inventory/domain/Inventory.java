package inventory.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory {
    private String id;
    private String name;
    private List<Product> products;

    public Inventory(String id, String name) {
        this.id = id;
        this.name = name;
        this.products  = new ArrayList<>();;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProduct(Product product) {
        if (products.contains(product)) {
            throw new IllegalStateException("Продукт уже есть в инвентаре");
        }
        products.add(product);
    }

    public void removeProduct(Product product) {
        if (!products.contains(product)) {
            throw new IllegalStateException("Продукта нет в инвентаре");
        }
        products.remove(product);
    }

    public void consumeProduct(Product product, int quantity) {
        if (!products.contains(product)) {
            throw new IllegalStateException("Продукта нет в инвентаре");
        }
        product.decreaseQuantity(quantity);
    }

    public void restockProduct(Product product, int quantity) {
        if (!products.contains(product)) {
            throw new IllegalStateException("Продукта нет в инвентаре");
        }
        product.increaseQuantity(quantity);
    }

    public void removeExpiredProducts(LocalDate currentDate) {
        products.removeIf(p -> p.isExpired(currentDate));
    }

    public void removeZeroQuantityProducts() {
        products.removeIf(p -> p.getQuantity() == 0);
    }

    public List<Product> criticalStockProducts() {
        return products.stream().filter(Product::isBelowCriticalLevel).collect(Collectors.toUnmodifiableList());
    }



    @Override
    public String toString() {
        return "Inventory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}
