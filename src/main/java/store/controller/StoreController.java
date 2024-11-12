package store.controller;

import store.dto.request.purchase.AdditionalPurchaseInput;
import store.dto.request.purchase.PurchaseInput;
import store.dto.request.purchase.PurchaseProductRequest;
import store.view.InputView;
import store.view.OutputView;
import store.util.parser.PurchaseProductRequestParser;
import store.validation.PurchaseInputValidator;

import java.util.List;

public class StoreController {
    private final OutputView outputView;
    private final InputView inputView;
    private final ProductController productController;

    public StoreController(OutputView outputView, InputView inputView, ProductController productController) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.productController = productController;
    }

    public void run() {
        runLoop();
    }

    private void runLoop() {
        do {
            displayProductInfo();
            processPurchaseInput();
        } while (wantsToContinue());
    }

    private void displayProductInfo() {
        outputView.printWelcomeMessage();
        outputView.printCurrentProductsMessage();

        List<String> formattedProducts = productController.getFormattedProducts();
        outputView.printProducts(formattedProducts);
    }

    private void processPurchaseInput() {
        boolean validInput = false;
        while (!validInput) {
            try {
                outputView.printPurchasePrompt();
                PurchaseInput purchaseInput = inputView.readPurchaseInput();

                PurchaseInputValidator.validate(purchaseInput.getInput());

                List<PurchaseProductRequest> purchaseProductRequests = PurchaseProductRequestParser.parse(purchaseInput.getInput());
                productController.processPurchase(purchaseProductRequests);

                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean wantsToContinue() {
        try {
            outputView.printContinuePurchasePrompt();
            AdditionalPurchaseInput additionalPurchaseInput = inputView.readAdditionalPurchaseInput();
            return additionalPurchaseInput.wantsToContinue();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
