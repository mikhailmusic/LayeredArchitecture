package inventory.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Product {
    private String id;
    private String name;
    private int  quantity;
    private LocalDate expiryDate;
    private TemperatureMode temperatureMode;
    private int criticalLevel;

    public Product(String id, String name, int quantity, LocalDate expiryDate, TemperatureMode temperatureMode, int criticalLevel) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        if (quantity < 0 || criticalLevel < 0) {
            throw new IllegalArgumentException("Запас не может быть отрицательным");
        }
        if (expiryDate == null || expiryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Дата истечения срока годности не может быть пустой или в прошлом");
        }

        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.temperatureMode = temperatureMode;
        this.criticalLevel = criticalLevel;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public TemperatureMode getTemperatureMode() {
        return temperatureMode;
    }

    public int getCriticalLevel() {
        return criticalLevel;
    }

    public void increaseQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalStateException("Количество не может быть отрицательным");
        }
        quantity += amount;
    }
    public void decreaseQuantity(int amount) {
        if (amount <= 0 || amount > quantity) {
            throw new IllegalArgumentException("Количество должно быть больше 0 и не превышать доступное количество");
        } else {
            this.quantity -= amount;
        }
    }

    public boolean isExpired(LocalDate currentDate) {
        return expiryDate.isBefore(currentDate);
    }

    public boolean isBelowCriticalLevel() {
        return quantity <= criticalLevel;
    }

    @Override
    public String toString() {
        return "Product{id='" + id + ", name='" + name + ", quantity=" + quantity + ", expiryDate=" + expiryDate +
                ", temperatureMode=" + temperatureMode + ", criticalLevel=" + criticalLevel + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
