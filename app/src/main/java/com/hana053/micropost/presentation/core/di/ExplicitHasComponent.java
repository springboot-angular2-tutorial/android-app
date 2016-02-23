package com.hana053.micropost.presentation.core.di;

public interface ExplicitHasComponent<C> extends HasComponent {
    Class<C> getComponentType();
}
