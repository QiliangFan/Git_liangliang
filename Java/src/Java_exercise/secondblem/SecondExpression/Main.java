package Java_exercise.secondblem.SecondExpression;

import javax.print.DocFlavor;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main{
    static Vector<Var> variables=new Vector<>();
    static Pattern var_definition=Pattern.compile(" *(int|float|double|long|byte|short) (\\w)=?(-?\\d+.?\\d*)?");
    static Pattern var_definition_fromvar=Pattern.compile(" *(int|float|double|long|byte|short) (\\w)=([^ ]*)");
    static Pattern var_expression=Pattern.compile(" *([^ ]+)=\\? *.*");
    static Pattern var_assign=Pattern.compile(" *([\\w]+)=(-?[\\d.]+)");
    static Pattern var_to_var=Pattern.compile(" *([\\w])=(-)?([^ ]+)");
    static Matcher m_var_definition;
    static Matcher m_var_definition_fromvar;
    static Matcher m_var_expression;
    static Matcher m_var_assign;
    static Matcher m_var_to_var;
    static String expression;
    static String TYPE;

    static boolean isDefined(String name){
        if(name==null) return true;
        for(int i=0;i<variables.size();i++){
            if(name.equals(variables.elementAt(i).name)) return true;
        }
        return false;
    }
    static boolean isAssigned(String name){
        if(name==null) return true;
        for(int i=0;i<variables.size();i++){
            if(name.equals(variables.elementAt(i).name)){
                return variables.elementAt(i).assigned;
            }
        }
        return false;
    }
    static Var getVar(String name){
        Pattern p=Pattern.compile("[\\-\\d.]*");
        Matcher m=p.matcher(name);

        for(int i=0;i<variables.size();i++){
            if(name.equals(variables.elementAt(i).name)){
                return variables.elementAt(i);
            }
        }
        if(!m.matches()){
            System.out.println("wrong - variable undefined");
            System.exit(0);
        }
        Var v = new Var();
        int sign=0;
        for(int i=0;i<name.length();i++){
            if(name.charAt(i)=='.'){
                sign=1;
            }
        }
        v.assigned=true;
        if(sign==1){v.f=Float.parseFloat(name);v.type="float";}
        else {v.i=Integer.parseInt(name);v.type="int";}
        v.name=name;
        variables.add(v);
        return v;
    }
    static boolean isOperand(String s){
        if(s==null) return true;
        Pattern p=Pattern.compile("\\+|-|\\*|/|%|\\(|\\)");
        Matcher m=p.matcher(s);
        return m.matches();
    }

    static String compute_expression(Vector<String> sufixexpression){
        Stack<String> compute=new Stack<>();
        for(int i=0;i<sufixexpression.size();i++){
            compute.push(sufixexpression.elementAt(i));
            if(isOperand(compute.peek())){
                String operand=compute.pop();
                if(compute.size()<=1){
                    System.out.println("wrong - error expression");
                    System.exit(0);
                }
                String var2=compute.pop();
                String var1=compute.pop();
                Var variable1=getVar(var1);
                Var variable2=getVar(var2);
                if(!isDefined(var1)||!isDefined(var2)){
                    System.out.println("wrong - variable undefined");
                    System.exit(0);
                }
                if(!isAssigned(var1)||!isAssigned(var2)){
                    System.out.println("wrong - variable unassigned");
                    System.exit(0);
                }
                String result=new String();
                if(operand.equals("+")){
                    if(variable1.type.equals("int")&&variable2.type.equals("int")){
                        result=variable1.i+variable2.i+"";
                    }else if(variable1.type.equals("int")&&variable2.type.equals("float")){
                        result=variable1.i+variable2.f+"";
                    }else if(variable1.type.equals("float")&&variable2.type.equals("int")){
                        result=variable1.f+variable2.i+"";
                    }else if(variable1.type.equals("float")&&variable2.type.equals("float")){
                        result=variable1.f+variable2.f+"";
                    }
                }else if(operand.equals("-")){
                    if(variable1.type.equals("int")&&variable2.type.equals("int")){
                        result=variable1.i-variable2.i+"";
                    }else if(variable1.type.equals("int")&&variable2.type.equals("float")){
                        result=variable1.i-variable2.f+"";
                    }else if(variable1.type.equals("float")&&variable2.type.equals("int")){
                        result=variable1.f-variable2.i+"";
                    }else if(variable1.type.equals("float")&&variable2.type.equals("float")){
                        result=variable1.f-variable2.f+"";
                    }
                }else if(operand.equals("*")){
                    if(variable1.type.equals("int")&&variable2.type.equals("int")){
                        result=variable1.i*variable2.i+"";
                    }else if(variable1.type.equals("int")&&variable2.type.equals("float")){
                        result=variable1.i*variable2.f+"";
                    }else if(variable1.type.equals("float")&&variable2.type.equals("int")){
                        result=variable1.f*variable2.i+"";
                    }else if(variable1.type.equals("float")&&variable2.type.equals("float")){
                        result=variable1.f*variable2.f+"";
                    }
                }else if(operand.equals("/")){
                    if(variable1.type.equals("int")&&variable2.type.equals("int")){
                        result=variable1.i/variable2.i+"";
                    }else if(variable1.type.equals("int")&&variable2.type.equals("float")){
                        result=variable1.i/variable2.f+"";
                    }else if(variable1.type.equals("float")&&variable2.type.equals("int")){
                        result=variable1.f/variable2.i+"";
                    }else if(variable1.type.equals("float")&&variable2.type.equals("float")){
                        result=variable1.f/variable2.f+"";
                    }
                }else if(operand.equals("%")){
                    if(variable1.type.equals("int")&&variable2.type.equals("int")){
                        result=variable1.i%variable2.i+"";
                    }else if(variable1.type.equals("int")&&variable2.type.equals("float")){
                        result=variable1.i%variable2.f+"";
                    }else if(variable1.type.equals("float")&&variable2.type.equals("int")){
                        result=variable1.f%variable2.i+"";
                    }else if(variable1.type.equals("float")&&variable2.type.equals("float")){
                        result=variable1.f%variable2.f+"";
                    }
                }
                compute.push(result);
            }
        }
        if(compute.size()>1){
            System.out.println("wrong - error expression");
            System.exit(0);

        }else{
            String s=compute.peek();
            if(isDefined(s)) {
                Var v = getVar(s);
                if (v.type.equals("int")) {
                    s = String.valueOf(v.i);
                    TYPE="int";
                } else {
                    s = String.valueOf(v.f);
                    TYPE="float";
                }
            }else{
                int start = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '.') {
                        start = 1;
                    }
                }
                if (start == 1) {
                    TYPE="float";
                }else{
                    TYPE="int";
                }
            }
            return s;
        }
        return null;
    }
    static String makeSufixExpression(){
        Vector<String> exp=new Vector<>();
        Stack<String> stack=new Stack<>();
        Vector<String> sufixexpression=new Vector<>();
        Pattern p1=Pattern.compile("(\\+|-|\\*|/|%|\\(|\\))|([a-zA-Z_$])|(?:-?[0-9]+.?[0-9]*)");
        Matcher m=p1.matcher(expression);
        while(m.find()){
            if(m.group()!=null){
                exp.add(m.group());
            }
//            if(m.group(1)!=null){
//                exp.add(m.group(1));
//            }
//            if(m.group(2)!=null){
//                exp.add(m.group(2));
//            }
//            if(m.group(3)!=null){
//                exp.add(m.group(3));
//            }
        }
        for(int i=0;i<exp.size();i++){
            String s=exp.elementAt(i);
            if(!isOperand(s)){
                sufixexpression.add(s);
            }else{
                if(stack.empty()) stack.add(s);
                else{
                    if(s.equals("+")||s.equals("-")){
                        while(!stack.empty()&&!stack.peek().equals("(")){
                            sufixexpression.add(stack.pop());
                        }
                        stack.push(s);
                    }else if(s.equals("*")||s.equals("/")||s.equals("%")){
                        String top=stack.peek();
                        if(top.equals("+")||top.equals("-")){
                            stack.push(s);
                        }else{
                            while(!stack.empty()&&!stack.peek().equals("(")&&
                                    !stack.peek().equals("-")&&!stack.peek().equals("+")){
                                sufixexpression.add(stack.pop());
                            }
                            stack.push(s);
                        }
                    }else if(s.equals("(")){
                        stack.push(s);
                    }else if(s.equals(")")){
                        while (!stack.peek().equals("(")){
                            sufixexpression.add(stack.pop());
                            if(stack.empty()){
                                System.out.println("wrong - error expression");
                                System.exit(0);
                            }
                        }
                        stack.pop();
                    }

                }
            }
        }
        while(!stack.empty()){
            if(stack.peek().equals("(")){
                System.out.println("wrong - error expression");
                System.exit(0);
            }
            sufixexpression.add(stack.pop());
        }
        return compute_expression(sufixexpression);
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        while(sc.hasNext()){
            sc.useDelimiter("\\n|;");
            String line=sc.next();
            if(line.isEmpty()) continue;
            m_var_expression=var_expression.matcher(line);
            m_var_definition=var_definition.matcher(line);
            m_var_definition_fromvar=var_definition_fromvar.matcher(line);
            m_var_assign=var_assign.matcher(line);
            m_var_to_var=var_to_var.matcher(line);
            if(m_var_expression.matches()){
                expression=m_var_expression.group(1);
                break;
            }else if(m_var_definition.matches()){
                String type=m_var_definition.group(1);
                if(type.equals("int")||type.equals("long")||type.equals("byte")||type.equals("short")){
                    type="int";
                }else{
                    type="float";
                }
                String name=m_var_definition.group(2);
                String value=m_var_definition.group(3);
                if(isDefined(name)){
                    Var v=getVar(name);
                    if(value!=null){
                        v.assigned=true;
                        v.type=type;
                        if(type.equals("int")){
                            v.i=(int)Float.parseFloat(value);

                        }else{
                            v.f=Float.parseFloat(value);
                        }
                    }
                }else {
                    Var v = new Var();
                    v.type = type;
                    v.name = name;
                    if (value != null) v.assigned = true;
                    if (v.isInt() && v.assigned) {
                        v.i = (int)Float.parseFloat(value);
                    } else if (v.assigned) {
                        v.f = Float.valueOf(value);
                    }
                    variables.add(v);
                }
            }else if(m_var_definition_fromvar.matches()){
                String type=m_var_definition_fromvar.group(1);
                String name=m_var_definition_fromvar.group(2);
                String expre=m_var_definition_fromvar.group(3);
                if(type.equals("int")||type.equals("long")){
                    type="int";
                }else{
                    type="float";
                }
                expression=expre;
                String result=makeSufixExpression();
                if(isDefined(name)){
                    Var v=getVar(name);
                    if(v.type.equals("int")){
                        v.i=(int)Float.parseFloat(result);
                    }else {
                        v.f=Float.parseFloat(result);
                    }
                }else {
                    Var v = new Var();
                    v.type = type;
                    v.name = name;
                    v.assigned = true;
                    if (v.isInt() && v.assigned) {
                        v.i = (int)Float.parseFloat(result);
                    } else if (v.assigned) {
                        v.f = Float.valueOf(result);
                    }
                    variables.add(v);
                }

            } else if(m_var_assign.matches()){

                String name = m_var_assign.group(1);
                String value = m_var_assign.group(2);


                Var v = getVar(name);
                v.assigned = true;
                if (v.type.equals("int")) {
                    v.i = (int)Float.parseFloat(value);
                } else {
                    v.f = Float.parseFloat(value);
                }
            }else if(m_var_to_var.matches()){
                String name=m_var_to_var.group(1);
                String sign=m_var_to_var.group(2);
                String expre=m_var_to_var.group(3);
                expression=expre;
                String temp=makeSufixExpression();
                Var v=getVar(name);
                v.assigned=true;
                if(v.type.equals("int")){
                    if(sign==null)
                        v.i=(int)Float.parseFloat(temp);
                    else
                        v.i=-(int)Float.parseFloat(temp);
                }else{
                    if(sign==null)
                        v.f=Float.parseFloat(temp);
                    else
                        v.f=-Float.parseFloat(temp);
                }
            }else {
                System.out.println("wrong - variable undefined");
            }

        }
        sc.close();
        String result=makeSufixExpression();
        if(TYPE.equals("int")){
            System.out.println(Integer.parseInt(result));
        }else{
            System.out.printf("%.2f",Float.parseFloat(result));
        }
    }
}

class Var{
    String name;
    int i;
    float f;
    String type;
    boolean assigned=false;

    boolean isInt(){
        return type.equals("int");
    }
    boolean isFloat(){
        return type.equals("float");
    }
}
