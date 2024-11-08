package store.io;

import static store.exception.ErrorMessage.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DefaultFileDataLoader implements FileDataLoader {

    @Override
    public List<String> loadFile(String filePath) throws IOException {
        validateFileExists(filePath);
        return readFileContents(filePath);
    }

    private void validateFileExists(String filePath) throws IOException {
        if (!Files.exists(Paths.get(filePath))) {
            throw new IOException(FILE_NOT_FOUND.getMessage());
        }
    }

    private List<String> readFileContents(String filePath) throws IOException {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new IOException(FILE_READ_ERROR.getMessage());
        }
    }
}
