package devgranlund.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is used to read file from resources-folder of this program.
 * Class was used during development.
 *
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-16.
 */
public class ResourceFileReader extends FileReader {

    public ResourceFileReader(String fileName) {
        super(fileName);
    }

    @Override
    public Stream<String> getFileContentInStream() {
        return getFileContentFromResourcesInStream();
    }

    /**
     * Opens file from resources and returns it's contents in List.
     * One line in the file is one entry in the list.
     *
     * @return file content
     * @note Used only in testing.
     */
    protected List<String> getFileContentFromResourcesInList() {
        List<String> lines;
        Stream<String> stream = getFileContentFromResourcesInStream();
        lines = stream.collect(Collectors.toList());
        return lines;
    }

    /**
     * Opens file from resources and returns it's contents in Stream.
     * <p>
     * TODO fix this
     *
     * @return Stream object that contains text file lines as a content.
     * @note is, r and br cannot be closed here. Closing one of these resources
     * results stream to be closed. This might be because of stream lazy processing?
     */
    public Stream<String> getFileContentFromResourcesInStream() {
        Stream<String> stream;

        try {

            final InputStream is = getClass().getResourceAsStream("/" + fileName);
            final Reader r = new InputStreamReader(is, StandardCharsets.UTF_8);
            final BufferedReader br = new BufferedReader(r);
            stream = br.lines();

        } catch (Exception exception) {
            throw new RuntimeException("FileReader: file cannot be read");
        }

        return stream;
    }
}
