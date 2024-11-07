package store.io;

import java.io.IOException;
import java.util.List;

public interface FileDataLoader {
    List<String> loadFile(String filePath) throws IOException;
}
