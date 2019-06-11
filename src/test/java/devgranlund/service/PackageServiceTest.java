package devgranlund.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-10.
 */
public class PackageServiceTest {
    
    @Test
    public void packageNamesListContainsLess(){
        List<String> packages = PackageService.getPackageNamesInList("status");
        Assert.assertTrue("package less can be found from list", packages.contains("less"));
    }
    
    @Test
    public void packageNamesListIsOrdered() {
        List<String> packages = PackageService.getPackageNamesInList("status");
        List tmp = new ArrayList(packages);
        Collections.sort(tmp);
        Assert.assertTrue("packageNamesList is ordered", tmp.equals(packages));        
    }
}
