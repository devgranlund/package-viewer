package devgranlund.util;

import java.util.stream.Stream;

/**
 * Class to read files.
 * <p>
 * During the development and testing local resource file can be used as a input.
 * The production version uses real file from file system.
 * <p>
 * From the view point of this class, the location of the file is dependency and
 * this dependency is injected through constructor.
 *
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public abstract class FileReader {

    protected String fileName;

    protected FileReader(String fileName) {
        this.fileName = fileName;
    }

    public static FileReader newInstance(String fileName, boolean runsInProductionMode) {
        if (!runsInProductionMode) {
            return new ResourceFileReader(fileName);
        } else {
            return new FileSystemFileReader(fileName);
        }
    }

    /**
     * Opens file and returns it's contents in Stream.
     *
     * @return Stream object that contains text file lines as a content.
     */
    public abstract Stream<String> getFileContentInStream();

}
