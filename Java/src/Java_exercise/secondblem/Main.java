//package Java_exercise.secondblem;
import java.util.*;
import java.util.regex.*;
public class Main {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        if(sc.hasNext()){
            String line=sc.nextLine();
            Pattern p1=Pattern.compile("([+\\-*/%()])|\\(?(-?\\d+\\.?\\d*)");
            Matcher m1=p1.matcher(line);
            Stack<String> prefix=new Stack<>();
            while(m1.find()){
                if(m1.group(1)!=null) prefix.push(m1.group(1));
                if(m1.group(2)!=null) prefix.push(m1.group(2));
            }
            Stack<String> sufix=makeExpression(prefix);
            Stack<Double> compute = new Stack<>();
            for(int i=0;i<sufix.size();i++){
                if(sufix.size()==1&&!isOperand(sufix.elementAt(0))) break;
                String s=sufix.elementAt(i);
                Double result=0.0;
                if(compute.size()>=2&&isOperand(s)){
                    if(s.equals("+")) result=compute.elementAt(compute.size()-2)+compute.elementAt(compute.size()-1);
                    if(s.equals("-")) result=compute.elementAt(compute.size()-2)-compute.elementAt(compute.size()-1);
                    if(s.equals("*")) result=compute.elementAt(compute.size()-2)*compute.elementAt(compute.size()-1);
                    if(s.equals("/")) result=compute.elementAt(compute.size()-2)/compute.elementAt(compute.size()-1);
                    if(s.equals("%")) result=compute.elementAt(compute.size()-2)%compute.elementAt(compute.size()-1);
                    sufix.set(i,String.valueOf(result));
                    i-=1;
                    compute.remove(compute.size()-1);
                    compute.remove(compute.size()-1);
                }
                if(!isOperand(s))
                {
                    compute.push(Double.parseDouble(s));
                    sufix.remove(i);i-=1;
                }
            }
            if(sufix.elementAt(0).length()>6) sufix.set(0,sufix.elementAt(0).substring(0,sufix.elementAt(0).length()-1));
            System.out.println(Float.parseFloat(sufix.elementAt(0)));
        }
        sc.close();

    }
    private static Stack<String> makeExpression(Stack<String> prefix){
        Stack<String> sufix=new Stack<>();
        Stack<String> operand_temp=new Stack<>();
        for(int i=0;i<prefix.size();i++){
            if(prefix.elementAt(i).equals("(")) {
                operand_temp.push(prefix.elementAt(i));
            }
            else if(prefix.elementAt(i).equals(")")){
                while(!operand_temp.peek().equals("(")){
                    sufix.push(operand_temp.pop());
                }
                operand_temp.pop();
            }else if(prefix.elementAt(i).equals("+")||prefix.elementAt(i).equals("-")) {
                if (operand_temp.isEmpty()) operand_temp.push(prefix.elementAt(i));
                else {
                    while (!operand_temp.isEmpty() && !operand_temp.peek().equals("(")) {
                        sufix.push(operand_temp.pop());
                    }

                operand_temp.push(prefix.elementAt(i));
                }
            }else if(prefix.elementAt(i).equals("*")||prefix.elementAt(i).equals("/")||prefix.elementAt(i).equals("%")){
                String temp=prefix.elementAt(i);
                if(operand_temp.isEmpty()) operand_temp.push(prefix.elementAt(i));
                else {
                    while (!operand_temp.isEmpty() && (operand_temp.peek().equals("*") || operand_temp.peek().equals("%") || operand_temp.peek().equals("/"))) {
                        sufix.push(operand_temp.pop());
                    }
                    operand_temp.push(temp);
                }
            }else {
                sufix.push(prefix.elementAt(i));
            }
        }
        while(!operand_temp.isEmpty()){
            sufix.push(operand_temp.pop());
        }
        return sufix;
    }
    static boolean isOperand(String s){
        return Pattern.matches("\\D",s);
    }
}