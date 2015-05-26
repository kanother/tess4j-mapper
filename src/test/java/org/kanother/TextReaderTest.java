/*
 * Copyright 2015 Hidetomo Kanazawa (https://github.com/kanother)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kanother;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TextReaderTest {

    private String fileName;

    private String tessdataDir;

    @Before
    public void setUp() throws Exception {
        final URL imageUrl =
                ClassLoader.getSystemClassLoader().getResource("test.png");
        fileName = imageUrl == null
                ? null : Paths.get(imageUrl.toURI()).toString();
        final URL tessdataUrl =
                ClassLoader.getSystemClassLoader().getResource("tessdata");
        tessdataDir = tessdataUrl == null
                ? null : Paths.get(tessdataUrl.toURI()).toString();
    }

    @Test
    public void testReadChars() throws Exception {
        final TextReader textReader = TextReader.create(tessdataDir);
        final String actual = textReader.read(fileName, 10, 10, 80, 40);
        assertThat(actual, is("Test"));
    }

    @Test
    public void testReadDigits() throws Exception {
        final TextReader textReader = TextReader.create(tessdataDir);
        final String actual = textReader.read(fileName, 110, 85, 75, 35);
        assertThat(actual, is("1234"));
    }
}