package store.util;

import static store.exception.ErrorMessage.*;

import java.net.URL;
import java.nio.file.Paths;

public class FilePathLoader {

    public static String getResourceFilePath(String fileName) {
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
