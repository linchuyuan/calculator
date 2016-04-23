package xfficient.chu.caculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.*;
import android.widget.*;
import android.widget.TextView;
import android.content.res.*;
import android.opengl.GLSurfaceView;
import java.lang.Math;
import xfficient.chu.caculator.math;

public class MainActivity extends AppCompatActivity {
    public String temp = "";
    public double first = 0;
    public double second = 0;
    public double result = 0;
    public String operand = "";
    public math expression ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        int config = getResources().getConfiguration().orientation;
        if (config == Configuration.ORIENTATION_LANDSCAPE) {
            startActivity( new Intent(MainActivity.this,graphic_activity.class));
            finish();
        }
        else{
            setContentView(R.layout.activity_main);
        }
        Button change_mode = (Button) findViewById(R.id.graphic);
        change_mode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final TextView display = (TextView) findViewById(R.id.display);
                temp = display.getText().toString();
                Intent change_mode = new Intent(MainActivity.this, graphic_activity.class);
                change_mode.putExtra("expression",temp);
                startActivity(change_mode);
                finish();
            }
        });
        init();

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            startActivity( new Intent(MainActivity.this,MainActivity.class));
            this.finish();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            startActivity( new Intent(MainActivity.this,MainActivity.class));
            this.finish();
        }

    }


    public void init(){
        final Button backspace = (Button) findViewById(R.id.back);
        final Button x_listen = (Button) findViewById(R.id.variable);
        final Button open = (Button) findViewById(R.id.open);
        final Button close = (Button) findViewById(R.id.close);
        final Button dot = (Button) findViewById(R.id.dot);
        final Button one = (Button) findViewById(R.id.one);
        final Button two = (Button) findViewById(R.id.two);
        final Button three = (Button) findViewById(R.id.three);
        final Button four = (Button) findViewById(R.id.four);
        final Button five = (Button) findViewById(R.id.five);
        final Button six = (Button) findViewById(R.id.six);
        final Button seven = (Button) findViewById(R.id.seven);
        final Button eight = (Button) findViewById(R.id.eight);
        final Button nine = (Button) findViewById(R.id.nine);
        final Button plus = (Button) findViewById(R.id.plus);
        final Button min = (Button) findViewById(R.id.min);
        final Button result_button = (Button) findViewById(R.id.result);
        final Button clear_button = (Button) findViewById(R.id.clear);
        final Button times = (Button) findViewById(R.id.multiply);
        final Button divide = (Button) findViewById(R.id.divide);
        final Button zero = (Button) findViewById(R.id.zero);
        final Button nagetive = (Button) findViewById(R.id.nagative);
        final TextView display = (TextView) findViewById(R.id.display);

        View.OnClickListener backspace_listen = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = display.getText().toString();
                if (temp.length()<=1 ){return;}
                temp = temp.substring(0,temp.length()-1);
                display.setText(temp);
                display.setTextSize(set_text_size());
            }
        };

        View.OnClickListener nagetive_listen = new View.OnClickListener() {
            public void onClick (View view) {
                temp = display.getText().toString();
                first = Double.parseDouble(temp)*-1;
                if (is_int(first)) {
                    display.setText(Integer.toString((int) first));
                }
                else {
                    display.setText(Double.toString(first));
                }
                display.setTextSize(set_text_size());

            }
        };
        View.OnClickListener clear_listen = new View.OnClickListener() {
            @Override
            public void onClick(View view){
                temp = "";
                first = 0;
                second = 0;
                operand = "";
                display.setText("0");
                display.setTextSize(set_text_size());

            }
        };
        View.OnClickListener result_listen = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double result ;
                expression = new math();
                temp = display.getText().toString();
                result = expression.expression_eval(temp,0.0);
                display.setText(Double.toString(result));
                display.setTextSize(set_text_size());
            }
        };

        View.OnClickListener operand_listen = new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Button click = (Button) view;
                String click_string = click.getText().toString();
                switch (click_string){
                    case "+":
                        operand = "plus";
                        break;
                    case "—":
                        operand = "min";
                        break;
                    case "*":
                        operand = "times";
                        break;
                    case "/":
                        operand = "divide";
                        break;
                }
                if ( first == 0 && temp != ""){
                    first = Double.parseDouble(temp);
                }
                else if (first != 0 && temp !="" && second == 0){
                    second = Double.parseDouble(temp);
                }
                else {
                    return;
                }
                temp = "";
                display.setTextSize(set_text_size());
            }
        };
        View.OnClickListener num_listen = new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Button click = (Button) view;
                String click_string = click.getText().toString();
                if (temp.length() > 30){
                    return;
                }
                switch (click_string){
                    case "1":
                        temp = temp + "1";
                        break;
                    case "2":
                        temp = temp + "2";
                        break;
                    case "3":
                        temp = temp + "3";
                        break;
                    case "4":
                        temp = temp + "4";
                        break;
                    case "5":
                        temp = temp + "5";
                        break;
                    case "6":
                        temp = temp + "6";
                        break;
                    case "7":
                        temp = temp + "7";
                        break;
                    case "8":
                        temp = temp + "8";
                        break;
                    case "9":
                        temp = temp + "9";
                        break;
                    case "0":
                        temp = temp + "0";
                        break;
                    case ".":
                        temp = temp + ".";
                        break;
                    case "+":
                        temp = temp + "+";
                        break;
                    case "—":
                        temp = temp + "—";
                        break;
                    case "*":
                        temp = temp + "*";
                        break;
                    case "/":
                        temp = temp + "/";
                        break;
                    case "(":
                        temp = temp + "(";
                        break;
                    case ")":
                        temp = temp + ")";
                        break;
                    case "x":
                        temp = temp + "x";
                        break;
                    case "-":
                        temp = temp + "-";
                        break;
                }
                display.setText(temp);
                display.setTextSize(set_text_size());
            }
        };
        backspace.setOnClickListener(backspace_listen);
        x_listen.setOnClickListener(num_listen);
        open.setOnClickListener(num_listen);
        close.setOnClickListener(num_listen);
        dot.setOnClickListener(num_listen);
        nagetive.setOnClickListener(num_listen);
        zero.setOnClickListener(num_listen);
        one.setOnClickListener(num_listen);
        two.setOnClickListener(num_listen);
        three.setOnClickListener(num_listen);
        four.setOnClickListener(num_listen);
        five.setOnClickListener(num_listen);
        six.setOnClickListener(num_listen);
        seven.setOnClickListener(num_listen);
        eight.setOnClickListener(num_listen);
        nine.setOnClickListener(num_listen);
        plus.setOnClickListener(num_listen);
        min.setOnClickListener(num_listen);
        times.setOnClickListener(num_listen);
        divide.setOnClickListener(num_listen);
        clear_button.setOnClickListener(clear_listen);
        result_button.setOnClickListener(result_listen);
    }

     public int set_text_size(){
         final TextView display = (TextView) findViewById(R.id.display);
         int length = display.getText().toString().length();
         return 130 - (length * 3);
    }
    public boolean is_int(double target){
        if (target % 1 == 0){
            return true;
        }
        else {
            return false;
        }
    }

}
