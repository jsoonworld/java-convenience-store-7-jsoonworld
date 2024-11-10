package store.repository;

import static store.exception.ErrorMessage.*;

import store.domain.Product;
import store.io.output.ProductStockSaver;

import java.util.List;

public class ProductRepository {
    private static final int PRODUCT_NOT_FOUND_INDEX = -1;

    private final List<Product> products;
    private final ProductStockSaver productStockSaver;

    public ProductRepository(List<Product> products, ProductStockSaver productStockSaver) {
        this.products = products;
        this.productStockSaver = productStockSaver;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product findProductByName(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    public Product findByNameAndPromotion(String productName, String promotionName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName) &&
                        (product.getPromotionName().equals(promotionName) || promotionName.equals("null")))
                .findFirst()
                .orElse(null);
    }

    public void save(Product updatedProduct) {
        Product existingProduct = findByNameAndPromotion(updatedProduct.getName(), updatedProduct.getPromotionName());

        if (existingProduct == null) {
            throw new RuntimeException(PRODUCT_SAVE_FAILED.getMessage());
        }

        int index = products.indexOf(existingProduct);
        if (index != PRODUCT_NOT_FOUND_INDEX) {
            products.set(index, updatedProduct);
        }
        productStockSaver.saveProductStock(products);
    }
}
