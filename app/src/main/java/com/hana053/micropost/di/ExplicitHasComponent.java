package com.hana053.micropost.di;

public interface ExplicitHasComponent<C> extends HasComponent {
    Class<C> getComponentType();
}
