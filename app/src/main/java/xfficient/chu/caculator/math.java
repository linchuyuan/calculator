package xfficient.chu.caculator;

/**
 * Created by Chu on 3/14/2016.
 */
import java.lang.String;
import java.lang.Math;

public class math {
    public String expression = "";
    private Double temp = 0.0;
    private int domain_length;
    public math (){
    }
    public void expression (String arg) {expression = arg;}
    public double[] get_range (double[] domain) {
        domain_length = domain.length;
        double[] range = new double[domain_length];
        if (expression == "x"){
            range = domain.clone();
            return range;
        }
        for (int i = 0; i<domain.length; i++){
            range[i] = expression_eval(expression,domain[i]);
        }
        return range;
    }

    public double expression_eval(String e,double x){
        double result = 0.0;
        String e_temp = "";
        String first = "";
        String second = "";
        String operand ="";
        boolean is_set = false;
        boolean ready = false;

        for (int i=0; i<e.length();i++){
            if (e.charAt(i) == '('){
                for (int k= i+1; k<e.length();k++){
                    if (e.charAt(k) != ')') {
                        e_temp = e_temp + Character.toString(e.charAt(k));
                    }
                    else {
                        if (first.equals("")) {
                            result = expression_eval(e_temp,x);
                            first = Double.toString(result);
                        }
                        else{ result = expression_eval(e_temp,x);second = Double.toString(result);}
                        e_temp = "";
                        i = k;
                    }
                }
            }
            else if (!is_set && second.equals("") && !(e.charAt(i) == '+' || e.charAt(i) == '—' || e.charAt(i) == '*' || e.charAt(i) == '/')){
                first = first + Character.toString(e.charAt(i));
            }
            else if (!is_set && (e.charAt(i) == '+' || e.charAt(i) == '—' || e.charAt(i) == '*' || e.charAt(i) == '/')){
                is_set = true;
                operand = Character.toString(e.charAt(i));
            }
            else if (is_set && !(e.charAt(i) == '+' || e.charAt(i) == '—' || e.charAt(i) == '*' || e.charAt(i) == '/')){
                second = second + Character.toString(e.charAt(i));
            }
            else if (is_set && !ready && (e.charAt(i) == '+' || e.charAt(i) == '—' || e.charAt(i) == '*' || e.charAt(i) == '/')) {
                ready = true;
            }
            if (i==e.length()-1){ ready = true; }
            if (ready) {
                result = eval(first + operand + second, x);
                first = Double.toString(result);
                operand = Character.toString(e.charAt(i));
                second = "";
                ready = false;
            }
        }
        return result;
    }

    public double eval (String e, double x){
        String letter = "";
        String frist="";
        String second="";
        Double result = 0.0;
        String operand = "";
        Boolean is_set = false;
        e = e.replace("x",Double.toString(x));
        for (int i = 0; i<e.length();i++){
            letter = Character.toString(e.charAt(i));
            if (!letter.equals("+") && !letter.equals("—") && !letter.equals("*") && !letter.equals("/") && !is_set){
                frist = frist + letter;
            }
            else if (!letter.equals("+") && !letter.equals("—") && !letter.equals("*") && !letter.equals("/") && is_set){
                second = second + letter;
            }
            else {
                is_set = true;
                operand = letter;
            }
        }
        switch (operand) {
            case "+":
                result = Double.parseDouble(frist) + Double.parseDouble(second);
                break;
            case "—":
                result = Double.parseDouble(frist) - Double.parseDouble(second);
                break;
            case "*":
                result = Double.parseDouble(frist) * Double.parseDouble(second);
                break;
            case "/":
                result = Double.parseDouble(frist) / Double.parseDouble(second);
                break;
        }
        return result;
    }
    public boolean is_num(String arg){
        try{
            temp = Double.parseDouble(arg) / 1;
            return true;
        }catch(Exception exception){
            return false;
        }

    }
}

