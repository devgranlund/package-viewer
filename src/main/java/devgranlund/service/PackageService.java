package devgranlund.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import devgranlund.util.FileReader;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public class PackageService {
    
    public static List<String> getPackageNamesInList (String fileName){
        List<String> packageNames = new ArrayList<>();
        FileReader fileReader = new FileReader(fileName);
        Stream<String> stream = fileReader.getFileContentFromResourcesInStream();
        packageNames = stream
                .filter(line -> line.startsWith("Package:"))
                .map(line -> line.split("\\s+")[1])
                .sorted()
                .collect(Collectors.toList());
        return packageNames;
    }
    
}
