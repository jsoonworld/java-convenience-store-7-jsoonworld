package store.repository;

import store.domain.Product;
import store.domain.vo.Price;
import store.domain.vo.ProductName;
import store.domain.vo.PromotionName;
import store.domain.vo.Quantity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepository {
    private final List<Product> products;

    public ProductRepository(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public List<Product> getAllProducts() {
        ensureRegularProducts();  // 상품 목록을 반환하기 전에 프로모션 상품에 대한 일반 상품 추가
        return new ArrayList<>(products);
    }

    private void ensureRegularProducts() {
        // 프로모션 상품 목록 필터링
        List<Product> promotionalProducts = products.stream()
                .filter(product -> product.getPromotionName() != null && !product.getPromotionName().equals("null"))
                .collect(Collectors.toList());

        // 프로모션 상품을 처리 후, 일반 상품을 추가
        for (Product promotionalProduct : promotionalProducts) {
            // 해당 프로모션에 대한 일반 상품이 없다면 추가
            if (findByNameAndPromotion(promotionalProduct.getName(), "null") == null) {
                Product regularProduct = Product.of(
                        ProductName.from(promotionalProduct.getName()),
                        Price.from(promotionalProduct.getPriceValue()),
                        Quantity.from(0), // "재고 없음"을 나타내기 위해 0으로 설정
                        PromotionName.from("null")
                );

                // 일반 상품을 프로모션 상품 뒤에 추가
                int promoIndex = products.indexOf(promotionalProduct);
                if (promoIndex != -1) {
                    addProductAtIndex(promoIndex + 1, regularProduct);
                }
            }
        }
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
                        ((promotionName.equals("null") && (product.getPromotionName() == null || product.getPromotionName().equals("null"))) ||
                                (!promotionName.equals("null") && promotionName.equals(product.getPromotionName()))))
                .findFirst()
                .orElse(null);
    }

    public void save(Product updatedProduct) {
        Product existingProduct = findByNameAndPromotion(updatedProduct.getName(), updatedProduct.getPromotionName());

        if (existingProduct == null) {
            addProduct(updatedProduct);
        } else {
            int index = products.indexOf(existingProduct);
            if (index != -1) {
                products.set(index, updatedProduct);
            }
        }
    }

    private void addProduct(Product newProduct) {
        products.add(newProduct);
    }

    public void addProductAtIndex(int index, Product newProduct) {
        if (index >= 0 && index <= products.size()) {
            products.add(index, newProduct);  // 원하는 위치에 추가
        } else {
            // 유효하지 않은 인덱스일 경우 예외를 던지거나, 다른 처리를 할 수 있습니다.
            System.out.println("유효하지 않은 인덱스입니다. 상품 추가 실패.");
        }
    }
}
