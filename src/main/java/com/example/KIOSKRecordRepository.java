package com.example;

import java.util.*;

public class KIOSKRecordRepository {

    private final Map<String, KIOSKRecords> storage = new LinkedHashMap<>();

    public KIOSKRecords save(String id, KIOSKRecords record) {
        if (record == null) throw new IllegalArgumentException("record cannot be null");
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        storage.put(id, record);
        return record;
    }

    public Optional<KIOSKRecords> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<KIOSKRecords> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(String id) {
        storage.remove(id);
    }

    public void clear() {
        storage.clear();
    }
}
