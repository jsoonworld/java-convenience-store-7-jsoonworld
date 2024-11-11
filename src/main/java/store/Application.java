package store;

import store.config.*;
import store.controller.StoreController;

public class Application {
    public static void main(String[] args) {
        StoreConfig storeConfig = new StoreConfig();
        StoreController storeController = storeConfig.createStoreController();
        storeController.run();
    }
}