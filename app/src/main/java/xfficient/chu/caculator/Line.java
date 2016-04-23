package xfficient.chu.caculator;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Chu on 3/16/2016.
 */
public class Line {
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private FloatBuffer vertexBuffer;
    static final int COORDS_PER_VERTEX = 2;
    float lineCoords[] = {};
    float color[] = {1.0f,0.0f,0.0f,1.0f };
    private int mProgram;
    public Line() {
    }
    public void init() {
        ByteBuffer bb = ByteBuffer.allocateDirect(lineCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(lineCoords);
        vertexBuffer.position(0);
        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        vertexCount = lineCoords.length / COORDS_PER_VERTEX;
        vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    }
    private int mPositionHandle = lineCoords.length / COORDS_PER_VERTEX;
    private int mColorHandle = COORDS_PER_VERTEX * 4;;

    private int vertexCount ;
    private int vertexStride;

    public void draw() {
        init();
        GLES20.glLineWidth(8.0f);
        GLES20.glUseProgram(mProgram);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_LINES,0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
    public void set_vertex(float[] vertex){
        lineCoords = vertex.clone();
        init();
    }
}
