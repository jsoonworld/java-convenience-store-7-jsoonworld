package store.config;

import store.controller.ProductController;
import store.controller.StoreController;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.ProductService;
import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

public class StoreConfig {

    public StoreController createStoreController() {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();

        FileConfig fileConfig = new FileConfig();
        ParserConfig parserConfig = new ParserConfig();

        RepositoryConfig repositoryConfig = new RepositoryConfig(fileConfig, parserConfig);
        ProductRepository productRepository = repositoryConfig.initializeProductRepository();
        PromotionRepository promotionRepository = repositoryConfig.initializePromotionRepository();

        PromotionService promotionService = new PromotionService(promotionRepository);

        ProductService productService = new ProductService(productRepository, promotionRepository, promotionService);
        ProductController productController = new ProductController(productService);

        return new StoreController(outputView, inputView, productController);
    }
}
