package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.*;

public class Warehouse {
    private final String name;
    private static final Map<String, Warehouse> instances = new HashMap<>();

    private final Map<UUID, ProductRecord> products = new HashMap<>();
    private final Set<ProductRecord> changedProducts = new HashSet<>();

    private Warehouse(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static Warehouse getInstance(String name) {
        if (instances.containsKey(name)){
            instances.get(name).reset();
        }
        else{
            instances.put(name, new Warehouse(name));
        }
        return instances.get(name);
    }

    public static Warehouse getInstance() {
        return getInstance("default");
    }

    public ProductRecord addProduct(UUID id, String name, Category category, BigDecimal price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }

        if (id == null) {
            id = UUID.randomUUID();
        }

        if (products.containsKey(id)) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }

        ProductRecord product = new ProductRecord(id, name, category, price != null ? price : BigDecimal.ZERO);
        products.put(id, product);
        return product;
    }

    public void updateProductPrice(UUID productId, BigDecimal newPrice) {
        ProductRecord product = products.get(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }


        if (!product.price().equals(newPrice)) {

            changedProducts.remove(product);

            ProductRecord updatedProduct = new ProductRecord(product.uuid(), product.name(), product.category(), newPrice);


            products.put(productId, updatedProduct);


            changedProducts.add(product);

        }
    }

    public Optional<ProductRecord> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<ProductRecord> getProductsBy(Category category) {
        List<ProductRecord> result = new ArrayList<>();
        for (ProductRecord product : products.values()) {
            if (product.category().equals(category)) {
                result.add(product);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        Map<Category, List<ProductRecord>> groupedProducts = new HashMap<>();
        for (ProductRecord product : products.values()) {
            groupedProducts
                    .computeIfAbsent(product.category(), _ -> new ArrayList<>())
                    .add(product);
        }
        return groupedProducts;
    }

    public List<ProductRecord> getProducts() {
        return List.copyOf(products.values());
    }


    public Set<ProductRecord> getChangedProducts() {
        return Collections.unmodifiableSet(changedProducts);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }


    public void reset() {
        products.clear();
        changedProducts.clear();
    }
}

