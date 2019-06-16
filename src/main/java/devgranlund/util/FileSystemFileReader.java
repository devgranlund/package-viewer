package devgranlund.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * This class reads file from file system (Ubuntu/Debian) and returns contents as stream.
 *
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-16.
 */
public class FileSystemFileReader extends FileReader {

    public FileSystemFileReader(String fileName) {
        super(fileName);
    }

    @Override
    public Stream<String> getFileContentInStream() {
        File file = new File(this.fileName);
        Stream<String> lines;
        try {
            lines = Files.lines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
        return lines;
    }
}
