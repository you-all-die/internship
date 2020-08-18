package com.example.demosite.repository;

import com.example.demosite.model.TestData;

import java.util.HashMap;
import java.util.Map;

public class TestDataRepository {
    private final Map<Long, TestData> store = new HashMap<>();
    long key = 0;

    public Map<Long, TestData> getStore() {
        return store;
    }

    public void addData(TestData data) {
        data.setId(key);
        ++key;
        store.put(data.getId(), data);
    }

    public void deleteData(String id) {
        store.remove(Long.parseLong(id));
    }

    public void deleteAllData() {
        store.clear();
    }
}
