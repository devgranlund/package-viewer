package devgranlund.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import devgranlund.domain.InstalledPackage;
import devgranlund.ui.PackageRenderer;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public class PackageServiceTest {

    private static final String TEST_FILE_NAME = "status";

    @Test
    public void packageNamesListContainsLess() {
        List<String> packages = PackageService.getPackageNamesInList(TEST_FILE_NAME, false);
        Assert.assertTrue("package less can be found from list", packages.contains("less"));
    }

    @Test
    public void packageNamesListIsOrdered() {
        List<String> packages = PackageService.getPackageNamesInList(TEST_FILE_NAME, false);
        List tmp = new ArrayList(packages);
        Collections.sort(tmp);
        Assert.assertTrue("packageNamesList is ordered", tmp.equals(packages));
    }

    @Test
    public void domainModelIsGenerated() {
        Map<String, InstalledPackage> domainModel = getDomainModel();
        Assert.assertNotNull("Domain model is not null", domainModel);
        Assert.assertTrue("Domain model contains some data", domainModel.size() > 0);
    }

    @Test
    public void installedPackageRsyncIsGeneratedCorrectly() {
        Map<String, InstalledPackage> domainModel = getDomainModel();
        Assert.assertTrue("Domain model contains rsync", domainModel.containsKey("rsync"));
        InstalledPackage ip = domainModel.get("rsync");
        Assert.assertNotNull("InstalledPackage 'rsync' is not null", ip);
        Assert.assertEquals("rsync", ip.getName());
        Assert.assertEquals("fast, versatile, remote (and local) file-copying tool", ip.getDescription());
        Assert.assertEquals("rsync has five dependencies", 5, ip.getDepends().size());
        Assert.assertEquals(1, ip.getBidirectionalLinks().size());
        Assert.assertTrue(ip.getBidirectionalLinks().contains("ubuntu-standard"));
    }

    @Test
    public void installedPackageWithNoDependenciesGeneratedCorrectly() {
        Map<String, InstalledPackage> domainModel = getDomainModel();
        Assert.assertTrue("Domain model contains java-common", domainModel.containsKey("java-common"));
        InstalledPackage ip = domainModel.get("java-common");
        Assert.assertNotNull("InstalledPackage 'java-common' is not null", ip);
        Assert.assertEquals("java-common", ip.getName());
        Assert.assertEquals("java-common, no dependencies", 0, ip.getDepends().size());
        Assert.assertEquals(2, ip.getBidirectionalLinks().size());
        Assert.assertTrue(ip.getBidirectionalLinks().contains("openjdk-6-jre-headless"));
        Assert.assertTrue(ip.getBidirectionalLinks().contains("libecj-java"));
    }

    @Test
    public void dependsSetIsGeneratedCorrectly() {
        String line = "Depends: libacl1 (>= 2.2.51-3), libc6 (>= 2.8), libpopt0 (>= 1.16), lsb-base (>= 3.2-14), base-files (>= 4.0.1)\n";
        Set<String> depends = PackageService.generateDependsSetFromLine(line);
        Assert.assertNotNull("Set is not null", depends);
        Assert.assertEquals("Set contains 5 dependencies", 5, depends.size());
        Assert.assertTrue("libc6 can be found from the Set", depends.contains("libc6"));
    }

    @Test
    public void dependsSetIsGeneretadCorrectlyWhenPipesExist() {
        String line = "Depends: default-jre-headless | java2-runtime-headless, libfop-java";
        Set<String> depends = PackageService.generateDependsSetFromLine(line);
        Assert.assertNotNull("Set is not null", depends);
        Assert.assertEquals("Set contains 3 dependencies", 3, depends.size());
        Assert.assertTrue(depends.contains("default-jre-headless"));
        Assert.assertTrue(depends.contains("java2-runtime-headless"));
        Assert.assertTrue(depends.contains("libfop-java"));
    }

    @Test(expected = RuntimeException.class)
    public void prodEnvironmentInTestFails(){
        Map<String, InstalledPackage> domainModel = PackageService.getDomainModel(TEST_FILE_NAME, true);
        Assert.assertNotNull(domainModel);
    }
    
    // Helper-method for getting domainModel
    private Map<String, InstalledPackage> getDomainModel() {
        return PackageService.getDomainModel(TEST_FILE_NAME, false);
    }
}
