package store.service;

import store.domain.Product;
import store.dto.request.PromotionRequest;
import store.dto.response.PromotionResponse;
import store.repository.ProductRepository;
import store.domain.Promotion;
import store.view.InputView;
import store.view.OutputView;

import java.util.Optional;

public class PromotionHandler {

    private final ProductRepository productRepository;
    private final PromotionService promotionService;
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();


    public PromotionHandler(ProductRepository productRepository, PromotionService promotionService) {
        this.productRepository = productRepository;
        this.promotionService = promotionService;
    }

    public PromotionResponse applyPromotion(PromotionRequest request) {
        Product promotionalProduct = productRepository.findByNameAndPromotion(request.getProductName(), request.getPromotionName());

        if (promotionalProduct != null) {
            Optional<Promotion> validPromotion = promotionService.getValidPromotionForProduct(promotionalProduct.getPromotionName());

            if (validPromotion.isPresent()) {
                PromotionProcessor promotionProcessor = new PromotionProcessor(inputView, outputView);
                return promotionProcessor.applyPromotion(request);
            }
        }

        return PromotionResponse.emptyResponse(request.getRequestedQuantity());
    }
}
