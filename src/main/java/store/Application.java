package store;

import store.io.FileDataLoader;
import store.io.ProductDataLoader;
import store.io.PromotionDataLoader;
import store.repository.Repository;
import store.repository.ProductFileRepository;
import store.repository.PromotionFileRepository;
import store.util.FilePathLoader;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            FileDataLoader productLoader = new ProductDataLoader();
            FileDataLoader promotionLoader = new PromotionDataLoader();

            String productFilePath = FilePathLoader.getResourceFilePath("products.md");
            String promotionFilePath = FilePathLoader.getResourceFilePath("promotions.md");

            Repository<String> productRepository = new ProductFileRepository(productLoader, productFilePath);
            Repository<String> promotionRepository = new PromotionFileRepository(promotionLoader, promotionFilePath);

            System.out.println("Products:");
            productRepository.findAll().forEach(System.out::println);

            System.out.println("\nPromotions:");
            promotionRepository.findAll().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
