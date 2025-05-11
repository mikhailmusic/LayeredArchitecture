package inventory.application;

import inventory.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryService {
    private final IInventoryRepository inventoryRepository;
    private final IProductRepository productRepository;

    public InventoryService(IInventoryRepository inventoryRepository, IProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public void newInventory(String id, String name) {
        Inventory newInventory = new Inventory(id, name);
        inventoryRepository.saveInventory(newInventory);
    }

    public void receiveProduct(String inventoryId, String productId, String name, int quantity, LocalDate expiryDate,
                               TemperatureMode temperatureMode, int criticalLevel) {
        Inventory inventory = getInventoryOrThrow(inventoryId);
        Product product = new Product(productId, name, quantity, expiryDate, temperatureMode, criticalLevel);
        inventory.addProduct(product);
        productRepository.saveProduct(product);
    }

    public void useProduct(String inventoryId, String productId, int quantity) {
        Inventory inventory = getInventoryOrThrow(inventoryId);
        Product product = getProductOrThrow(productId);
        inventory.consumeProduct(product, quantity);
    }

    public void writeOffExpiredProducts(String inventoryId) {
        Inventory inventory = getInventoryOrThrow(inventoryId);
        LocalDate today = LocalDate.now();
        List<Product> expired = inventory.getProducts().stream().filter(p -> p.isExpired(today)).collect(Collectors.toList());
        for (Product product : expired) {
            inventory.removeProduct(product);
        }
    }

    public void restockProduct(String inventoryId, String productId, int addQuantity) {
        Inventory inventory = getInventoryOrThrow(inventoryId);
        Product product = getProductOrThrow(productId);
        inventory.restockProduct(product, addQuantity);
    }

    public void removeProduct(String inventoryId, String productId) {
        Inventory inventory = getInventoryOrThrow(inventoryId);
        Product product = getProductOrThrow(productId);
        inventory.removeProduct(product);
    }

    public List<Product> getCriticalStockProducts(String inventoryId) {
        Inventory inventory = getInventoryOrThrow(inventoryId);
        List<Product> products = inventory.getProducts().stream().filter(Product::isBelowCriticalLevel).collect(Collectors.toList());
        return products;
    }

    public Inventory generateInventoryReport(String inventoryId) {
        return getInventoryOrThrow(inventoryId);
    }

    public void cleanupZeroQuantity(String inventoryId) {
        Inventory inventory = getInventoryOrThrow(inventoryId);
        List<Product> products = inventory.getProducts().stream().filter(p -> p.getQuantity() == 0).collect(Collectors.toList());
        for (Product product : products) {
            inventory.removeProduct(product);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }
    public List<Product> getAllInventoryProducts(String inventoryId) {
        Inventory inventory = getInventoryOrThrow(inventoryId);
        return inventory.getProducts();
    }

    public List<Product> getProductsTemperatureMode(TemperatureMode temperatureMode) {
        return productRepository.findByTemperatureMode(temperatureMode);
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAllInventories();
    }

    public void adjustInventory(String inventoryId, String productId, int actualQuantity) {
        Inventory inventory = getInventoryOrThrow(inventoryId);
        Product product = getProductOrThrow(productId);

        int currentQuantity = product.getQuantity();
        if (actualQuantity < currentQuantity) {
            int toConsume = currentQuantity - actualQuantity;
            inventory.consumeProduct(product, toConsume);
        } else if (actualQuantity > currentQuantity) {
            int toRestock = actualQuantity - currentQuantity;
            inventory.restockProduct(product, toRestock);
        }
    }

    public Inventory getInventoryOrThrow(String id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("Инвентарь не найден: " + id));
    }
    public Product getProductOrThrow(String id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Продукт не найден: " + id));
    }
}
