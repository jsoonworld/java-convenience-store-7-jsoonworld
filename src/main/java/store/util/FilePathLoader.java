package store.util;

import static store.exception.ErrorMessage.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;

public class FilePathLoader {

    public static String getWritableFilePath(String fileName) throws IOException {
        String resourcePath = getResourceFilePath(fileName);
        Path sourcePath = Paths.get(resourcePath);
        Path targetPath = Paths.get("src/main/modifiable-data/" + fileName);

        Files.createDirectories(targetPath.getParent());

        if (!Files.exists(targetPath)) {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        return targetPath.toString();
    }

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
