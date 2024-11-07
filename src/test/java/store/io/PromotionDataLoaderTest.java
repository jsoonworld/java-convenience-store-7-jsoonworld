package store.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class PromotionDataLoaderTest {

    private final FileDataLoader promotionDataLoader = new PromotionDataLoader();

    @Test
    @DisplayName("정상적으로 파일을 읽어오는 경우")
    void loadFileSuccess(@TempDir Path tempDir) throws IOException {
        // given
        Path filePath = tempDir.resolve("promotions.md");
        Files.write(filePath, List.of("탄산2+1,2,1,2024-01-01,2024-12-31", "MD추천상품,1,1,2024-01-01,2024-12-31"));

        // when
        List<String> lines = promotionDataLoader.loadFile(filePath.toString());

        // then
        Assertions.assertThat(lines).hasSize(2);
        Assertions.assertThat(lines).containsExactly("탄산2+1,2,1,2024-01-01,2024-12-31", "MD추천상품,1,1,2024-01-01,2024-12-31");
    }

    @Test
    @DisplayName("파일이 존재하지 않는 경우 IOException 발생")
    void loadFileNotFound() {
        // given
        String nonExistentFilePath = "non_existent_file.md";

        // when & then
        Assertions.assertThatThrownBy(() -> promotionDataLoader.loadFile(nonExistentFilePath))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("[ERROR] 파일이 존재하지 않습니다");
    }
}