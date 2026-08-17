package top.untoldstudio.rimeui.core.render.provider;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.ARBImaging.GL_BLEND_COLOR;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import top.untoldstudio.rimeui.core.error.RenderError;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.render.RenderBackend;
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
    private final int projectLocation;
    private final int vao;
    private final int vbo;
    private FloatBuffer vertexBuffer;
    private int vertexCount = 0;
    private int vboCapacityBytes;
    private final Matrix4f projectionMatrix = new Matrix4f();
    private final float[] projectionMatrixArray = new float[16];
    private final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
    private final IntBuffer int4Buffer = BufferUtils.createIntBuffer(4);
    private final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(4);
    private final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(1);
    private final DoubleBuffer doubleBuffer = BufferUtils.createDoubleBuffer(2);

    public OpenGLGuiRender(long windowHandle){
        this.windowHandle = windowHandle;

        String baseFragSource;
        try {
            baseFragSource = ResourceReader.readString("/shader/base/frag.glsl");
        } catch (IOException e){
            throw new RenderError("Cannot read base glsl frag shader!");
        }
        String baseVertexSource;
        try {
            baseVertexSource = ResourceReader.readString("/shader/base/vert.glsl");
        } catch (IOException e){
            throw new RenderError("Cannot read base glsl vert shader!");
        }
        int baseVertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(baseVertexShader, baseVertexSource);
        glCompileShader(baseVertexShader);
        if (glGetShaderi(baseVertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RenderError(glGetShaderInfoLog(baseVertexShader));
        }
        int baseFragShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(baseFragShader, baseFragSource);
        glCompileShader(baseFragShader);
        if (glGetShaderi(baseFragShader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RenderError(glGetShaderInfoLog(baseFragShader));
        }
        baseShaderProgram = glCreateProgram();
        glAttachShader(baseShaderProgram, baseVertexShader);
        glAttachShader(baseShaderProgram, baseFragShader);
        glLinkProgram(baseShaderProgram);
        if (glGetProgrami(baseShaderProgram, GL_LINK_STATUS) == GL_FALSE) {
            throw new RenderError(glGetProgramInfoLog(baseShaderProgram));
        }
        glDeleteShader(baseVertexShader);
        glDeleteShader(baseFragShader);
        projectLocation = glGetUniformLocation(baseShaderProgram, "uProjection");
        System.out.println("projectLocation = " + projectLocation);
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        int stride = 6 * Float.BYTES;
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, stride, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);
        int maxVertices = 65536 * 3;
        vertexBuffer = BufferUtils.createFloatBuffer(maxVertices * 6);
        vboCapacityBytes = maxVertices * 6 * Float.BYTES;
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vboCapacityBytes, GL_STATIC_DRAW);
    }

    public void drawTriangle(int ax, int ay, int bx, int by, int cx, int cy,
                             int aRed, int aGreen, int aBlue, int aAlpha,
                             int bRed, int bGreen, int bBlue, int bAlpha,
                             int cRed, int cGreen, int cBlue, int cAlpha
    ){
        detectCpuBuffer(3);
        vertexBuffer.put(ax).put(ay).put(aRed / 255f).put(aGreen / 255f).put(aBlue / 255f).put(aAlpha / 255f);
        vertexBuffer.put(bx).put(by).put(bRed / 255f).put(bGreen / 255f).put(bBlue / 255f).put(bAlpha / 255f);
        vertexBuffer.put(cx).put(cy).put(cRed / 255f).put(cGreen / 255f).put(cBlue / 255f).put(cAlpha / 255f);
        vertexCount += 3;
    }

    private void detectCpuBuffer(int trianglesToAdd) {
        int neededFloats = trianglesToAdd * 18;
        if (vertexBuffer.remaining() < neededFloats) {
            int oldCapacity = vertexBuffer.capacity();
            int requiredCapacity = vertexBuffer.position() + neededFloats;
            int newCapacity = Math.max(oldCapacity * 2, requiredCapacity);
            FloatBuffer newBuffer = BufferUtils.createFloatBuffer(newCapacity);
            vertexBuffer.flip();
            newBuffer.put(vertexBuffer);
            vertexBuffer = newBuffer;
        }
    }
    private void detectGpuBuffer() {
        int neededBytes = vertexCount * 6 * Float.BYTES;
        if (neededBytes > vboCapacityBytes) {
            int newCapacity = Math.max(vboCapacityBytes * 2, neededBytes);
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, newCapacity, GL_STREAM_DRAW);
            vboCapacityBytes = newCapacity;
        }
    }

    @Override
    public void submitBuffer(){
        detectGpuBuffer();
        vertexBuffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexBuffer);
        glBindVertexArray(vao);
        glUseProgram(baseShaderProgram);
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
    }

    @Override
    public void begin(){
        saveContext();

        glUseProgram(baseShaderProgram);
        int windowWidth = MainUi.getInstance().getWindowWidth();
        int windowHeight = MainUi.getInstance().getWindowHeight();
        glViewport(0, 0, windowWidth, windowHeight);

        projectionMatrix.setOrtho(0.0f, windowWidth, windowHeight, 0.0f, -1.0f, 1.0f);

        projectionMatrix.get(projectionMatrixArray);

        glUniformMatrix4fv(projectLocation, false, projectionMatrixArray);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);

        vertexBuffer.clear();
        vertexCount = 0;
    }
    @Override
    public void end(){
        restoreContext();
    }

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
}