package xfficient.chu.caculator;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import java.lang.Math;
import xfficient.chu.caculator.math;

public class graphic_activity extends AppCompatActivity {
    public GLSurfaceView mGLView;
    public MyRenderer my_render = new MyRenderer();
    String expression = "";
    final int resolution = 1000;
    double[] domain = new double[resolution];
    double[] range = new double[resolution];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_horizontal);
        expression = getIntent().getStringExtra("expression");
        mGLView = (GLSurfaceView) findViewById(R.id.graph);
        mGLView.setEGLContextClientVersion(2);
        mGLView.setRenderer(my_render);
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        init();
    }
    public void init(){
        final Button refresh = (Button) findViewById(R.id.refresh);
        final Button calculator = (Button) findViewById(R.id.calculator);
        final TextView display = (TextView)findViewById(R.id.expression);
        final double max_scale = 10.0;
        expression = clean_expression(expression);
        display.setText(expression);
        final math my_math = new math();
        my_math.expression(expression);
        domain[0] = -max_scale;
        for (int i= 1; i<domain.length; i++){
            domain[i] = domain[i-1] + 0.02 ;
        }
        if (expression != "x") {
            range = my_math.get_range(domain);
        }
        else { range = domain.clone();}
        calculator.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity( new Intent(graphic_activity.this,MainActivity.class));
                finish();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                float[] vertex = new float[(resolution) * 2 ];
                //vertex[0] = (float) (domain[0]/max_scale); vertex[1] = (float) (range[0]/max_scale);
                //vertex[vertex.length-1] = (float) (domain[resolution -1]/max_scale); vertex[vertex.length-2] = (float) (range[resolution -1]/max_scale);
                int count = 0;
                for (int i=0; i < vertex.length-1; i=i+2){
                    vertex[i]= (float) (domain[count]/ max_scale);
                    vertex[i+1]=(float) (range[count] / (max_scale));
                    //vertex[i+2]= (float) (domain[count]/ max_scale);
                    //vertex[i+3]=(float) (range[count] / (max_scale));
                    count++;
                }
                my_render.set_vertex(vertex);
                mGLView.requestRender();
            }
        });
    }
    class MyGlSurfaceView extends GLSurfaceView {
        public final MyRenderer mRender;
        public MyGlSurfaceView(Context context) {
            super(context);
            setEGLContextClientVersion(2);
            mRender = new MyRenderer();
            setRenderer(mRender);
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

    }
    public String clean_expression(String e){
        String temp = e;
        for (int i=1; i<e.length();i++){
            if(e.charAt(i) == 'x' && (is_num(Character.toString(e.charAt(i-1)))  || e.charAt(i-1)=='x')){
                temp = e.replace(Character.toString(e.charAt(i-1))+Character.toString(e.charAt(i)),Character.toString(e.charAt(i-1))+"*"+Character.toString(e.charAt(i)));
            }
            if (i != e.length()-1) {
                if (e.charAt(i) == 'x' && (e.charAt(i + 1) == 'x')) {
                    temp = temp.replace(Character.toString(e.charAt(i)) + Character.toString(e.charAt(i + 1)), Character.toString(e.charAt(i)) + "*" + Character.toString(e.charAt(i + 1)));
                }
            }
        }
        return temp;
    }
    public boolean is_num(String arg){
        try{
            double temp = Double.parseDouble(arg) / 1;
            return true;
        }catch(Exception exception){
            return false;
        }

    }
}
