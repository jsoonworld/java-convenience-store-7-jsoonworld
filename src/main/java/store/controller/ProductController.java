package store.controller;

import store.domain.Product;
import store.dto.request.PurchaseProductRequest;
import store.service.product.ProductService;
import store.util.formatter.ProductInventoryFormatter;

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
