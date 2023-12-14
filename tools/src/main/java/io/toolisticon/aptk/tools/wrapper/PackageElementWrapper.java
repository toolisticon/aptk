package io.toolisticon.aptk.tools.wrapper;

import javax.lang.model.element.PackageElement;

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

}
