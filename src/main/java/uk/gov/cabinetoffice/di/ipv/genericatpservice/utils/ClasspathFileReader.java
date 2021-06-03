package uk.gov.cabinetoffice.di.ipv.genericatpservice.utils;

import java.io.*;
import java.util.stream.Collectors;

public abstract class ClasspathFileReader {
    public static Reader getResourceFileReader(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader;
//                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}
