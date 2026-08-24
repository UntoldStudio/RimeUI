package top.untoldstudio.rimeui.core.font;

import org.lwjgl.util.freetype.FT_Face;

import java.nio.ByteBuffer;

public record Font(FT_Face face, long fontPointer, long bufferPointer, int defaultSize, String fontPath, ByteBuffer memoryBuffer) {
}
