package io.toolisticon.aptk.tools.wrapper;

import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;

public class PackageElementWrapper extends ElementWrapper<PackageElement>{

    private PackageElementWrapper(PackageElement packageElement){
        super(packageElement);
    }

    public String getQualifiedName() {
        return this.element.getQualifiedName().toString();
    }

    public boolean isUnnamed() {
        return this.element.isUnnamed();
    }

    public static PackageElementWrapper wrap (PackageElement element) {
        return new PackageElementWrapper(element);
    }

}
