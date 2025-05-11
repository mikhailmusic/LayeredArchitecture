package inventory.domain;

import java.util.List;
import java.util.Optional;

public interface IInventoryRepository {
    void saveInventory(Inventory inventory);
    Optional<Inventory> findById(String id);
    List<Inventory> findAllInventories();
}
