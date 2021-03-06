package com.structurizr.componentfinder;

import com.structurizr.model.Component;

import java.util.*;

/**
 * A component finder strategy that uses type information to find components, based upon a number
 * of pluggable {@link TypeMatcher} implementations.
 */
public class TypeBasedComponentFinderStrategy extends AbstractReflectionsComponentFinderStrategy {

    private List<TypeMatcher> typeMatchers = new LinkedList<>();

    public TypeBasedComponentFinderStrategy(TypeMatcher... typeMatchers) {
        this.typeMatchers.addAll(Arrays.asList(typeMatchers));
    }

    @Override
    public Collection<Component> findComponents() throws Exception {
        Collection<Component> componentsFound = new LinkedList<>();

        Set<Class<?>> types = getAllTypes();
        for (Class<?> type : types) {
            for (TypeMatcher typeMatcher : typeMatchers) {
                if (typeMatcher.matches(type)) {
                    Component component = getComponentFinder().getContainer().addComponent(
                            type.getSimpleName(),
                            type.getCanonicalName(),
                            typeMatcher.getDescription(),
                            typeMatcher.getTechnology());
                    componentsFound.add(component);
                }
            }
        }

        return componentsFound;
    }

}