package devgranlund.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    /**
     * Creates and returns list containing all package names.
     *
     * @param fileName             - source of data
     * @param runsInProductionMode - when true, file is read from actual file system
     * @return list containing all package names
     */
    public static List<String> getPackageNamesInList(String fileName, boolean runsInProductionMode) {
        List<String> packageNames;
        final FileReader fileReader = FileReader.newInstance(fileName, runsInProductionMode);
        Stream<String> stream = fileReader.getFileContentInStream();
        packageNames = stream
                .filter(line -> line.startsWith("Package:"))
                .map(line -> line.split("\\s+")[1])
                .sorted()
                .collect(Collectors.toList());
        return packageNames;
    }

    /**
     * Creates and returns domain model.
     *
     * @param fileName             - source of data
     * @param runsInProductionMode - when true, file is read from actual file system.
     * @return domain model with dependencies and bidirectional links
     */
    public static Map<String, InstalledPackage> getDomainModel(String fileName, boolean runsInProductionMode) {
        List<String> lines;
        lines = getLinesFromFile(fileName, runsInProductionMode);

        Map<String, InstalledPackage> domainModel = new HashMap<>();
        Map<String, List<String>> helperMap = new HashMap<>();

        String mainPackageName = "";
        String mainPackageDescription = "";
        Set<String> mainPackageDepends = new HashSet<>();

        for (String line : lines) {
            if (line.startsWith("Package:")) {
                mainPackageName = getDataFromLine(line);
            } else if (line.startsWith("Description:")) {
                mainPackageDescription = getDataFromLine(line);
            } else if (line.startsWith("Depends:")) {
                mainPackageDepends = generateDependsSetFromLine(line);
            }
            // end of "package entry"
            else if (line.length() == 0) {
                createPackageEntry(domainModel, helperMap, mainPackageName, mainPackageDescription, mainPackageDepends);

                // clear existing values
                mainPackageName = "";
                mainPackageDescription = "";
                mainPackageDepends = new HashSet<>();
            }
        }

        return domainModel;
    }

    /**
     * Helper-method to create new package entry to domain model
     */
    private static void createPackageEntry(Map<String, InstalledPackage> domainModel, Map<String, List<String>> helperMap, String mainPackageName, String mainPackageDescription, Set<String> mainPackageDepends) {
        domainModel.put(mainPackageName, new InstalledPackage(mainPackageName, mainPackageDescription, mainPackageDepends));

        // check if there's bidirectional dependencies in helper map that belong
        // to the package that was just created
        if (helperMap.containsKey(mainPackageName)) {
            for (String dependant : helperMap.get(mainPackageName)) {
                domainModel.put(mainPackageName, domainModel.get(mainPackageName).addBidirectionalLink(dependant));
            }
            helperMap.remove(mainPackageName);
        }
        createBIdirectionalLinks(domainModel, helperMap, mainPackageName, mainPackageDepends);
    }

    /**
     * Helper-method to create bidirectional links.
     */
    private static void createBIdirectionalLinks(Map<String, InstalledPackage> domainModel, Map<String, List<String>> helperMap, String mainPackageName, Set<String> mainPackageDepends) {
        for (String dependency : mainPackageDepends) {
            if (domainModel.containsKey(dependency)) {
                domainModel.put(dependency, domainModel.get(dependency).addBidirectionalLink(mainPackageName));
            } else {
                if (helperMap.containsKey(dependency)) {
                    helperMap.get(dependency).add(mainPackageName);
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(mainPackageName);
                    helperMap.put(dependency, list);
                }
            }
        }
    }

    /**
     * Helper-method to return lines from a file.
     */
    private static List<String> getLinesFromFile(String fileName, boolean runsInProductionMode) {
        List<String> lines;
        final FileReader fileReader = FileReader.newInstance(fileName, runsInProductionMode);
        Stream<String> stream = fileReader.getFileContentInStream();
        lines = stream
                .filter(line ->
                        line.startsWith("Package:")
                                ||
                                line.startsWith("Depends:")
                                ||
                                line.startsWith("Description:")
                                ||
                                line.length() == 0
                )
                .collect(Collectors.toList());
        return lines;
    }

    /**
     * Generates Set containing package names.
     * See param text line format from below.
     *
     * @param line - expected format: 'Depends: libacl1 (>= 2.2.51-3), libc6 (>= 2.8), libpopt0 (>= 1.16), lsb-base (>= 3.2-14), base-files (>= 4.0.1)\n'
     * @return Set with package names
     */
    protected static Set<String> generateDependsSetFromLine(String line) {
        Set<String> depends = new HashSet<>();
        String data = getDataFromLine(line);
        String[] dirtyDependencies = data.split(",");
        for (String dirty : dirtyDependencies) {
            String[] finalDependencies = dirty.split("\\|");
            for (String dependency : finalDependencies) {
                depends.add(dependency.trim().split(" ")[0]);
            }
        }
        return depends;
    }

    /**
     * Rationale to use colon as a separator:
     * <p>
     * "Each paragraph consists of a series of data fields.
     * Each field consists of the field name followed by a colon
     * and then the data/value associated with that field.
     * The field name is composed of US-ASCII characters excluding
     * control characters, space, and colon (i.e., characters
     * in the ranges U+0021 (!) through U+0039 (9), and U+003B (;)
     * through U+007E (~), inclusive)."
     * <p>
     * - Debian Policy Manual v4.3.0.3,
     * https://www.debian.org/doc/debian-policy/ch-controlfields.html
     *
     * @param line
     * @return data content (from the text line)
     */
    private static String getDataFromLine(String line) {
        return line.split(":")[1].trim();
    }
}
