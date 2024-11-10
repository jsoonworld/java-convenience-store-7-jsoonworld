package store.io.output;

import static store.exception.ErrorMessage.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DefaultFileDataSaver implements FileDataSaver {

    @Override
    public void saveFile(String filePath, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            throw new IOException(FILE_SAVE_ERROR.getMessage(), e);
        }
    }
}
