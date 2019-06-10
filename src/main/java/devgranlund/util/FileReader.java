package devgranlund.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public class FileReader {
    
    private String fileName;
    
    public FileReader (String fileName){
        this.fileName = fileName;
    }

    /**
     * Opens file from resources and returns it's contents in List. 
     * One line in the file is one entry in the list. 
     * 
     * @return
     */
    protected List<String> getFileContentFromResoucesInList(){
        List<String> lines = new ArrayList<>();
        Stream<String> stream = getFileContentFromResourcesInStream();
        lines = stream.collect(Collectors.toList());
        return lines;
    }

    /**
     * Opens file from resources and returns it's contents in Stream. 
     * 
     * @return
     */
    protected Stream<String> getFileContentFromResourcesInStream(){
        Stream<String> stream = null;
        Path path;
        
        try {
            path = Paths.get(Thread.currentThread().getContextClassLoader().getResource(fileName).toURI());
            stream = Files.lines(path);
        } catch (URISyntaxException | IOException exception){
            throw new RuntimeException("FileReader: file cannot be read");
        }
        
        return stream;
    }
    
}
