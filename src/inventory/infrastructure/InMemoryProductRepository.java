package inventory.infrastructure;

import inventory.domain.IProductRepository;
import inventory.domain.Product;
import inventory.domain.TemperatureMode;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements IProductRepository {
    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void saveProduct(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Product> findByTemperatureMode(TemperatureMode temperatureMode) {
        return products.values().stream().filter(product -> product.getTemperatureMode() == temperatureMode)
                .collect(Collectors.toList());
    }
}
