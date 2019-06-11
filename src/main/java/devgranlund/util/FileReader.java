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
        List<String> lines;
        Stream<String> stream = getFileContentFromResourcesInStream();
        lines = stream.collect(Collectors.toList());
        return lines;
    }

    /**
     * Opens file from resources and returns it's contents in Stream. 
     * 
     * FIXTHIS
     * Note: is, r and br cannot be closed here. Closing one of these resources
     * results stream to be closed. This might be caused by stream lazy processing?
     * 
     * @return
     */
    public Stream<String> getFileContentFromResourcesInStream(){
        Stream<String> stream = null;
        
        try {
            
            final InputStream is = getClass().getResourceAsStream("/" + fileName);
            final Reader r = new InputStreamReader(is, StandardCharsets.UTF_8);
            final BufferedReader br = new BufferedReader(r);
            stream = br.lines();
            
          } catch ( Exception exception){
            throw new RuntimeException("FileReader: file cannot be read");
        }
        
        return stream;
    }
    
}
