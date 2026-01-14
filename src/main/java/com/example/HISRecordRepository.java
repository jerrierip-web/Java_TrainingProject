package com.example;

import java.util.*;

/**
 * Simple in-memory repository for HISRecord objects.
 */
public class HISRecordRepository {

    private final Map<String, HISRecord> storage = new LinkedHashMap<>();

    public HISRecord save(HISRecord record) {
        if (record == null) throw new IllegalArgumentException("record cannot be null");
        if (record.getSerial_no() == null || record.getSerial_no().isEmpty()) {
            record.setSerial_no(UUID.randomUUID().toString());
        }
        storage.put(record.getSerial_no(), record);
        return record;
    }

    public Optional<HISRecord> findBySerial(String serialNo) {
        return Optional.ofNullable(storage.get(serialNo));
    }

    public List<HISRecord> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteBySerial(String serialNo) {
        storage.remove(serialNo);
    }

    public List<HISRecord> findByPatientId(String patientId) {
        List<HISRecord> result = new ArrayList<>();
        for (HISRecord r : storage.values()) {
            if (r != null && Objects.equals(r.getPatient_id(), patientId)) {
                result.add(r);
            }
        }
        return result;
    }

    public void clear() {
        storage.clear();
    }
}
