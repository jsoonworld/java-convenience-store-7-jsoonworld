package store.controller;

import store.domain.Product;
import store.service.MembershipDiscountService;
import store.service.ReceiptService;
import store.service.ReceiptCalculationService;
import store.service.ProductSaver;
import store.view.InputView;
import store.view.OutputView;
import store.repository.ProductRepository;

import java.util.List;

public class CompletePurchaseController {

    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();
    private final MembershipDiscountService discountService = new MembershipDiscountService(outputView, inputView);
    private final ReceiptService receiptService = new ReceiptService(new ReceiptCalculationService(), outputView);
    private final ProductSaver productSaver;

    public CompletePurchaseController(ProductRepository productRepository) {
        this.productSaver = new ProductSaver(productRepository);
    }

    public void completePurchase(StringBuilder purchaseDetails, StringBuilder promotionalDetails, int totalFinalQuantity, int totalPrice, int totalPromotionalDiscount, List<Product> productsToSave) {

        // 1. 멤버십 할인 여부 처리
        boolean membershipDiscountApplied = discountService.applyDiscount();

        // 2. 영수증 출력
        receiptService.printReceipt(purchaseDetails, promotionalDetails, totalFinalQuantity, totalPrice, totalPromotionalDiscount, membershipDiscountApplied);

        // 3. 업데이트된 상품 저장
        productSaver.save(productsToSave);
    }
}
