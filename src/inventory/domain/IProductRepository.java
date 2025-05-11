package inventory.domain;

import java.util.List;
import java.util.Optional;

public interface IProductRepository {
    void saveProduct(Product product);
    Optional<Product> findById(String id);
    List<Product> findAllProducts();
    List<Product> findByTemperatureMode(TemperatureMode temperatureMode);
}
