package store.util;

import static store.exception.ErrorMessage.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;

public class FilePathLoader {

    public static String getFilePath(String fileName) throws IOException {
        return getResourceFilePath(fileName);
    }

    private static String getResourceFilePath(String fileName) {
        URL resource = validateFileExists(fileName);
        return Paths.get(resource.getPath()).toString();
    }

    private static URL validateFileExists(String fileName) {
        URL resource = FilePathLoader.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException(FILE_NOT_FOUND.getMessage());
        }
        return resource;
    }
}
