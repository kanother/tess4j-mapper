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

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;

public class TextReader {

    private final Tesseract ocr;

    private TextReader(final String tessdataDir) {
        ocr = new Tesseract();
        ocr.setDatapath(tessdataDir);
        ocr.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_WORD);
        ocr.setConfigs(Collections.singletonList("bazaar"));
    }

    public static TextReader create(final String tessDataDir) {
        return new TextReader(tessDataDir);
    }

    public String read(final String fileName,
                       final int x,
                       final int y,
                       final int width,
                       final int height) throws TesseractException {
        final File file = Paths.get(fileName).toFile();
        if (! file.exists()) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        return ocr.doOCR(file, new Rectangle(x, y, width, height))
                .replaceAll("[\\n\\s]", "");
    }
}
