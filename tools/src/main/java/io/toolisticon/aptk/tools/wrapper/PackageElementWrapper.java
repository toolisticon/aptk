package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.common.ToolingProvider;

import javax.lang.model.element.PackageElement;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Wrapper for PackageElement.
 */
public class PackageElementWrapper extends ElementWrapper<PackageElement> {

    /**
     * Hidden constructor.
     *
     * @param packageElement the package element to wrap
     */
    private PackageElementWrapper(PackageElement packageElement) {
        super(packageElement);
    }

    /**
     * Returns the qualified name of the package.
     *
     * @return the qualified name of the package
     */
    public String getQualifiedName() {
        return this.element.getQualifiedName().toString();
    }

    /**
     * Returns if package is the unnamed package.
     *
     * @return true if package is the unnamed package, otherwise false.
     */
    public boolean isUnnamed() {
        return this.element.isUnnamed();
    }
    
    /**
     * WARN - This method is in incubation stage and might be removed in the near future.
     * Returns the parent package of the wrapped package.
     * The annotation processor api handles all packages as unrelated, nevertheless it's a common case that packages are handled as they are related.
     * Think of configuration annotations that should be applied to all sub-packages as well.
     * WARN : The behavior of this method depends on the JDK since it uses  javax.lang.model.util.Elements.getPackageElement() . Some JDKs handle "uniquely determined" differently. So using this method might lead to inconsistent behavior.
     * @return The wrapped PackageElement, or an empty optional if it doesn't exist or if the parent package cannot be uniquely determined (i.e. parent package is used in dependency and in code under compilation).
     */

    public Optional<PackageElementWrapper> getParentPackage() {
    	
    	String qualifiedPackageName = this.getQualifiedName();
    	String [] packageToken = qualifiedPackageName.split("[.]");
    	
    	if (packageToken.length == 1) {
    		return Optional.empty();
    	} else {
    		String parentPackageName = Arrays.stream(packageToken, 0, packageToken.length-1).collect(Collectors.joining("."));
    		return PackageElementWrapper.getByFqn(parentPackageName);
    	}
    	
    }
    
    

    /**
     * Wraps a PackageElement instance.
     * Throws am IllegalArgumentException if passed element is null.
     *
     * @param element the element to wrap
     * @return a wrapper instance‚
     */
    public static PackageElementWrapper wrap(PackageElement element) {
        return new PackageElementWrapper(element);
    }

    /**
     * Gets the PackageElementWrapper by using a fully qualified package name.
     * @param fqn the fully qualified name of the package
     * @return an Optional containing the PackageElementWrapper or an empty Optional if the package element can't be found
     */
    public static Optional<PackageElementWrapper> getByFqn(String fqn) {

        PackageElement packageElement = ToolingProvider.getTooling().getElements().getPackageElement(fqn);
        return packageElement != null ? Optional.of(PackageElementWrapper.wrap(packageElement)) : Optional.empty();

    }

}
