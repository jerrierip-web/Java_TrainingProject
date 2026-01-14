package com.example;

import java.util.*;

public class KIOSKPointRepository {

    private final Map<String, KIOSKPoint> storage = new LinkedHashMap<>();

    public KIOSKPoint save(String id, KIOSKPoint point) {
        if (point == null) throw new IllegalArgumentException("point cannot be null");
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        storage.put(id, point);
        return point;
    }

    public Optional<KIOSKPoint> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<KIOSKPoint> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(String id) {
        storage.remove(id);
    }

    public void clear() {
        storage.clear();
    }
}
