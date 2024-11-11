package store.service.product;

import static store.exception.ErrorMessage.*;

import store.controller.CompletePurchaseController;
import store.domain.Product;
import store.dto.init.PurchaseProcessData;
import store.dto.request.purchase.PurchaseProductRequest;
import store.dto.response.PurchaseProcessingResult;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.promotion.PromotionHandler;
import store.service.promotion.PromotionService;
import store.service.purchase.PurchaseProcessor;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final PromotionHandler promotionHandler;
    private final StockService stockService;

    public ProductService(ProductRepository productRepository, PromotionRepository promotionRepository, PromotionService promotionService) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.promotionHandler = new PromotionHandler(productRepository, promotionService);
        this.stockService = new StockService(productRepository);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void processPurchase(List<PurchaseProductRequest> purchaseProductRequests) {
        PurchaseProcessData processData = initializePurchaseProcess();

        for (PurchaseProductRequest request : purchaseProductRequests) {
            PurchaseProcessingResult result = accumulatePurchaseResult(request, processData.productsToSave, processData.purchaseDetails, processData.promotionalDetails);

            processData.totalPrice += result.getTotalPrice();
            processData.totalPromotionalDiscount += result.getPromotionalDiscount();
            processData.totalFinalQuantity += result.getFinalQuantity();
        }

        finalizePurchase(processData.purchaseDetails, processData.promotionalDetails, processData.totalFinalQuantity, processData.totalPrice, processData.totalPromotionalDiscount, processData.productsToSave);
    }

    private PurchaseProcessData initializePurchaseProcess() {
        return PurchaseProcessData.of();
    }

    private PurchaseProcessingResult accumulatePurchaseResult(PurchaseProductRequest request, List<Product> productsToSave, StringBuilder purchaseDetails, StringBuilder promotionalDetails) {
        Product product = getProductByName(request.getProductName());
        PurchaseProcessor processor = new PurchaseProcessor(promotionHandler, stockService, product, request, productRepository, promotionRepository);
        PurchaseProcessingResult result = processor.process();

        purchaseDetails.append(result.getPurchaseDetails());
        promotionalDetails.append(result.getPromotionalDetails());
        productsToSave.addAll(result.getProductsToSave());

        return result;
    }

    private void finalizePurchase(StringBuilder purchaseDetails, StringBuilder promotionalDetails, int totalFinalQuantity, int totalPrice, int totalPromotionalDiscount, List<Product> productsToSave) {
        CompletePurchaseController completePurchaseController = new CompletePurchaseController(productRepository);
        completePurchaseController.completePurchase(
                purchaseDetails, promotionalDetails, totalFinalQuantity, totalPrice, totalPromotionalDiscount, productsToSave
        );
    }

    private Product getProductByName(String productName) {
        Product product = productRepository.findProductByName(productName);

        if (product == null) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND.getMessage());
        }
        return product;
    }
}
