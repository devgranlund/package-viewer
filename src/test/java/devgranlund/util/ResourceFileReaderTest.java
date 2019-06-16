package devgranlund.util;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public class ResourceFileReaderTest {

    @Test
    public void fileCanBeReadFromResources() {
        FileReader fileReader = FileReader.newInstance("status", false);
        Stream<String> lines = fileReader.getFileContentInStream();
        Assert.assertNotNull("Stream containing the file cannot be null", lines);
    }

    @Test
    public void fileContainsSomeLines() {
        ResourceFileReader fileReader = (ResourceFileReader) FileReader.newInstance("status", false);
        List<String> lines = fileReader.getFileContentFromResoucesInList();
        Assert.assertTrue("There's some lines in the list", lines.size() > 0);
    }

}
