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

import static org.lwjgl.opengl.GL32C.*;
import static org.lwjgl.opengl.ARBImaging.GL_BLEND_COLOR;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.freetype.FT_Bitmap;
import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.error.RenderError;
import top.untoldstudio.rimeui.core.error.ResourceError;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.resource.ResourceReader;
import top.untoldstudio.rimeui.core.ui.MainUi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

public final class OpenGLGuiRender extends GuiRender {
    private record SavedGLState(
            int program, int vao, int ebo, int fbo, int rbo, int activeTexture,
            List<Integer> textureBinding, Map<Integer, Boolean> enabledMap,
            int blendSrcRGB, int blendSrcAlpha, int blendDstRGB, int blendDstAlpha,
            int blendEquationRGB, int blendEquationAlpha, float[] blendColor,
            int depthFunc, boolean depthMask, float depthClearValue, double[] depthRange,
            int stencilFuncFront, int stencilRefFront, int stencilValueMaskFront,
            int stencilWriteMaskFront, int stencilFailFront, int stencilPassDepthFailFront,
            int stencilPassDepthPassFront,
            int stencilFuncBack, int stencilRefBack, int stencilValueMaskBack,
            int stencilWriteMaskBack, int stencilFailBack, int stencilPassDepthFailBack,
            int stencilPassDepthPassBack, int stencilClearValue,
            int cullFaceMode, int frontFace, int polygonMode,
            float polygonOffsetFactor, float polygonOffsetUnits,
            int[] viewport, int[] scissorBox, float[] colorClearValue,
            int unpackAlignment, int unpackRowLength, int unpackSkipPixels, int unpackSkipRows,
            int packAlignment, int packRowLength, int packSkipPixels, int packSkipRows,
            float lineWidth, float pointSize, int logicOpMode,
            float sampleCoverageValue, boolean sampleCoverageInvert
    ){}

    private interface DrawCommand {
        void execute();
    }

    private final Deque<SavedGLState> stateStack = new ArrayDeque<>();
    private final List<DrawCommand> commandList = new ArrayList<>();

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
    private final FloatBuffer fontVertBuffer = BufferUtils.createFloatBuffer(24);
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

    public OpenGLGuiRender(long windowHandle) {
        super(windowHandle);

        String baseVertSource;
        try {
            baseVertSource = ResourceReader.readString("/shader/base/vert.glsl");
        } catch (IOException e) {
            throw new ResourceError("Cannot read base glsl vertex shader!");
        }
        String baseFragSource;
        try {
            baseFragSource = ResourceReader.readString("/shader/base/frag.glsl");
        } catch (IOException e) {
            throw new ResourceError("Cannot read base glsl frag shader!");
        }
        int[] baseAttribLocs = {0, 1};
        String[] baseAttribNames = {"aPos", "aColor"};
        baseShaderProgram = loadProgram(baseVertSource, baseFragSource, baseAttribLocs, baseAttribNames);

        String textureVertSource;
        try {
            textureVertSource = ResourceReader.readString("/shader/texture/vert.glsl");
        } catch (IOException e) {
            throw new ResourceError("Cannot read texture glsl vertex shader!");
        }
        String textureFragSource;
        try {
            textureFragSource = ResourceReader.readString("/shader/texture/frag.glsl");
        } catch (IOException e) {
            throw new ResourceError("Cannot read texture glsl frag shader!");
        }
        int[] textureAttribLocs = {0, 1, 2};
        String[] textureAttribNames = {"aPos", "aTexCoord", "aColor"};
        textureShaderProgram = loadProgram(textureVertSource, textureFragSource, textureAttribLocs, textureAttribNames);
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
        } catch (IOException e) {
            throw new ResourceError("Cannot read font glsl vertex shader!");
        }
        String fontFragSource;
        try {
            fontFragSource = ResourceReader.readString("/shader/font/frag.glsl");
        } catch (IOException e) {
            throw new ResourceError("Cannot read font glsl frag shader!");
        }
        int[] fontAttribLocs = {0, 1};
        String[] fontAttribNames = {"aPos", "aTexCoord"};
        fontShaderProgram = loadProgram(fontVertSource, fontFragSource, fontAttribLocs, fontAttribNames);
        fontColorLocation = glGetUniformLocation(fontShaderProgram, "uColor");
        fontProjectionLocation = glGetUniformLocation(fontShaderProgram, "uProjection");
        fontSamplerLocation = glGetUniformLocation(fontShaderProgram, "uTexture");

