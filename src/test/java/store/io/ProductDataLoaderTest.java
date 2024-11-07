package store.io;

import static store.exception.ErrorMessage.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class ProductDataLoaderTest {

    private final FileDataLoader productDataLoader = new ProductDataLoader();

    @Test
    @DisplayName("정상적으로 파일을 읽어오는 경우")
    void loadFileSuccess(@TempDir Path tempDir) throws IOException {
        // given
        Path filePath = tempDir.resolve("products.md");
        Files.write(filePath, List.of("콜라,1000,10,탄산2+1", "사이다,1000,8,탄산2+1"));

        // when
        List<String> lines = productDataLoader.loadFile(filePath.toString());

        // then
        Assertions.assertThat(lines).hasSize(2);
        Assertions.assertThat(lines).containsExactly("콜라,1000,10,탄산2+1", "사이다,1000,8,탄산2+1");
    }

    @Test
    @DisplayName("파일이 존재하지 않는 경우 IOException 발생")
    void loadFileNotFound() {
        // given
        String nonExistentFilePath = "non_existent_file.md";

        // when & then
        Assertions.assertThatThrownBy(() -> productDataLoader.loadFile(nonExistentFilePath))
                .isInstanceOf(IOException.class)
                .hasMessageContaining(FILE_NOT_FOUND.getMessage());
    }
}
