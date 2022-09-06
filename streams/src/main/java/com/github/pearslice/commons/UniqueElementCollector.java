package com.github.pearslice.commons;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class UniqueElementCollector<T> implements Collector<T, AtomicReference<T>, T> {

    private static final Set<Characteristics> CHARACTERISTICS = Collections.emptySet();
    private final String className;
    private final String description;

    private UniqueElementCollector(String className, String description) {
        this.className = className;
        this.description = description;
    }


    public static <C> UniqueElementCollector<C> toUniqueElement(Class<C> clazz) {
        return new UniqueElementCollector<>(clazz.getSimpleName(), null);
    }

    public static <C> UniqueElementCollector<C> toUniqueElement(Class<C> clazz, String description) {
        return new UniqueElementCollector<>(clazz.getSimpleName(), description);
    }

    @Override
    public Supplier<AtomicReference<T>> supplier() {
        return AtomicReference::new;
    }

    @Override
    public BiConsumer<AtomicReference<T>, T> accumulator() {
        return (r, v) -> {
            if (r.get() == null) {
                r.set(v);
            } else {
                throw new IllegalStateException("Duplicate [" + className + "]"
                        + (description != null ? ("(" + description + ")") : "") + " found");
            }
        };
    }

    @Override
    public BinaryOperator<AtomicReference<T>> combiner() {
        //Never used
        return (a, b) -> a;
    }

    @Override
    public Function<AtomicReference<T>, T> finisher() {
        return a -> {
            if (a.get() == null) {
                throw new NoSuchElementException("No [" + className + "]"
                        + (description != null ? ("(" + description + ")") : "") + " found");
            }
            return a.get();
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return CHARACTERISTICS;
    }
}