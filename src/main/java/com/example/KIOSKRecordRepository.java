package com.example;

import java.util.*;

public class KIOSKRecordRepository {

    private final Map<String, KIOSKRecord> storage = new LinkedHashMap<>();

    public KIOSKRecord save(String id, KIOSKRecord record) {
        if (record == null) throw new IllegalArgumentException("record cannot be null");
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        storage.put(id, record);
        return record;
    }

    public Optional<KIOSKRecord> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<KIOSKRecord> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(String id) {
        storage.remove(id);
    }

    public void clear() {
        storage.clear();
    }
}
