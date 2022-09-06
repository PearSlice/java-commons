package com.github.pearslice.commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static com.github.pearslice.commons.UniqueElementCollector.toUniqueElement;

class UniqueElementCollectorTest {

    @Test
    void uniqueElement() {


        String elementDescription = "message";
        String word = "Hello";
        List<String> list = List.of(word);


        String message = list.stream().collect(toUniqueElement(String.class, elementDescription));

        Assertions.assertEquals(word, message);

    }

    @Test
    void exceptionOnTwoElements() {
        String elementDescription = "message";
        List<String> list = List.of("Hello", "World");

        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class, () ->
                list
                        .stream()
                        .collect(toUniqueElement(String.class, elementDescription))
        );

        Assertions.assertEquals("Duplicate [String](" + elementDescription + ") found", thrown.getMessage());
    }

    @Test
    void exceptionOnNoElements() {
        String elementDescription = "message";
        List<String> list = Collections.emptyList();

        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class, () ->
                list
                        .stream()
                        .collect(toUniqueElement(String.class, elementDescription))
        );

        Assertions.assertEquals("No [String](" + elementDescription + ") found", thrown.getMessage());
    }

    @Test
    void exceptionOnNoElementsAfterFilter() {
        String elementDescription = "message";
        List<String> list = List.of("Hello", "World");

        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class, () ->
                list
                        .stream()
                        .filter(msg -> !msg.contains("l"))
                        .collect(toUniqueElement(String.class, elementDescription))
        );

        Assertions.assertEquals("No [String](" + elementDescription + ") found", thrown.getMessage());
    }

    @Test
    void exceptionOnTwoElementsAndNoDescription() {
        String elementDescription = "message";
        List<String> list = List.of("Hello", "World");

        IllegalStateException thrown = Assertions.assertThrows(IllegalStateException.class, () ->
                list
                        .stream()
                        .collect(toUniqueElement(String.class, elementDescription))
        );

        Assertions.assertEquals("Duplicate [String](" + elementDescription + ") found", thrown.getMessage());
    }

    @Test
    void exceptionOnNoElementsAndNoDescription() {
        String elementDescription = "message";
        List<String> list = Collections.emptyList();

        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class, () ->
                list
                        .stream()
                        .collect(toUniqueElement(String.class, elementDescription))
        );

        Assertions.assertEquals("No [String](" + elementDescription + ") found", thrown.getMessage());
    }
}