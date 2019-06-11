package devgranlund.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-11.
 */
public class InstalledPackageTest {
    
    @Test
    public void compareToWorksCorrectly() {
        List<InstalledPackage> packages = new ArrayList<>();
        packages.add(new InstalledPackage("man", null, null));
        packages.add(new InstalledPackage("ls", null, null));
        packages.add(new InstalledPackage("java", null, null));
        packages.add(new InstalledPackage("vi", null, null));
        packages.add(new InstalledPackage("python", null, null));

        Collections.sort(packages);

        Assert.assertEquals("java", packages.get(0).getName());
        Assert.assertEquals("ls", packages.get(1).getName());
        Assert.assertEquals("man", packages.get(2).getName());
        Assert.assertEquals("python", packages.get(3).getName());
        Assert.assertEquals("vi", packages.get(4).getName());
    }
    
}