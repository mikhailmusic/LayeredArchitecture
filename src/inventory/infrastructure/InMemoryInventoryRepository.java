package inventory.infrastructure;

import inventory.domain.IInventoryRepository;
import inventory.domain.Inventory;

import java.util.*;

public class InMemoryInventoryRepository implements IInventoryRepository {
    private final Map<String, Inventory> inventories = new HashMap<>();

    @Override
    public void saveInventory(Inventory inventory) {
        inventories.put(inventory.getId(), inventory);
    }

    @Override
    public Optional<Inventory> findById(String id) {
        return Optional.ofNullable(inventories.get(id));
    }

    @Override
    public List<Inventory> findAllInventories() {
        return new ArrayList<>(inventories.values());

    }
}
