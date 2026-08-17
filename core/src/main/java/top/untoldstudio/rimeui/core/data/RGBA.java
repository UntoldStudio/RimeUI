package top.untoldstudio.rimeui.core.data;

import java.awt.*;
import java.nio.ByteBuffer;

public record RGBA(int red, int green, int blue, int alpha) {
    public RGBA withRed(int red){
        return new RGBA(red, green, blue, alpha);
    }
    public RGBA withGreen(int green){
        return new RGBA(red, green, blue, alpha);
    }
    public RGBA withBlue(int blue){
        return new RGBA(red, green, blue, alpha);
    }
    public RGBA withAlpha(int alpha){
        return new RGBA(red, green, blue, alpha);
    }
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

    public static final RGBA WHITE = new RGBA(255, 255, 255, 255);
    public static final RGBA BLACK = new RGBA(0, 0, 0, 255);
    public static final RGBA RED = new RGBA(255, 0, 0, 255);
    public static final RGBA GREEN = new RGBA(0, 255, 0, 255);
    public static final RGBA BLUE = new RGBA(0, 0, 255, 255);
}
