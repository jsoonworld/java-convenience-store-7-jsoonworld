package store.io;

import static store.exception.ErrorMessage.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import store.io.input.DefaultFileDataLoader;
import store.io.input.FileDataLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class DefaultFileDataLoaderTest {

    private final FileDataLoader fileDataLoader = new DefaultFileDataLoader();

    @Nested
    @DisplayName("성공 케이스")
    class LoadFileSuccessTests {

        @Test
        @DisplayName("정상적으로 파일을 읽어오는 경우")
        void loadFileSuccess(@TempDir Path tempDir) throws IOException {
            // given
            Path filePath = tempDir.resolve("testfile.md");
            Files.write(filePath, List.of("먹태깡,1000,10,반짝할인", "허니버터칩,2000,5,null"));

            // when
            List<String> lines = fileDataLoader.loadFile(filePath.toString());

            // then
            Assertions.assertThat(lines).hasSize(2);
            Assertions.assertThat(lines).containsExactly("먹태깡,1000,10,반짝할인", "허니버터칩,2000,5,null");
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class LoadFileErrorTests {

        @Test
        @DisplayName("파일이 존재하지 않는 경우 IOException 발생")
        void loadFileNotFound() {
            // given
            String nonExistentFilePath = "non_existent_file.md";

            // when & then
            Assertions.assertThatThrownBy(() -> fileDataLoader.loadFile(nonExistentFilePath))
                    .isInstanceOf(IOException.class)
                    .hasMessageContaining(FILE_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("읽기 오류 발생 시 IOException 발생")
        void loadFileReadError() {
            // given
            String invalidFilePath = "invalid_file_path.md";

            // when & then
            Assertions.assertThatThrownBy(() -> fileDataLoader.loadFile(invalidFilePath))
                    .isInstanceOf(IOException.class)
                    .hasMessageContaining(FILE_NOT_FOUND.getMessage());
        }
    }
}
