package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.common.ToolingProvider;

import javax.lang.model.element.PackageElement;
import java.util.Optional;

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
