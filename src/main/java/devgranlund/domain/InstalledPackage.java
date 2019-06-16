package devgranlund.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Domain class for installed package. Class is immutable.
 * <p>
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

    private final Set<String> bidirectionalLinks;

    /**
     * New object should be created only through this constructor to guarantee immutability.
     *
     * @param name - name of the package
     * @param description - package description
     * @param depends - set containing class names that this class depends
     */
    public InstalledPackage(String name, String description, Set<String> depends) {
        this.name = name;
        this.description = description;
        if (depends != null) {
            this.depends = new HashSet<>(depends);
        } else {
            this.depends = new HashSet<>();
        }
        this.bidirectionalLinks = new HashSet<>();
    }

    private InstalledPackage(String name, String description, Set<String> depends, Set<String> bidirectionalLinks) {
        this.name = name;
        this.description = description;
        this.depends = new HashSet<>(depends);
        this.bidirectionalLinks = new HashSet<>(bidirectionalLinks);
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

    public Set<String> getBidirectionalLinks() {
        return new HashSet<>(bidirectionalLinks);
    }

    /**
     * Method to add new bidirectional link. Does not mutate current object,
     * new object with added content is returned.
     *
     * @param packageName name of the package (to be added as a bidirectional link)
     * @return new object with added content
     * @usage objectReference = objectReference.addBidirectionalLink(link)
     */
    public InstalledPackage addBidirectionalLink(String packageName) {
        Set<String> newBidirectionalLink = new HashSet<>(this.bidirectionalLinks);
        newBidirectionalLink.add(packageName);
        return new InstalledPackage(this.name, this.description, this.depends, newBidirectionalLink);
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
        if (o instanceof InstalledPackage) {
            InstalledPackage ip = (InstalledPackage) o;
            return (this.name.compareTo(ip.name));
        } else {
            return -1;
        }
    }
}
