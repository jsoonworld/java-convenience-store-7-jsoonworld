package store.io.output;

import static store.exception.ErrorMessage.*;

import store.domain.Product;
import store.util.ProductCsvParser;

import java.io.IOException;
import java.util.List;

public class ProductStockSaver {
    private final FileDataSaver fileDataSaver;
    private final String filePath;
    private final ProductCsvParser parser;

    public ProductStockSaver(FileDataSaver fileDataSaver, String filePath, ProductCsvParser parser) {
        this.fileDataSaver = fileDataSaver;
        this.filePath = filePath;
        this.parser = parser;
    }

    public void saveProductStock(List<Product> products) {
        List<String> productLines = parser.toCsvWithHeader(products);

        try {
            fileDataSaver.saveFile(filePath, productLines);
        } catch (IOException e) {
            throw new RuntimeException(FILE_SAVE_ERROR.getMessage());
        }
    }
}