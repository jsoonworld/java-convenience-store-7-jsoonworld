package store.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class AbstractFileDataLoader implements FileDataLoader {

    @Override
    public List<String> loadFile(String filePath) throws IOException {
        validateFileExists(filePath);
        return readFileContents(filePath);
    }

    private void validateFileExists(String filePath) throws IOException {
        if (!Files.exists(Paths.get(filePath))) {
            throw new IOException("[ERROR] 파일이 존재하지 않습니다");
        }
    }

    private List<String> readFileContents(String filePath) throws IOException {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new IOException("[ERROR] 파일을 읽을 수 없습니다");
        }
    }
}
