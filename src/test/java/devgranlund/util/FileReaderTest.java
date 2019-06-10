package devgranlund.util;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public class FileReaderTest {
    
    @Test
    public void fileCanBeReadFromResources() {
        FileReader fileReader = new FileReader("status");
        Stream<String> lines = fileReader.getFileContentFromResourcesInStream();
        Assert.assertNotNull("Stream containing the file cannot be null", lines);
    }
    
    @Test
    public void fileContainsSomeLines(){
        FileReader fileReader = new FileReader("status");
        List<String> lines = fileReader.getFileContentFromResoucesInList();
        Assert.assertTrue("There's some lines in the list", lines.size() > 0);
    }
    
}
