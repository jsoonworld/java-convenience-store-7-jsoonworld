package store.repository;

import static store.exception.ErrorMessage.*;

import store.io.FileDataLoader;
import java.io.IOException;
import java.util.List;

public abstract class FileBasedRepository implements Repository<String> {

    private final FileDataLoader dataLoader;
    private final String filePath;
    protected List<String> data;

    protected FileBasedRepository(FileDataLoader dataLoader, String filePath) {
        this.dataLoader = dataLoader;
        this.filePath = filePath;
        loadData();
    }

    @Override
    public List<String> loadAll() {
        return data;
    }

    private void loadData() {
        try {
            this.data = dataLoader.loadFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException(FILE_LOAD_FAILURE.getMessage(), e);
        }
    }
}
