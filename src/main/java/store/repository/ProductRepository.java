package store.repository;

import static store.exception.ErrorMessage.*;

import store.domain.Product;
import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.domain.vo.PromotionName;
import store.domain.vo.Quantity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductRepository {
    private static final int NEXT_INDEX_OFFSET = 1;
    private static final int PRODUCT_NOT_FOUND_INDEX = -1;
    private static final int NO_STOCK_QUANTITY = 0;
    private static final String NO_PROMOTION_NAME = "null";

    private final List<Product> products;

    public ProductRepository(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public List<Product> getAllProducts() {
        ensureRegularProducts();
        return new ArrayList<>(products);
    }

    public Product findProductByName(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    public Product findProductByPromotionNameAndPromotion(String name) {
        return products.stream()
                .filter(product -> Objects.equals(product.getName(), name) && product.isPromotional())
                .findFirst()
                .orElse(null);
    }

    public Product findByNameAndPromotion(String productName, String promotionName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName) &&
                        ((promotionName.equals("null") && (product.getPromotionName() == null || product.getPromotionName().equals("null"))) ||
                                (!promotionName.equals("null") && promotionName.equals(product.getPromotionName()))))
                .findFirst()
                .orElse(null);
    }

    public void save(Product updatedProduct) {
        Product existingProduct = findByNameAndPromotion(updatedProduct.getName(), updatedProduct.getPromotionName());

        if (isNewProduct(existingProduct)) {
            addProduct(updatedProduct);
            return;
        }

        updateExistingProduct(existingProduct, updatedProduct);
    }

    private boolean isNewProduct(Product existingProduct) {
        return existingProduct == null;
    }

    private void updateExistingProduct(Product existingProduct, Product updatedProduct) {
        int index = findProductIndex(existingProduct);
        if (isValidIndex(index)) {
            products.set(index, updatedProduct);
        }
    }

    private int findProductIndex(Product product) {
        return products.indexOf(product);
    }

    private boolean isValidIndex(int index) {
        return index != PRODUCT_NOT_FOUND_INDEX;
    }

    private void ensureRegularProducts() {
        List<Product> promotionalProducts = filterPromotionalProducts();

        promotionalProducts.forEach(this::addRegularProductIfNotExists);
    }

    private List<Product> filterPromotionalProducts() {
        return products.stream()
                .filter(product -> hasPromotion(product))
                .collect(Collectors.toList());
    }

    private boolean hasPromotion(Product product) {
        return product.getPromotionName() != null && !product.getPromotionName().equals("null");
    }

    private void addRegularProductIfNotExists(Product promotionalProduct) {
        if (isRegularProductMissing(promotionalProduct)) {
            Product regularProduct = createRegularProduct(promotionalProduct);
            addProductNextToPromotion(promotionalProduct, regularProduct);
        }
    }

    private boolean isRegularProductMissing(Product promotionalProduct) {
        return findByNameAndPromotion(promotionalProduct.getName(), "null") == null;
    }

    private Product createRegularProduct(Product promotionalProduct) {
        return Product.of(
                ProductName.from(promotionalProduct.getName()),
                Price.from(promotionalProduct.getPriceValue()),
                Quantity.from(NO_STOCK_QUANTITY),
                PromotionName.from(NO_PROMOTION_NAME)
        );
    }

    private void addProductNextToPromotion(Product promotionalProduct, Product regularProduct) {
        int promoIndex = products.indexOf(promotionalProduct);
        if (promoIndex != PRODUCT_NOT_FOUND_INDEX) {
            addProductAtIndex(promoIndex + NEXT_INDEX_OFFSET, regularProduct);
        }
    }

    private void addProduct(Product newProduct) {
        products.add(newProduct);
    }

    public void addProductAtIndex(int index, Product newProduct) {
        if (index < 0 || index > products.size()) {
            throw new IllegalArgumentException(INVALID_INDEX.getMessage());
        }
        products.add(index, newProduct);
    }
}
