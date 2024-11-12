package store.service.purchase;

import store.controller.PromotionController;
import store.controller.StockController;
import store.domain.product.Product;
import store.dto.request.promotion.PromotionPurchaseRequest;
import store.dto.request.promotion.PromotionRequest;
import store.dto.request.purchase.PurchaseProductRequest;
import store.dto.response.promotion.PromotionResult;
import store.dto.response.PurchaseProcessingResult;
import store.dto.response.StockResult;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.product.StockService;
import store.service.promotion.PromotionHandler;
import store.service.promotion.PromotionRequestCreator;
import store.util.formatter.PurchaseDetailsFormatter;

import java.util.ArrayList;
import java.util.List;

public class PurchaseProcessor {
    private final PromotionHandler promotionHandler;
    private final StockService stockService;
    private final Product product;
    private final PurchaseProductRequest request;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public PurchaseProcessor(PromotionHandler promotionHandler, StockService stockService, Product product, PurchaseProductRequest request, ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.promotionHandler = promotionHandler;
        this.stockService = stockService;
        this.product = product;
        this.request = request;
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public PurchaseProcessingResult process() {
        PromotionResult promotionResult = applyPromotion();
        StockResult stockResult = updateStock(promotionResult);

        int totalPromotionalDiscount = calculateTotalDiscount(promotionResult, stockResult);
        int totalFinalQuantity = calculateFinalQuantity(promotionResult.getRemainingQuantity(), request.getQuantity());
        List<Product> productsToSave = gatherProductsToSave(promotionResult, stockResult);
        String purchaseDetails = createPurchaseDetails(totalFinalQuantity);

        int totalPrice = calculateTotalPrice();

        return PurchaseProcessingResult.of(totalPrice, totalPromotionalDiscount, totalFinalQuantity, purchaseDetails, promotionResult.getPromotionalDetails().toString(), productsToSave);
    }

    private PromotionResult applyPromotion() {
        PromotionRequest promotionRequest = createPromotionRequest();
        PromotionController promotionController = new PromotionController(promotionHandler);
        return promotionController.handlePromotion(promotionRequest);
    }

    private PromotionRequest createPromotionRequest() {
        PromotionRequestCreator requestCreator = new PromotionRequestCreator();
        return requestCreator.createPromotionRequest(product, PromotionPurchaseRequest.of(request.getProductName(), request.getQuantity()), productRepository, promotionRepository);
    }

    private StockResult updateStock(PromotionResult promotionResult) {
        StockController stockController = new StockController(stockService);
        return stockController.handleRegularStock(
                product.getName(),
                promotionResult.getRemainingQuantity(),
                product.getPriceValue(),
                request.getQuantity()
        );
    }

    private int calculateTotalDiscount(PromotionResult promotionResult, StockResult stockResult) {
        return promotionResult.getTotalPromotionalDiscount() + stockResult.getTotalPromotionalDiscount();
    }

    private int calculateFinalQuantity(int remainingQuantity, int requestedQuantity) {
        if (remainingQuantity == 0) {
            return requestedQuantity;
        }
        return remainingQuantity;
    }

    private List<Product> gatherProductsToSave(PromotionResult promotionResult, StockResult stockResult) {
        List<Product> productsToSave = new ArrayList<>(promotionResult.getProductsToSave());
        productsToSave.addAll(stockResult.getProductsToSave());
        return productsToSave;
    }

    private String createPurchaseDetails(int totalFinalQuantity) {
        return PurchaseDetailsFormatter.formatPurchaseDetails(product, totalFinalQuantity);
    }

    private int calculateTotalPrice() {
        return product.getPriceValue() * request.getQuantity();
    }
}
