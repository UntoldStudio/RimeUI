package top.untoldstudio.rimeui.core.source;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ResourceReader {
    public static String readString(String path) throws IOException {
        try (InputStream stream = ResourceReader.class.getResourceAsStream(path)) {
            return new String(Objects.requireNonNull(stream).readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
