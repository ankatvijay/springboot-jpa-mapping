package com.ankat.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileLoaderTest {

    @Test
    void testGivenNon_WhenNewObject_ThenReturnError() throws ClassNotFoundException {
        // Given

        // When
        Class<?> cls = Class.forName("com.ankat.util.FileLoader");
        IllegalAccessException exception = assertThrows(IllegalAccessException.class, cls::newInstance);

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo("class com.ankat.util.FileLoaderTest cannot access a member of class com.ankat.util.FileLoader with modifiers \"private\"");
    }

    @Test
    void testGivenFileName_WhenGetFileFromResourceCall_ThenReturnFile() {
        // Given
        String fileName = "test.json";

        // When
        File file = FileLoader.getFileFromResource(fileName);

        // Then
        Assertions.assertThat(file).isNotNull();
        Assertions.assertThat(file).isFile();
    }

    @Test
    void testGivenRandomFileName_WhenGetFileFromResourceCall_ThenReturnError() {
        // Given
        String fileName = RandomStringUtils.random(10, true, false);

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> FileLoader.getFileFromResource(fileName));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo("Cannot invoke \"java.net.URL.toURI()\" because the return value of \"java.lang.ClassLoader.getResource(String)\" is null");
    }
}