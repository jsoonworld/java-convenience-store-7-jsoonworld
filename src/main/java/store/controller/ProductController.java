package store.controller;

import store.domain.Product;
import store.dto.PurchaseProductRequest;
import store.service.ProductService;
import store.util.ProductInventoryFormatter;

import java.util.List;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public List<String> getFormattedProducts() {
        List<Product> products = productService.getAllProducts();
        return ProductInventoryFormatter.format(products);
    }

    public void processPurchase(List<PurchaseProductRequest> purchaseProductRequests) {
        productService.processPurchase(purchaseProductRequests);
    }
}