        fontVao = glGenVertexArrays();
        fontVbo = glGenBuffers();
        glBindVertexArray(fontVao);
        int fontStride = 4 * Float.BYTES;
        glBindBuffer(GL_ARRAY_BUFFER, fontVbo);
        int initialFontBatchFloats = 4096 * 4;
        glBufferData(GL_ARRAY_BUFFER, initialFontBatchFloats * Float.BYTES, GL_STREAM_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, fontStride, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, fontStride, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        int[] indices = {0, 1, 2, 0, 2, 3};
        IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length);
        indexBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
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
        glBufferData(GL_ARRAY_BUFFER, 64 * Float.BYTES, GL_STREAM_DRAW);

        fontAtlasTextureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, fontAtlasTextureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_R8, atlasWidth, atlasHeight, 0, GL_RED, GL_UNSIGNED_BYTE, (ByteBuffer) null);
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
            expandAtlas();
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, fontAtlasTextureId);
        }

        ByteBuffer pixelBuffer = bitmap.buffer(pitch * height);
        if (pixelBuffer == null) return;

        glPixelStorei(GL_UNPACK_ROW_LENGTH, pitch);
        glTexSubImage2D(GL_TEXTURE_2D, 0, atlasCursorX, atlasCursorY, width, height, GL_RED, GL_UNSIGNED_BYTE, pixelBuffer);
        glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);

        float u0 = (float) atlasCursorX / atlasWidth;
        float u1 = (float) (atlasCursorX + width) / atlasWidth;
        float vTop = 1.0f - (float) atlasCursorY / atlasHeight;
        float vBottom = 1.0f - (float) (atlasCursorY + height) / atlasHeight;

        atlasCursorX += width;
        if (height > atlasRowHeight) {
            atlasRowHeight = height;
        }

        float x1 = glyphX + width;
        float y1 = glyphY + height;

        float[] verts = new float[24];
        int idx = 0;
        verts[idx++] = glyphX;
        verts[idx++] = glyphY;
        verts[idx++] = u0;
        verts[idx++] = vTop;
        verts[idx++] = x1;
        verts[idx++] = glyphY;
        verts[idx++] = u1;
        verts[idx++] = vTop;
        verts[idx++] = x1;
        verts[idx++] = y1;
        verts[idx++] = u1;
        verts[idx++] = vBottom;
        verts[idx++] = glyphX;
        verts[idx++] = glyphY;
        verts[idx++] = u0;
        verts[idx++] = vTop;
        verts[idx++] = x1;
        verts[idx++] = y1;
        verts[idx++] = u1;
        verts[idx++] = vBottom;
        verts[idx++] = glyphX;
        verts[idx++] = y1;
        verts[idx++] = u0;
        verts[idx++] = vBottom;

        final float[] finalVerts = verts;
        final RGBA finalColor = color;
        commandList.add(() -> {
            glEnable(GL_BLEND);
            glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
            glUseProgram(fontShaderProgram);
            glUniform4f(fontColorLocation, finalColor.red() / 255f, finalColor.green() / 255f, finalColor.blue() / 255f, finalColor.alpha() / 255f);
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, fontAtlasTextureId);
            glUniform1i(fontSamplerLocation, 0);
            glBindVertexArray(fontVao);
            glBindBuffer(GL_ARRAY_BUFFER, fontVbo);
            fontVertBuffer.clear();
            fontVertBuffer.put(finalVerts);
            fontVertBuffer.flip();
            glBufferSubData(GL_ARRAY_BUFFER, 0, fontVertBuffer);
            glDrawArrays(GL_TRIANGLES, 0, 6);
            glBindVertexArray(0);
            glBindTexture(GL_TEXTURE_2D, 0);
        });
    }

    @Override
    protected void beginTextRendering() {
    }

    @Override
    protected void endTextRendering() {
    }

    @Override
    public void flushBaseBuffer(){
        submitBuffer();
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

        float[] verts = new float[32];
        int idx = 0;
        verts[idx++] = ax;
        verts[idx++] = ay;
        verts[idx++] = u0;
        verts[idx++] = v0;
        verts[idx++] = aRed / 255f;
        verts[idx++] = aGreen / 255f;
        verts[idx++] = aBlue / 255f;
        verts[idx++] = aAlpha / 255f;

        verts[idx++] = bx;
        verts[idx++] = ay;
        verts[idx++] = u1;
        verts[idx++] = v0;
        verts[idx++] = bRed / 255f;
        verts[idx++] = bGreen / 255f;
        verts[idx++] = bBlue / 255f;
        verts[idx++] = bAlpha / 255f;

        verts[idx++] = bx;
        verts[idx++] = by;
        verts[idx++] = u1;
        verts[idx++] = v1;
        verts[idx++] = cRed / 255f;
        verts[idx++] = cGreen / 255f;
        verts[idx++] = cBlue / 255f;
        verts[idx++] = cAlpha / 255f;

        verts[idx++] = ax;
        verts[idx++] = by;
        verts[idx++] = u0;
        verts[idx++] = v1;
        verts[idx++] = dRed / 255f;
        verts[idx++] = dGreen / 255f;
        verts[idx++] = dBlue / 255f;
        verts[idx++] = dAlpha / 255f;

        final int finalTextureId = textureId;
        final float[] finalVerts = verts;
        commandList.add(() -> {
            glUseProgram(textureShaderProgram);
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, finalTextureId);
            glUniform1i(textureSamplerLocation, 0);
            glBindVertexArray(textureVao);
            glBindBuffer(GL_ARRAY_BUFFER, textureVbo);
            textureVertBuffer.clear();
            textureVertBuffer.put(finalVerts);
            textureVertBuffer.flip();
            glBufferSubData(GL_ARRAY_BUFFER, 0, textureVertBuffer);
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
            glBindVertexArray(0);
            glBindTexture(GL_TEXTURE_2D, 0);
        });
    }

    private static int loadProgram(String vertSource, String fragSource, int[] attribLocations, String[] attribNames) {
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

        for (int i = 0; i < attribLocations.length; i++) {
            glBindAttribLocation(program, attribLocations[i], attribNames[i]);
        }

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
        float[] verts = new float[18];
        int idx = 0;
        verts[idx++] = ax;
        verts[idx++] = ay;
        verts[idx++] = aRed / 255f;
        verts[idx++] = aGreen / 255f;
        verts[idx++] = aBlue / 255f;
        verts[idx++] = aAlpha / 255f;

        verts[idx++] = bx;
        verts[idx++] = by;
        verts[idx++] = bRed / 255f;
        verts[idx++] = bGreen / 255f;
        verts[idx++] = bBlue / 255f;
        verts[idx++] = bAlpha / 255f;

        verts[idx++] = cx;
        verts[idx++] = cy;
        verts[idx++] = cRed / 255f;
        verts[idx++] = cGreen / 255f;
        verts[idx++] = cBlue / 255f;
        verts[idx++] = cAlpha / 255f;

        final float[] finalVerts = verts;
        commandList.add(() -> {
            glUseProgram(baseShaderProgram);
            glBindVertexArray(baseVao);
            glBindBuffer(GL_ARRAY_BUFFER, baseVbo);
            FloatBuffer temp = BufferUtils.createFloatBuffer(18);
            temp.put(finalVerts);
            temp.flip();
            glBufferSubData(GL_ARRAY_BUFFER, 0, temp);
            glDrawArrays(GL_TRIANGLES, 0, 3);
            glBindVertexArray(0);
        });
    }

    @Override
    public void submitBuffer(){
        for (DrawCommand command : commandList) {
            command.execute();
        }
        commandList.clear();
    }

    @Override
    public void begin(){
        atlasCursorX = 0;
        atlasCursorY = 0;
        atlasRowHeight = 0;

        glUseProgram(baseShaderProgram);

        int windowWidth = MainUi.getInstance().getWindowWidth();
        int windowHeight = MainUi.getInstance().getWindowHeight();
        if (isUseRenderMapping){
            glViewport(renderRegionMin.getXPixel(), renderRegionMin.getYPixel(), renderRegionSize.getXPixel(), renderRegionSize.getYPixel());
        } else {
            glViewport(0, 0, windowWidth, windowHeight);
        }

        projectionMatrix.setOrtho(0.0f, windowWidth, windowHeight, 0.0f, -1.0f, 1.0f);
        projectionMatrix.get(projectionMatrixArray);

        glUniformMatrix4fv(projectLocation, false, projectionMatrixArray);

        glUseProgram(textureShaderProgram);
        glUniformMatrix4fv(textureProjectionLocation, false, projectionMatrixArray);

        glUseProgram(fontShaderProgram);
        glUniformMatrix4fv(fontProjectionLocation, false, projectionMatrixArray);

        glUseProgram(baseShaderProgram);

        glColorMask(true, true, true, true);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);
        glDisable(GL_SCISSOR_TEST);
        glDisable(GL_STENCIL_TEST);
        glDisable(GL_COLOR_LOGIC_OP);

        commandList.clear();
    }
    @Override
    public void end(){
    }

    @Override
    public int loadImage(int width, int height, ByteBuffer stbData){
        GuiRender render = MainUi.getInstance().getRender();
        render.saveContext();

        int textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);
        glPixelStorei(GL_UNPACK_SKIP_PIXELS, 0);
        glPixelStorei(GL_UNPACK_SKIP_ROWS, 0);
        glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, stbData);
        glBindTexture(GL_TEXTURE_2D, 0);

        render.restoreContext();

        return textureId;
    }

    @Override
    public void saveContext() {
        int program, vao, ebo, fbo, rbo;
        int activeTexture;
        List<Integer> textureBinding = new ArrayList<>();
        Map<Integer, Boolean> enabledMap = new HashMap<>();
        int blendSrcRGB, blendSrcAlpha, blendDstRGB, blendDstAlpha;
        int blendEquationRGB, blendEquationAlpha;
        float[] blendColor = new float[4];
        int depthFunc;
        boolean depthMask;
        float depthClearValue;
        double[] depthRange = new double[2];
        int stencilFuncFront, stencilRefFront, stencilValueMaskFront;
        int stencilWriteMaskFront, stencilFailFront, stencilPassDepthFailFront, stencilPassDepthPassFront;
        int stencilFuncBack, stencilRefBack, stencilValueMaskBack;
        int stencilWriteMaskBack, stencilFailBack, stencilPassDepthFailBack, stencilPassDepthPassBack;
        int stencilClearValue;
        int cullFaceMode, frontFace, polygonMode;
        float polygonOffsetFactor, polygonOffsetUnits;
        int[] viewport = new int[4];
        int[] scissorBox = new int[4];
        float[] colorClearValue = new float[4];
        int unpackAlignment, unpackRowLength, unpackSkipPixels, unpackSkipRows;
        int packAlignment, packRowLength, packSkipPixels, packSkipRows;
        float lineWidth, pointSize;
        int logicOpMode;
        float sampleCoverageValue;
        boolean sampleCoverageInvert;

        intBuffer.clear(); glGetIntegerv(GL_CURRENT_PROGRAM, intBuffer); program = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_VERTEX_ARRAY_BINDING, intBuffer); vao = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_ELEMENT_ARRAY_BUFFER_BINDING, intBuffer); ebo = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_FRAMEBUFFER_BINDING, intBuffer); fbo = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_RENDERBUFFER_BINDING, intBuffer); rbo = intBuffer.get(0);

        intBuffer.clear(); glGetIntegerv(GL_ACTIVE_TEXTURE, intBuffer); activeTexture = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, intBuffer); int textureUnitCount = intBuffer.get(0);
        for (int i = 0; i < textureUnitCount; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            intBuffer.clear(); glGetIntegerv(GL_TEXTURE_BINDING_2D, intBuffer); textureBinding.add(intBuffer.get(0));
        }

        enabledMap.put(GL_BLEND, glIsEnabled(GL_BLEND));
        enabledMap.put(GL_DEPTH_TEST, glIsEnabled(GL_DEPTH_TEST));
        enabledMap.put(GL_CULL_FACE, glIsEnabled(GL_CULL_FACE));
        enabledMap.put(GL_SCISSOR_TEST, glIsEnabled(GL_SCISSOR_TEST));
        enabledMap.put(GL_STENCIL_TEST, glIsEnabled(GL_STENCIL_TEST));
        enabledMap.put(GL_POLYGON_OFFSET_FILL, glIsEnabled(GL_POLYGON_OFFSET_FILL));
        enabledMap.put(GL_POLYGON_OFFSET_LINE, glIsEnabled(GL_POLYGON_OFFSET_LINE));
        enabledMap.put(GL_POLYGON_OFFSET_POINT, glIsEnabled(GL_POLYGON_OFFSET_POINT));
        enabledMap.put(GL_SAMPLE_ALPHA_TO_COVERAGE, glIsEnabled(GL_SAMPLE_ALPHA_TO_COVERAGE));
        enabledMap.put(GL_SAMPLE_COVERAGE, glIsEnabled(GL_SAMPLE_COVERAGE));
        enabledMap.put(GL_COLOR_LOGIC_OP, glIsEnabled(GL_COLOR_LOGIC_OP));
        enabledMap.put(GL_DITHER, glIsEnabled(GL_DITHER));
        enabledMap.put(GL_MULTISAMPLE, glIsEnabled(GL_MULTISAMPLE));
        enabledMap.put(GL_LINE_SMOOTH, glIsEnabled(GL_LINE_SMOOTH));
        enabledMap.put(GL_POLYGON_SMOOTH, glIsEnabled(GL_POLYGON_SMOOTH));
        enabledMap.put(GL_FRAMEBUFFER_SRGB, glIsEnabled(GL_FRAMEBUFFER_SRGB));
        enabledMap.put(GL_PROGRAM_POINT_SIZE, glIsEnabled(GL_PROGRAM_POINT_SIZE));

        intBuffer.clear(); glGetIntegerv(GL_BLEND_SRC_RGB, intBuffer); blendSrcRGB = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_BLEND_SRC_ALPHA, intBuffer); blendSrcAlpha = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_BLEND_DST_RGB, intBuffer); blendDstRGB = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_BLEND_DST_ALPHA, intBuffer); blendDstAlpha = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_BLEND_EQUATION_RGB, intBuffer); blendEquationRGB = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_BLEND_EQUATION_ALPHA, intBuffer); blendEquationAlpha = intBuffer.get(0);
        floatBuffer.clear(); glGetFloatv(GL_BLEND_COLOR, floatBuffer);
        blendColor[0] = floatBuffer.get(0);
        blendColor[1] = floatBuffer.get(1);
        blendColor[2] = floatBuffer.get(2);
        blendColor[3] = floatBuffer.get(3);

        intBuffer.clear(); glGetIntegerv(GL_DEPTH_FUNC, intBuffer); depthFunc = intBuffer.get(0);
        byteBuffer.clear(); glGetBooleanv(GL_DEPTH_WRITEMASK, byteBuffer); depthMask = byteBuffer.get(0) == GL_TRUE;
        floatBuffer.clear(); glGetFloatv(GL_DEPTH_CLEAR_VALUE, floatBuffer); depthClearValue = floatBuffer.get(0);
        doubleBuffer.clear(); glGetDoublev(GL_DEPTH_RANGE, doubleBuffer);
        depthRange[0] = doubleBuffer.get(0);
        depthRange[1] = doubleBuffer.get(1);

        intBuffer.clear(); glGetIntegerv(GL_STENCIL_FUNC, intBuffer); stencilFuncFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_REF, intBuffer); stencilRefFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_VALUE_MASK, intBuffer); stencilValueMaskFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_WRITEMASK, intBuffer); stencilWriteMaskFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_FAIL, intBuffer); stencilFailFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_PASS_DEPTH_FAIL, intBuffer); stencilPassDepthFailFront = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_PASS_DEPTH_PASS, intBuffer); stencilPassDepthPassFront = intBuffer.get(0);

        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_FUNC, intBuffer); stencilFuncBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_REF, intBuffer); stencilRefBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_VALUE_MASK, intBuffer); stencilValueMaskBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_WRITEMASK, intBuffer); stencilWriteMaskBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_FAIL, intBuffer); stencilFailBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_PASS_DEPTH_FAIL, intBuffer); stencilPassDepthFailBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_BACK_PASS_DEPTH_PASS, intBuffer); stencilPassDepthPassBack = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_STENCIL_CLEAR_VALUE, intBuffer); stencilClearValue = intBuffer.get(0);

        intBuffer.clear(); glGetIntegerv(GL_CULL_FACE_MODE, intBuffer); cullFaceMode = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_FRONT_FACE, intBuffer); frontFace = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_POLYGON_MODE, intBuffer); polygonMode = intBuffer.get(0);
        floatBuffer.clear(); glGetFloatv(GL_POLYGON_OFFSET_FACTOR, floatBuffer); polygonOffsetFactor = floatBuffer.get(0);
        floatBuffer.clear(); glGetFloatv(GL_POLYGON_OFFSET_UNITS, floatBuffer); polygonOffsetUnits = floatBuffer.get(0);

        int4Buffer.clear(); glGetIntegerv(GL_VIEWPORT, int4Buffer);
        viewport[0] = int4Buffer.get(0);
        viewport[1] = int4Buffer.get(1);
        viewport[2] = int4Buffer.get(2);
        viewport[3] = int4Buffer.get(3);

        int4Buffer.clear(); glGetIntegerv(GL_SCISSOR_BOX, int4Buffer);
        scissorBox[0] = int4Buffer.get(0);
        scissorBox[1] = int4Buffer.get(1);
        scissorBox[2] = int4Buffer.get(2);
        scissorBox[3] = int4Buffer.get(3);

        floatBuffer.clear(); glGetFloatv(GL_COLOR_CLEAR_VALUE, floatBuffer);
        colorClearValue[0] = floatBuffer.get(0);
        colorClearValue[1] = floatBuffer.get(1);
        colorClearValue[2] = floatBuffer.get(2);
        colorClearValue[3] = floatBuffer.get(3);

        intBuffer.clear(); glGetIntegerv(GL_UNPACK_ALIGNMENT, intBuffer); unpackAlignment = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_UNPACK_ROW_LENGTH, intBuffer); unpackRowLength = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_UNPACK_SKIP_PIXELS, intBuffer); unpackSkipPixels = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_UNPACK_SKIP_ROWS, intBuffer); unpackSkipRows = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_PACK_ALIGNMENT, intBuffer); packAlignment = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_PACK_ROW_LENGTH, intBuffer); packRowLength = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_PACK_SKIP_PIXELS, intBuffer); packSkipPixels = intBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_PACK_SKIP_ROWS, intBuffer); packSkipRows = intBuffer.get(0);

        floatBuffer.clear(); glGetFloatv(GL_LINE_WIDTH, floatBuffer); lineWidth = floatBuffer.get(0);
        floatBuffer.clear(); glGetFloatv(GL_POINT_SIZE, floatBuffer); pointSize = floatBuffer.get(0);
        intBuffer.clear(); glGetIntegerv(GL_LOGIC_OP_MODE, intBuffer); logicOpMode = intBuffer.get(0);
        floatBuffer.clear(); glGetFloatv(GL_SAMPLE_COVERAGE_VALUE, floatBuffer); sampleCoverageValue = floatBuffer.get(0);
        byteBuffer.clear(); glGetBooleanv(GL_SAMPLE_COVERAGE_INVERT, byteBuffer); sampleCoverageInvert = byteBuffer.get(0) == GL_TRUE;

        stateStack.push(new SavedGLState(
                program, vao, ebo, fbo, rbo, activeTexture,
                textureBinding, enabledMap,
                blendSrcRGB, blendSrcAlpha, blendDstRGB, blendDstAlpha,
                blendEquationRGB, blendEquationAlpha, blendColor,
                depthFunc, depthMask, depthClearValue, depthRange,
                stencilFuncFront, stencilRefFront, stencilValueMaskFront,
                stencilWriteMaskFront, stencilFailFront, stencilPassDepthFailFront,
                stencilPassDepthPassFront,
                stencilFuncBack, stencilRefBack, stencilValueMaskBack,
                stencilWriteMaskBack, stencilFailBack, stencilPassDepthFailBack,
                stencilPassDepthPassBack, stencilClearValue,
                cullFaceMode, frontFace, polygonMode,
                polygonOffsetFactor, polygonOffsetUnits,
                viewport, scissorBox, colorClearValue,
                unpackAlignment, unpackRowLength, unpackSkipPixels, unpackSkipRows,
                packAlignment, packRowLength, packSkipPixels, packSkipRows,
                lineWidth, pointSize, logicOpMode,
                sampleCoverageValue, sampleCoverageInvert
        ));
    }

    @Override
    public void restoreContext() {
        SavedGLState s = stateStack.pop();

        glViewport(s.viewport()[0], s.viewport()[1], s.viewport()[2], s.viewport()[3]);
        glScissor(s.scissorBox()[0], s.scissorBox()[1], s.scissorBox()[2], s.scissorBox()[3]);
        glClearColor(s.colorClearValue()[0], s.colorClearValue()[1], s.colorClearValue()[2], s.colorClearValue()[3]);
        glClearDepth(s.depthClearValue());
        glClearStencil(s.stencilClearValue());
        glDepthRange(s.depthRange()[0], s.depthRange()[1]);

        glBlendFuncSeparate(s.blendSrcRGB(), s.blendDstRGB(), s.blendSrcAlpha(), s.blendDstAlpha());
        glBlendEquationSeparate(s.blendEquationRGB(), s.blendEquationAlpha());
        glBlendColor(s.blendColor()[0], s.blendColor()[1], s.blendColor()[2], s.blendColor()[3]);

        glDepthFunc(s.depthFunc());
        glDepthMask(s.depthMask());

        glStencilFuncSeparate(GL_FRONT, s.stencilFuncFront(), s.stencilRefFront(), s.stencilValueMaskFront());
        glStencilOpSeparate(GL_FRONT, s.stencilFailFront(), s.stencilPassDepthFailFront(), s.stencilPassDepthPassFront());
        glStencilMaskSeparate(GL_FRONT, s.stencilWriteMaskFront());
        glStencilFuncSeparate(GL_BACK, s.stencilFuncBack(), s.stencilRefBack(), s.stencilValueMaskBack());
        glStencilOpSeparate(GL_BACK, s.stencilFailBack(), s.stencilPassDepthFailBack(), s.stencilPassDepthPassBack());
        glStencilMaskSeparate(GL_BACK, s.stencilWriteMaskBack());

        glCullFace(s.cullFaceMode());
        glFrontFace(s.frontFace());
        glPolygonMode(GL_FRONT_AND_BACK, s.polygonMode());
        glPolygonOffset(s.polygonOffsetFactor(), s.polygonOffsetUnits());

        glPixelStorei(GL_UNPACK_ALIGNMENT, s.unpackAlignment());
        glPixelStorei(GL_UNPACK_ROW_LENGTH, s.unpackRowLength());
        glPixelStorei(GL_UNPACK_SKIP_PIXELS, s.unpackSkipPixels());
        glPixelStorei(GL_UNPACK_SKIP_ROWS, s.unpackSkipRows());
        glPixelStorei(GL_PACK_ALIGNMENT, s.packAlignment());
        glPixelStorei(GL_PACK_ROW_LENGTH, s.packRowLength());
        glPixelStorei(GL_PACK_SKIP_PIXELS, s.packSkipPixels());
        glPixelStorei(GL_PACK_SKIP_ROWS, s.packSkipRows());

        glLineWidth(s.lineWidth());
        glPointSize(s.pointSize());
        glLogicOp(s.logicOpMode());
        glSampleCoverage(s.sampleCoverageValue(), s.sampleCoverageInvert());

        for (Map.Entry<Integer, Boolean> entry : s.enabledMap().entrySet()) {
            if (entry.getValue()) {
                glEnable(entry.getKey());
            } else {
                glDisable(entry.getKey());
            }
        }

        int textureUnitCount = s.textureBinding().size();
        for (int i = 0; i < textureUnitCount; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, s.textureBinding().get(i));
        }
        glActiveTexture(s.activeTexture());

        glBindRenderbuffer(GL_RENDERBUFFER, s.rbo());
        glBindFramebuffer(GL_FRAMEBUFFER, s.fbo());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, s.ebo());
        glBindVertexArray(s.vao());
        glUseProgram(s.program());
    }

    private void expandAtlas() {
        glDeleteTextures(fontAtlasTextureId);

        fontAtlasTextureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, fontAtlasTextureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        atlasWidth *= 2;
        atlasHeight *= 2;

        glTexImage2D(GL_TEXTURE_2D, 0, GL_R8, atlasWidth, atlasHeight, 0, GL_RED, GL_UNSIGNED_BYTE, (ByteBuffer) null);

        atlasCursorX = 0;
        atlasCursorY = 0;
        atlasRowHeight = 0;
    }

    @Override
    public void enableScissor(ScaleOffset position, ScaleOffset size) {
        glEnable(GL_SCISSOR_TEST);
        glScissor(position.getXPixel(), MainUi.getInstance().getWindowHeight() - position.getYPixel() - size.getYPixel(), size.getXPixel(), size.getYPixel());
    }
    @Override
    public void disableScissor() {
        glDisable(GL_SCISSOR_TEST);
    }
}