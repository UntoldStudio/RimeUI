package top.untoldstudio.rimeui.core.data;

import java.awt.*;
import java.nio.ByteBuffer;

public record RGBA(int red, int green, int blue, int alpha) {
    public int toARGBInteger(){
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
    public ByteBuffer writeRGBAByteBuffer(ByteBuffer buffer){
        buffer.put((byte)red);
        buffer.put((byte)green);
        buffer.put((byte)blue);
        buffer.put((byte)alpha);
        return buffer;
    }
    public ByteBuffer toRGBAByteBuffer(){
        return writeRGBAByteBuffer(ByteBuffer.allocate(4));
    }
    public float[] toFloatArray() {
        return new float[] {
                red / 255.0f,
                green / 255.0f,
                blue / 255.0f,
                alpha / 255.0f
        };
    }
    public Color toAWTColor(){
        return new Color(red, green, blue, alpha);
    }
}
