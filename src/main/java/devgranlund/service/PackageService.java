package devgranlund.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import devgranlund.domain.InstalledPackage;
import devgranlund.util.FileReader;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public class PackageService {
    
    public static List<String> getPackageNamesInList (String fileName){
        List<String> packageNames;
        final FileReader fileReader = new FileReader(fileName);
        Stream<String> stream = fileReader.getFileContentFromResourcesInStream();
        packageNames = stream
                .filter(line -> line.startsWith("Package:"))
                .map(line -> line.split("\\s+")[1])
                .sorted()
                .collect(Collectors.toList());
        return packageNames;
    }
    
    public static Map<String, InstalledPackage> getDomainModel (String fileName){
        List<String> lines;
        final FileReader fileReader = new FileReader(fileName);
        Stream<String> stream = fileReader.getFileContentFromResourcesInStream();
        lines = stream
                .filter(line -> 
                        line.startsWith("Package:")
                        || 
                        line.startsWith("Depends:")
                        ||
                        line.startsWith("Description:")
                        ||
                        line.startsWith(" ")
                        )
                .collect(Collectors.toList());
        
        Map<String, InstalledPackage> domainModel = new HashMap<>();
        String name = "";
        String description = "";
        Set<String> depends = new HashSet<>();
        for (String line : lines){
            if (line.startsWith("Package:")){
                name = getDataFromLine(line);
            } 
            else if (line.startsWith("Description:")){
                description = getDataFromLine(line);
            } 
            else if (line.startsWith("Depends:")){
                depends = generateDependsSetFromLine(line);
            }
            // end of "package object"
            else if (line.startsWith(" ")){
                domainModel.put(name, new InstalledPackage(name, description, Optional.of(depends)));
            }
        }
        
        return domainModel;
    }

    /**
     * Generates Set containing package names. 
     * See param text line format from below. 
     * 
     * @param line
     * - expected format: 'Depends: libacl1 (>= 2.2.51-3), libc6 (>= 2.8), libpopt0 (>= 1.16), lsb-base (>= 3.2-14), base-files (>= 4.0.1)\n'
     * @return Set with package names
     */
    protected static Set<String> generateDependsSetFromLine(String line){
        Set<String> depends = new HashSet<>();
        String data = getDataFromLine(line);
        String[] dependencies = data.split(",");
        for (String dependency : dependencies){
            depends.add(dependency.trim().split(" ")[0]);
        }
        return depends;
    }

    /**
     * Rationale to use colon as a separator:
     * 
     * "Each paragraph consists of a series of data fields. 
     * Each field consists of the field name followed by a colon 
     * and then the data/value associated with that field. 
     * The field name is composed of US-ASCII characters excluding 
     * control characters, space, and colon (i.e., characters 
     * in the ranges U+0021 (!) through U+0039 (9), and U+003B (;) 
     * through U+007E (~), inclusive)."
     * 
     *  - Debian Policy Manual v4.3.0.3,
     *  https://www.debian.org/doc/debian-policy/ch-controlfields.html
     * 
     * @param line
     * @return data content (from the text line)
     */
    private static String getDataFromLine(String line){
        return line.split(":")[1].trim();
    }
}
