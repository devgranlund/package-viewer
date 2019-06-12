package devgranlund.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Domain class for installed package. Class is immutable. 
 * 
 * Design assumption is that the name of the package is unique in the OS context. 
 * 
 * @author tuomas.granlund@gmail.com
 * @since 2019-06-11.
 */
public final class InstalledPackage implements Comparable {
    
    // unique name
    private final String name;
    
    private final String description;
    
    // unique names
    private final Set<String> depends;
    
    //private Set<String> dependentOn;
    
    public InstalledPackage(String name, String description, Optional<Set<String>> dependsOptional){
        this.name = name;
        this.description = description;
        if (dependsOptional.isPresent()){
            this.depends = new HashSet<>(dependsOptional.get());    
        } else {
            this.depends = new HashSet<>();
        }
        
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getDepends() {
        return new HashSet<>(depends);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstalledPackage that = (InstalledPackage) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof InstalledPackage){
            InstalledPackage ip = (InstalledPackage) o;
            return (this.name.compareTo(ip.name));
        } else {
            return -1;
        }
    }
}
