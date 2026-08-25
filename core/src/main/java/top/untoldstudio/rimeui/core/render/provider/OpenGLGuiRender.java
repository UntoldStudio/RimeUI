/*
 * Copyright 2026 Untold Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.untoldstudio.rimeui.core.render.provider;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.ARBImaging.GL_BLEND_COLOR;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.freetype.FT_Bitmap;
import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.error.RenderError;
import top.untoldstudio.rimeui.core.error.ResourceError;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.render.RenderBackend;
import top.untoldstudio.rimeui.core.render.RenderBackendProvider;
import top.untoldstudio.rimeui.core.resource.ResourceReader;
import top.untoldstudio.rimeui.core.ui.MainUi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class OpenGLGuiRender extends GuiRender {
    private int savedProgram;
    private int savedVao;
    private int savedEbo;
    private int savedFbo;
    private int savedRbo;
    private int savedActiveTexture;
    private final List<Integer> savedTextureBinding = new ArrayList<>();
    private final Map<Integer, Boolean> savedEnabledMap = new HashMap<>();
    private int savedTextureUnitCount;
    private int savedBlendSrcRGB;
    private int savedBlendSrcAlpha;
    private int savedBlendDstRGB;
    private int savedBlendDstAlpha;
    private int savedBlendEquationRGB;
    private int savedBlendEquationAlpha;
    private final float[] savedBlendColor = new float[4];
    private int savedDepthFunc;
    private boolean savedDepthMask;
    private float savedDepthClearValue;
    private final double[] savedDepthRange = new double[2];
    private int savedStencilFuncFront;
    private int savedStencilRefFront;
    private int savedStencilValueMaskFront;
    private int savedStencilWriteMaskFront;
    private int savedStencilFailFront;
    private int savedStencilPassDepthFailFront;
    private int savedStencilPassDepthPassFront;
    private int savedStencilFuncBack;
    private int savedStencilRefBack;
    private int savedStencilValueMaskBack;
    private int savedStencilWriteMaskBack;
    private int savedStencilFailBack;
    private int savedStencilPassDepthFailBack;
    private int savedStencilPassDepthPassBack;
    private int savedStencilClearValue;
    private int savedCullFaceMode;
    private int savedFrontFace;
    private int savedPolygonMode;
    private float savedPolygonOffsetFactor;
    private float savedPolygonOffsetUnits;
    private final int[] savedViewport = new int[4];
    private final int[] savedScissorBox = new int[4];
    private final float[] savedColorClearValue = new float[4];
    private int savedUnpackAlignment;
    private int savedUnpackRowLength;
    private int savedUnpackSkipPixels;
    private int savedUnpackSkipRows;
    private int savedPackAlignment;
    private int savedPackRowLength;
    private int savedPackSkipPixels;
    private int savedPackSkipRows;
    private float savedLineWidth;
    private float savedPointSize;
    private int savedLogicOpMode;
    private float savedSampleCoverageValue;
    private boolean savedSampleCoverageInvert;

    private final long windowHandle;
    private final int baseShaderProgram;
    private final int textureShaderProgram;
    private final int fontShaderProgram;
    private final int projectLocation;
    private final int baseVao;
    private final int baseVbo;
    private final int textureVao;
    private final int textureVbo;
    private final int fontVao;
    private final int fontVbo;
    private final int textureProjectionLocation;
    private final int textureSamplerLocation;
    private final int fontProjectionLocation;
    private final int fontSamplerLocation;
    private final int fontColorLocation;
    private final FloatBuffer textureVertBuffer = BufferUtils.createFloatBuffer(32);
    private final FloatBuffer fontVertBuffer = BufferUtils.createFloatBuffer(32);
    private FloatBuffer vertBuffer;
    private int vertCount = 0;
    private int vboCapacityBytes;
    private final Matrix4f projectionMatrix = new Matrix4f();
    private final float[] projectionMatrixArray = new float[16];
    private final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
    private final IntBuffer int4Buffer = BufferUtils.createIntBuffer(4);
    private final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(4);
    private final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(1);
    private final DoubleBuffer doubleBuffer = BufferUtils.createDoubleBuffer(2);
    private int fontAtlasTextureId;
    private int atlasWidth = 1024;
    private int atlasHeight = 1024;
    private int atlasCursorX = 0;
    private int atlasCursorY = 0;
    private int atlasRowHeight = 0;
    private FloatBuffer fontBatchVertBuffer;
    private int fontBatchVertCount = 0;
    private int fontBatchVboCapacityFloats;
    private RGBA fontRenderColor;

    public OpenGLGuiRender(long windowHandle){
        this.windowHandle = windowHandle;

        String baseVertSource;
        try {
            baseVertSource = ResourceReader.readString("/shader/base/vert.glsl");
        } catch (IOException e){
            throw new ResourceError("Cannot read base glsl vertex shader!");
        }
        String baseFragSource;
        try {
            baseFragSource = ResourceReader.readString("/shader/base/frag.glsl");
        } catch (IOException e){
            throw new ResourceError("Cannot read base glsl frag shader!");
        }
        baseShaderProgram = loadProgram(baseVertSource, baseFragSource);
        String textureVertSource;
        try {
            textureVertSource = ResourceReader.readString("/shader/texture/vert.glsl");
        } catch (IOException e){
            throw new ResourceError("Cannot read texture glsl vertex shader!");
        }
        String textureFragSource;
        try {
            textureFragSource = ResourceReader.readString("/shader/texture/frag.glsl");
        } catch (IOException e){
            throw new ResourceError("Cannot read texture glsl frag shader!");
        }

        textureShaderProgram = loadProgram(textureVertSource, textureFragSource);
        textureProjectionLocation = glGetUniformLocation(textureShaderProgram, "uProjection");
        textureSamplerLocation = glGetUniformLocation(textureShaderProgram, "uTexture");
        textureVao = glGenVertexArrays();
        textureVbo = glGenBuffers();
        int ebo = glGenBuffers();
        glBindVertexArray(textureVao);
        int textureStride = 8 * Float.BYTES;
        glBindBuffer(GL_ARRAY_BUFFER, textureVbo);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, textureStride, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, textureStride, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 4, GL_FLOAT, false, textureStride, 4 * Float.BYTES);
        glEnableVertexAttribArray(2);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);

        String fontVertSource;
        try {
            fontVertSource = ResourceReader.readString("/shader/font/vert.glsl");
        } catch (IOException e){
            throw new ResourceError("Cannot read texture glsl vertex shader!");
        }
        String fontFragSource;
        try {
            fontFragSource = ResourceReader.readString("/shader/font/frag.glsl");
        } catch (IOException e){
            throw new ResourceError("Cannot read texture glsl frag shader!");
        }

        fontShaderProgram = loadProgram(fontVertSource, fontFragSource);
        fontColorLocation = glGetUniformLocation(fontShaderProgram, "uColor");
        fontProjectionLocation = glGetUniformLocation(fontShaderProgram, "uProjection");
        fontSamplerLocation = glGetUniformLocation(fontShaderProgram, "uTexture");
        glGetUniformLocation(fontShaderProgram, "uTexture");
        fontVao = glGenVertexArrays();
        fontVbo = glGenBuffers();
        glBindVertexArray(fontVao);
        int fontStride = 4 * Float.BYTES;
        glBindBuffer(GL_ARRAY_BUFFER, fontVbo);
        glBufferData(GL_ARRAY_BUFFER, 16 * Float.BYTES, GL_STREAM_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, fontStride, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, fontStride, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);
        int initialFontBatchFloats = 4096 * 4;
        glBindBuffer(GL_ARRAY_BUFFER, fontVbo);
        glBufferData(GL_ARRAY_BUFFER, initialFontBatchFloats * Float.BYTES, GL_STREAM_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        fontBatchVboCapacityFloats = initialFontBatchFloats;
        fontBatchVertBuffer = BufferUtils.createFloatBuffer(initialFontBatchFloats);

        int[] indices = { 0, 1, 2, 0, 2, 3 };
        IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length);
        indexBuffer.put(indices).flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, textureVbo);
        glBufferData(GL_ARRAY_BUFFER, 32 * Float.BYTES, GL_STREAM_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        projectLocation = glGetUniformLocation(baseShaderProgram, "uProjection");
        baseVao = glGenVertexArrays();
        glBindVertexArray(baseVao);
        baseVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, baseVbo);
        int stride = 6 * Float.BYTES;
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, stride, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);
        int maxVertices = 65536 * 3;
        vertBuffer = BufferUtils.createFloatBuffer(maxVertices * 6);
        vboCapacityBytes = maxVertices * 6 * Float.BYTES;
        glBindBuffer(GL_ARRAY_BUFFER, baseVbo);
        glBufferData(GL_ARRAY_BUFFER, vboCapacityBytes, GL_STATIC_DRAW);

        fontAtlasTextureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, fontAtlasTextureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_R8, atlasWidth, atlasHeight, 0, GL_RED, GL_UNSIGNED_BYTE, (ByteBuffer)null);
    }

    @Override
    public void drawGlyph(FT_Bitmap bitmap, int glyphX, int glyphY, RGBA color) {
        int width = bitmap.width();
        int height = bitmap.rows();
        int pitch = bitmap.pitch();
        if (width == 0 || height == 0) return;

        if (atlasCursorX + width > atlasWidth) {
            atlasCursorX = 0;
            atlasCursorY += atlasRowHeight;
            atlasRowHeight = 0;
        }
        if (atlasCursorY + height > atlasHeight) {
            flushFontBatch();
            expandAtlas();
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, fontAtlasTextureId);
        }

        ByteBuffer pixelBuffer = bitmap.buffer(pitch * height);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glPixelStorei(GL_UNPACK_ROW_LENGTH, pitch);
        assert pixelBuffer != null;
        glTexSubImage2D(GL_TEXTURE_2D, 0, atlasCursorX, atlasCursorY, width, height, GL_RED, GL_UNSIGNED_BYTE, pixelBuffer);
        glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 4);

        float u0 = (float) atlasCursorX / atlasWidth;
        float u1 = (float) (atlasCursorX + width) / atlasWidth;
        float vTop = 1.0f - (float) atlasCursorY / atlasHeight;
        float vBottom = 1.0f - (float) (atlasCursorY + height) / atlasHeight;

        atlasCursorX += width;
        if (height > atlasRowHeight) {
            atlasRowHeight = height;
        }

        fontRenderColor = color;

        float x1 = glyphX + width;
        float y1 = glyphY + height;

        putFontVertex((float) glyphX, (float) glyphY, u0, vTop);
        putFontVertex(x1, (float) glyphY, u1, vTop);
        putFontVertex(x1, y1, u1, vBottom);
        putFontVertex((float) glyphX, (float) glyphY, u0, vTop);
        putFontVertex(x1, y1, u1, vBottom);
        putFontVertex((float) glyphX, y1, u0, vBottom);
    }

    private void putFontVertex(float x, float y, float u, float v) {
        if (fontBatchVertBuffer.remaining() < 4) {
            int newCapacity = fontBatchVertBuffer.capacity() * 2;
            FloatBuffer newBuffer = BufferUtils.createFloatBuffer(newCapacity);
            fontBatchVertBuffer.flip();
            newBuffer.put(fontBatchVertBuffer);
            fontBatchVertBuffer = newBuffer;
        }
        fontBatchVertBuffer.put(x).put(y).put(u).put(v);
        fontBatchVertCount += 1;
    }

    @Override
    public void beginTextRendering() {
        atlasCursorX = 0;
        atlasCursorY = 0;
        atlasRowHeight = 0;
        fontBatchVertBuffer.clear();
        fontBatchVertCount = 0;
        fontRenderColor = null;

        glUseProgram(fontShaderProgram);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, fontAtlasTextureId);
        glUniform1i(fontSamplerLocation, 0);
    }
    @Override
    public void endTextRendering() {
        flushFontBatch();
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void drawTexture(int textureId, int ax, int ay, int bx, int by,
                                   float u0, float u1, float v0, float v1,
                                   int aRed, int aGreen, int aBlue, int aAlpha,
                                   int bRed, int bGreen, int bBlue, int bAlpha,
                                   int cRed, int cGreen, int cBlue, int cAlpha,
                                   int dRed, int dGreen, int dBlue, int dAlpha)
    {
        float tmp = v0;
        v0 = v1;
        v1 = tmp;
        glUseProgram(textureShaderProgram);
        drawTexture(
                textureVertBuffer, textureVao, textureVbo, textureSamplerLocation, textureId, ax, ay, bx, by,
                u0, u1, v0, v1, aRed, aGreen, aBlue, aAlpha, bRed, bGreen, bBlue, bAlpha, cRed, cGreen, cBlue, cAlpha, dRed, dGreen, dBlue, dAlpha
        );
    }

    private static void drawTexture(FloatBuffer vertBuffer, int vao, int vbo, int samplerLocation, int textureId, int ax, int ay, int bx, int by,
                             float u0, float u1, float v0, float v1,
                             int aRed, int aGreen, int aBlue, int aAlpha,
                             int bRed, int bGreen, int bBlue, int bAlpha,
                             int cRed, int cGreen, int cBlue, int cAlpha,
                             int dRed, int dGreen, int dBlue, int dAlpha){
        vertBuffer.clear();
        vertBuffer.put((float) ax).put((float) ay).put(u0).put(v0).put(aRed / 255f).put(aGreen / 255f).put(aBlue / 255f).put(aAlpha / 255f);
        vertBuffer.put((float) bx).put((float) ay).put(u1).put(v0).put(bRed / 255f).put(bGreen / 255f).put(bBlue / 255f).put(bAlpha / 255f);
        vertBuffer.put((float) bx).put((float) by).put(u1).put(v1).put(cRed / 255f).put(cGreen / 255f).put(cBlue / 255f).put(cAlpha / 255f);
        vertBuffer.put((float) ax).put((float) by).put(u0).put(v1).put(dRed / 255f).put(dGreen / 255f).put(dBlue / 255f).put(dAlpha / 255f);
        vertBuffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertBuffer);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(samplerLocation, 0);

        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private static int loadProgram(String vertSource, String fragSource){
        int vertShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertShader, vertSource);
        glCompileShader(vertShader);
        if (glGetShaderi(vertShader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RenderError(glGetShaderInfoLog(vertShader));
        }
        int fragShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragShader, fragSource);
        glCompileShader(fragShader);
        if (glGetShaderi(fragShader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RenderError(glGetShaderInfoLog(fragShader));
        }
        int program = glCreateProgram();
        glAttachShader(program, vertShader);
        glAttachShader(program, fragShader);
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RenderError(glGetProgramInfoLog(program));
        }
        glDeleteShader(vertShader);
        glDeleteShader(fragShader);
        return program;
    }

    public void drawTriangle(int ax, int ay, int bx, int by, int cx, int cy,
                             int aRed, int aGreen, int aBlue, int aAlpha,
                             int bRed, int bGreen, int bBlue, int bAlpha,
                             int cRed, int cGreen, int cBlue, int cAlpha
    ){
        detectCpuBuffer(3);
        vertBuffer.put(ax).put(ay).put(aRed / 255f).put(aGreen / 255f).put(aBlue / 255f).put(aAlpha / 255f);
        vertBuffer.put(bx).put(by).put(bRed / 255f).put(bGreen / 255f).put(bBlue / 255f).put(bAlpha / 255f);
        vertBuffer.put(cx).put(cy).put(cRed / 255f).put(cGreen / 255f).put(cBlue / 255f).put(cAlpha / 255f);
        vertCount += 3;
    }

    private void detectCpuBuffer(int trianglesToAdd) {
        int neededFloats = trianglesToAdd * 18;
        if (vertBuffer.remaining() < neededFloats) {
            int oldCapacity = vertBuffer.capacity();
            int requiredCapacity = vertBuffer.position() + neededFloats;
            int newCapacity = Math.max(oldCapacity * 2, requiredCapacity);
            FloatBuffer newBuffer = BufferUtils.createFloatBuffer(newCapacity);
            vertBuffer.flip();
            newBuffer.put(vertBuffer);
            vertBuffer = newBuffer;
        }
    }
    private void detectGpuBuffer() {
        int neededBytes = vertCount * 6 * Float.BYTES;
        if (neededBytes > vboCapacityBytes) {
            int newCapacity = Math.max(vboCapacityBytes * 2, neededBytes);
            glBindBuffer(GL_ARRAY_BUFFER, baseVbo);
            glBufferData(GL_ARRAY_BUFFER, newCapacity, GL_STREAM_DRAW);
            vboCapacityBytes = newCapacity;
        }
    }

    @Override
    public void submitBuffer(){
        detectGpuBuffer();
        vertBuffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, baseVbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertBuffer);
        glBindVertexArray(baseVao);
        glUseProgram(baseShaderProgram);
        glDrawArrays(GL_TRIANGLES, 0, vertCount);
    }

    @Override
    public void begin(){
        glUseProgram(baseShaderProgram);

        int windowWidth = MainUi.getInstance().getWindowWidth();
        int windowHeight = MainUi.getInstance().getWindowHeight();
        glViewport(0, 0, windowWidth, windowHeight);

        projectionMatrix.setOrtho(0.0f, windowWidth, windowHeight, 0.0f, -1.0f, 1.0f);

        projectionMatrix.get(projectionMatrixArray);

        glUniformMatrix4fv(projectLocation, false, projectionMatrixArray);

        glUseProgram(textureShaderProgram);
        glUniformMatrix4fv(textureProjectionLocation, false, projectionMatrixArray);

        glUseProgram(fontShaderProgram);
        glUniformMatrix4fv(fontProjectionLocation, false, projectionMatrixArray);

        glUseProgram(baseShaderProgram);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);

        vertBuffer.clear();
        vertCount = 0;
    }
    @Override
    public void end(){
    }

    @Override
    public void saveContext() {
        intBuffer.clear(); glGetIntegerv(GL_CURRENT_PROGRAM, intBuffer); savedProgram = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_VERTEX_ARRAY_BINDING, intBuffer); savedVao = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_ELEMENT_ARRAY_BUFFER_BINDING, intBuffer); savedEbo = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_FRAMEBUFFER_BINDING, intBuffer); savedFbo = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_RENDERBUFFER_BINDING, intBuffer); savedRbo = intBuffer.get(0);

        intBuffer.clear(); glGetIntegerv(GL_ACTIVE_TEXTURE, intBuffer); savedActiveTexture = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, intBuffer); savedTextureUnitCount = intBuffer.get(0);
        savedTextureBinding.clear();
        for (int i = 0; i < savedTextureUnitCount; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            intBuffer.clear(); glGetIntegerv(GL_TEXTURE_BINDING_2D, intBuffer); savedTextureBinding.add(intBuffer.get(0));
        }

        savedEnabledMap.clear();
        savedEnabledMap.put(GL_BLEND, glIsEnabled(GL_BLEND));
        savedEnabledMap.put(GL_DEPTH_TEST, glIsEnabled(GL_DEPTH_TEST));
        savedEnabledMap.put(GL_CULL_FACE, glIsEnabled(GL_CULL_FACE));
        savedEnabledMap.put(GL_SCISSOR_TEST, glIsEnabled(GL_SCISSOR_TEST));
        savedEnabledMap.put(GL_STENCIL_TEST, glIsEnabled(GL_STENCIL_TEST));
        savedEnabledMap.put(GL_POLYGON_OFFSET_FILL, glIsEnabled(GL_POLYGON_OFFSET_FILL));
        savedEnabledMap.put(GL_POLYGON_OFFSET_LINE, glIsEnabled(GL_POLYGON_OFFSET_LINE));
        savedEnabledMap.put(GL_POLYGON_OFFSET_POINT, glIsEnabled(GL_POLYGON_OFFSET_POINT));
        savedEnabledMap.put(GL_SAMPLE_ALPHA_TO_COVERAGE, glIsEnabled(GL_SAMPLE_ALPHA_TO_COVERAGE));
        savedEnabledMap.put(GL_SAMPLE_COVERAGE, glIsEnabled(GL_SAMPLE_COVERAGE));
        savedEnabledMap.put(GL_COLOR_LOGIC_OP, glIsEnabled(GL_COLOR_LOGIC_OP));
        savedEnabledMap.put(GL_DITHER, glIsEnabled(GL_DITHER));
        savedEnabledMap.put(GL_MULTISAMPLE, glIsEnabled(GL_MULTISAMPLE));
        savedEnabledMap.put(GL_LINE_SMOOTH, glIsEnabled(GL_LINE_SMOOTH));
        savedEnabledMap.put(GL_POINT_SMOOTH, glIsEnabled(GL_POINT_SMOOTH));
        savedEnabledMap.put(GL_POLYGON_SMOOTH, glIsEnabled(GL_POLYGON_SMOOTH));
        savedEnabledMap.put(GL_FRAMEBUFFER_SRGB, glIsEnabled(GL_FRAMEBUFFER_SRGB));
        savedEnabledMap.put(GL_PROGRAM_POINT_SIZE, glIsEnabled(GL_PROGRAM_POINT_SIZE));

        intBuffer.clear(); glGetIntegerv(GL_BLEND_SRC_RGB, intBuffer); savedBlendSrcRGB = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_BLEND_SRC_ALPHA, intBuffer); savedBlendSrcAlpha = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_BLEND_DST_RGB, intBuffer); savedBlendDstRGB = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_BLEND_DST_ALPHA, intBuffer); savedBlendDstAlpha = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_BLEND_EQUATION_RGB, intBuffer); savedBlendEquationRGB = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_BLEND_EQUATION_ALPHA, intBuffer); savedBlendEquationAlpha = intBuffer.get(0);
        floatBuffer.clear(); glGetFloatv(GL_BLEND_COLOR, floatBuffer);
        savedBlendColor[0] = floatBuffer.get(0);
        savedBlendColor[1] = floatBuffer.get(1);
        savedBlendColor[2] = floatBuffer.get(2);
        savedBlendColor[3] = floatBuffer.get(3);

        intBuffer.clear(); glGetIntegerv(GL_DEPTH_FUNC, intBuffer); savedDepthFunc = intBuffer.get(0);
        byteBuffer.clear(); glGetBooleanv(GL_DEPTH_WRITEMASK, byteBuffer); savedDepthMask = byteBuffer.get(0) == GL_TRUE;
        floatBuffer.clear(); glGetFloatv(GL_DEPTH_CLEAR_VALUE, floatBuffer); savedDepthClearValue = floatBuffer.get(0);
        doubleBuffer.clear(); glGetDoublev(GL_DEPTH_RANGE, doubleBuffer);
        savedDepthRange[0] = doubleBuffer.get(0);
        savedDepthRange[1] = doubleBuffer.get(1);

        intBuffer.clear(); glGetIntegerv(GL_STENCIL_FUNC, intBuffer); savedStencilFuncFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_REF, intBuffer); savedStencilRefFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_VALUE_MASK, intBuffer); savedStencilValueMaskFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_WRITEMASK, intBuffer); savedStencilWriteMaskFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_FAIL, intBuffer); savedStencilFailFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_PASS_DEPTH_FAIL, intBuffer); savedStencilPassDepthFailFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_PASS_DEPTH_PASS, intBuffer); savedStencilPassDepthPassFront = intBuffer.get(0);

        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_FUNC, intBuffer); savedStencilFuncBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_REF, intBuffer); savedStencilRefBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_VALUE_MASK, intBuffer); savedStencilValueMaskBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_WRITEMASK, intBuffer); savedStencilWriteMaskBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_FAIL, intBuffer); savedStencilFailBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_PASS_DEPTH_FAIL, intBuffer); savedStencilPassDepthFailBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_PASS_DEPTH_PASS, intBuffer); savedStencilPassDepthPassBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_CLEAR_VALUE, intBuffer); savedStencilClearValue = intBuffer.get(0);

        intBuffer.clear(); glGetIntegerv(GL_CULL_FACE_MODE, intBuffer); savedCullFaceMode = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_FRONT_FACE, intBuffer); savedFrontFace = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_POLYGON_MODE, intBuffer); savedPolygonMode = intBuffer.get(0);
        floatBuffer.clear(); glGetFloatv(GL_POLYGON_OFFSET_FACTOR, floatBuffer); savedPolygonOffsetFactor = floatBuffer.get(0);
        floatBuffer.clear(); glGetFloatv(GL_POLYGON_OFFSET_UNITS, floatBuffer); savedPolygonOffsetUnits = floatBuffer.get(0);

        int4Buffer.clear(); glGetIntegerv(GL_VIEWPORT, int4Buffer);
        savedViewport[0] = int4Buffer.get(0);
        savedViewport[1] = int4Buffer.get(1);
        savedViewport[2] = int4Buffer.get(2);
        savedViewport[3] = int4Buffer.get(3);

        int4Buffer.clear(); glGetIntegerv(GL_SCISSOR_BOX, int4Buffer);
        savedScissorBox[0] = int4Buffer.get(0);
        savedScissorBox[1] = int4Buffer.get(1);
        savedScissorBox[2] = int4Buffer.get(2);
        savedScissorBox[3] = int4Buffer.get(3);

        floatBuffer.clear(); glGetFloatv(GL_COLOR_CLEAR_VALUE, floatBuffer);
        savedColorClearValue[0] = floatBuffer.get(0);
        savedColorClearValue[1] = floatBuffer.get(1);
        savedColorClearValue[2] = floatBuffer.get(2);
        savedColorClearValue[3] = floatBuffer.get(3);

        intBuffer.clear(); glGetIntegerv(GL_UNPACK_ALIGNMENT, intBuffer); savedUnpackAlignment = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_UNPACK_ROW_LENGTH, intBuffer); savedUnpackRowLength = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_UNPACK_SKIP_PIXELS, intBuffer); savedUnpackSkipPixels = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_UNPACK_SKIP_ROWS, intBuffer); savedUnpackSkipRows = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_PACK_ALIGNMENT, intBuffer); savedPackAlignment = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_PACK_ROW_LENGTH, intBuffer); savedPackRowLength = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_PACK_SKIP_PIXELS, intBuffer); savedPackSkipPixels = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_PACK_SKIP_ROWS, intBuffer); savedPackSkipRows = intBuffer.get(0);

        floatBuffer.clear(); glGetFloatv(GL_LINE_WIDTH, floatBuffer); savedLineWidth = floatBuffer.get(0);
        floatBuffer.clear(); glGetFloatv(GL_POINT_SIZE, floatBuffer); savedPointSize = floatBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_LOGIC_OP_MODE, intBuffer); savedLogicOpMode = intBuffer.get(0);
        floatBuffer.clear(); glGetFloatv(GL_SAMPLE_COVERAGE_VALUE, floatBuffer); savedSampleCoverageValue = floatBuffer.get(0);
        byteBuffer.clear(); glGetBooleanv(GL_SAMPLE_COVERAGE_INVERT, byteBuffer); savedSampleCoverageInvert = byteBuffer.get(0) == GL_TRUE;
    }

    @Override
    public void restoreContext() {
        glViewport(savedViewport[0], savedViewport[1], savedViewport[2], savedViewport[3]);
        glScissor(savedScissorBox[0], savedScissorBox[1], savedScissorBox[2], savedScissorBox[3]);
        glClearColor(savedColorClearValue[0], savedColorClearValue[1], savedColorClearValue[2], savedColorClearValue[3]);
        glClearDepth(savedDepthClearValue);
        glClearStencil(savedStencilClearValue);
        glDepthRange(savedDepthRange[0], savedDepthRange[1]);

        glBlendFuncSeparate(savedBlendSrcRGB, savedBlendDstRGB, savedBlendSrcAlpha, savedBlendDstAlpha);
        glBlendEquationSeparate(savedBlendEquationRGB, savedBlendEquationAlpha);
        glBlendColor(savedBlendColor[0], savedBlendColor[1], savedBlendColor[2], savedBlendColor[3]);

        glDepthFunc(savedDepthFunc);
        glDepthMask(savedDepthMask);

        glStencilFuncSeparate(GL_FRONT, savedStencilFuncFront, savedStencilRefFront, savedStencilValueMaskFront);
        glStencilOpSeparate(GL_FRONT, savedStencilFailFront, savedStencilPassDepthFailFront, savedStencilPassDepthPassFront);
        glStencilMaskSeparate(GL_FRONT, savedStencilWriteMaskFront);
        glStencilFuncSeparate(GL_BACK, savedStencilFuncBack, savedStencilRefBack, savedStencilValueMaskBack);
        glStencilOpSeparate(GL_BACK, savedStencilFailBack, savedStencilPassDepthFailBack, savedStencilPassDepthPassBack);
        glStencilMaskSeparate(GL_BACK, savedStencilWriteMaskBack);

        glCullFace(savedCullFaceMode);
        glFrontFace(savedFrontFace);
        glPolygonMode(GL_FRONT_AND_BACK, savedPolygonMode);
        glPolygonOffset(savedPolygonOffsetFactor, savedPolygonOffsetUnits);

        glPixelStorei(GL_UNPACK_ALIGNMENT, savedUnpackAlignment);
        glPixelStorei(GL_UNPACK_ROW_LENGTH, savedUnpackRowLength);
        glPixelStorei(GL_UNPACK_SKIP_PIXELS, savedUnpackSkipPixels);
        glPixelStorei(GL_UNPACK_SKIP_ROWS, savedUnpackSkipRows);
        glPixelStorei(GL_PACK_ALIGNMENT, savedPackAlignment);
        glPixelStorei(GL_PACK_ROW_LENGTH, savedPackRowLength);
        glPixelStorei(GL_PACK_SKIP_PIXELS, savedPackSkipPixels);
        glPixelStorei(GL_PACK_SKIP_ROWS, savedPackSkipRows);

        glLineWidth(savedLineWidth);
        glPointSize(savedPointSize);
        glLogicOp(savedLogicOpMode);
        glSampleCoverage(savedSampleCoverageValue, savedSampleCoverageInvert);

        for (Map.Entry<Integer, Boolean> entry : savedEnabledMap.entrySet()) {
            if (entry.getValue()) {
                glEnable(entry.getKey());
            } else {
                glDisable(entry.getKey());
            }
        }

        for (int i = 0; i < savedTextureUnitCount; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, savedTextureBinding.get(i));
        }
        glActiveTexture(savedActiveTexture);

        glBindRenderbuffer(GL_RENDERBUFFER, savedRbo);
        glBindFramebuffer(GL_FRAMEBUFFER, savedFbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, savedEbo);
        glBindVertexArray(savedVao);
        glUseProgram(savedProgram);
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    private void flushFontBatch() {
        if (fontBatchVertCount == 0) return;

        int neededFloats = fontBatchVertCount * 4;
        if (neededFloats > fontBatchVboCapacityFloats) {
            glBindBuffer(GL_ARRAY_BUFFER, fontVbo);
            glBufferData(GL_ARRAY_BUFFER, (long) neededFloats * Float.BYTES, GL_STREAM_DRAW);
            fontBatchVboCapacityFloats = neededFloats;
        }

        fontBatchVertBuffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, fontVbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, fontBatchVertBuffer);

        if (fontRenderColor != null) {
            glUseProgram(fontShaderProgram);
            glUniform4f(fontColorLocation,
                    fontRenderColor.red() / 255f,
                    fontRenderColor.green() / 255f,
                    fontRenderColor.blue() / 255f,
                    fontRenderColor.alpha() / 255f);
        }

        glBindVertexArray(fontVao);
        glDrawArrays(GL_TRIANGLES, 0, fontBatchVertCount);

        glBindVertexArray(0);

        fontBatchVertBuffer.clear();
        fontBatchVertCount = 0;
    }

    private void expandAtlas() {
        glDeleteTextures(fontAtlasTextureId);

        fontAtlasTextureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, fontAtlasTextureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        atlasWidth = atlasWidth * 2;
        atlasHeight = atlasHeight * 2;

        glTexImage2D(GL_TEXTURE_2D, 0, GL_R8, atlasWidth, atlasHeight, 0, GL_RED, GL_UNSIGNED_BYTE, (ByteBuffer) null);

        atlasCursorX = 0;
        atlasCursorY = 0;
        atlasRowHeight = 0;
    }

    @Override
    public void enableScissor(ScaleOffset position, ScaleOffset size) {
        glEnable(GL_SCISSOR_TEST);
        glScissor(position.getXPixel(), RenderBackend.getProvider().getWindowHeight() - position.getYPixel() - size.getYPixel(), size.getXPixel(), size.getYPixel());
    }
    @Override
    public void disableScissor() {
        glDisable(GL_SCISSOR_TEST);
    }
}