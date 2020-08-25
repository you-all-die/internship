package com.example.demosite.repository;

import com.example.demosite.model.Product;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    private final Map<Long, Product> products;
    private long index;

    public ProductService(Map<Long, Product> products) {
        this.products = products;
    }

    public ProductService() {
        products = new HashMap<>();
        products.put(0L, new Product(0, "Смартфоны", "Iphone 7", "Описание Iphone 7", "", "В наличии"));
        products.put(1L, new Product(1, "Смартфоны", "Iphone 8", "Описание Iphone 8", "", "В наличии"));
        products.put(2L, new Product(2, "Смартфоны", "Iphone X", "Описание Iphone X", "", "В наличии"));
        products.put(3L, new Product(3, "Смартфоны", "Iphone XR", "Описание Iphone XR", "", "В наличии"));
        products.put(4L, new Product(4, "Смартфоны", "Iphone 11", "Описание Iphone 1", "", "В наличии"));

        index = 5;
    }

    public Product getProductById(Long id) {
        return products.get(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public void removeProduct(long id) {
        products.remove(id);
    }

    public void addProduct(Product product) {
        product.setId(index);
        products.put(index++, product);
    }

    public List<Product> findByName(String name) {
        List<Product> result = new ArrayList<>();

        products.forEach((id, product) -> {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(product);
            }
        });
        return result;
    }

}
