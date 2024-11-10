package store.io.output;

import java.io.IOException;
import java.util.List;

public interface FileDataSaver {
    void saveFile(String filePath, List<String> lines) throws IOException;
}
