import java.io.IOException;
import java.util.*;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExpressionConvert {
    static Logger ml;
    static FileHandler fh;
    static LinkedList<String> strlist = new LinkedList<String>();
    static LinkedList<Var> varlist = new LinkedList<Var>();

    public static void main(String[] args) throws Exception{   //entrance
        ExpressionConvert.fh=new FileHandler("log.txt");
        ExpressionConvert.ml=Logger.getLogger("Expression Convert");
        ml.setUseParentHandlers(false);
        ml.addHandler(fh);
        ml.removeHandler(new ConsoleHandler());
        fh.setFormatter(new SimpleFormatter());
        ml.setLevel(Level.ALL);
        getInput();
    }

    static void outputVar(Var var){    //for debug
        System.out.println(var.type + " " + var.name + " " + var.value);
    }

    static boolean verify_name_valid(String s){  //for valid_confirm
        for(Var i :varlist){
            if(i.name.equals(s)){
                return true;
            }
        }
        return false;
    }

    static boolean isOperator(String s){   //
        if(s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")||s.equals("%")||s.equals("(")||s.equals(")")) return true;
        return false;
    }

    static boolean is_priority(String s,String s_stacktop){   //priority judge
        if(s.equals("*")||s.equals("/")||s.equals("%")) {
            if(s_stacktop.equals("+")||s_stacktop.equals("-")) return true;
            else return false;
        }
        return false;
    }

    static String getType(String s){    //get the varname
        for(Var i:varlist){
            if(i.name.equals(s)){
                return i.type;
            }
        }
        Pattern p=Pattern.compile("\\d*.?\\d*");
        Matcher m=p.matcher(s);
        if(m.matches()) return s;
        else return new String();
    }

    static String getValue(String s){   //get the varValue
        for(Var i:varlist){
            if(i.name.equals(s)){
                return i.value;
            }
        }
        Pattern p=Pattern.compile("\\d*.?\\d*");
        Matcher m=p.matcher(s);
        if(m.matches()) return s;
        else return new String();
    }
    static boolean expre_convertTo_expreSuffix(LinkedList<String> expression,LinkedList<String> expression_suffix){
       /* for(String s:expression){   //for test
            System.out.println(s);
        }*/
        if(expression.size()==1){
            String n=expression.get(0);
            String value=getValue(n);
            ml.info("output:"+value);
            System.out.println(value);
            return true;
        }
        Stack<String> opeStack=new Stack<>();
        for(String s:expression){
            if(isOperator(s)){
                if(s.equals("(")){//push "(" into stack
                    opeStack.push(s);
                }else if(s.equals(")")){//pop until meet the "("
                    while(!opeStack.empty()&&!opeStack.peek().equals("(")){
                        expression_suffix.add(opeStack.pop());
                    }
                    if(opeStack.empty()) return false;  //brackets unmatched
                    opeStack.pop();
                }else if(s.equals("+")||s.equals("-")){
                    if(opeStack.empty()){
                        opeStack.push(s);
                    }else if(is_priority(s, opeStack.peek())){
                        opeStack.push(s);
                    }else{
                        while(!opeStack.empty()&&!opeStack.peek().equals("(")&&!is_priority(s, opeStack.peek())){
                            expression_suffix.add(opeStack.pop());
                        }
                        opeStack.push(s);
                    }
                }else if(s.equals("*")||s.equals("/")||s.equals("%")){
                    if(opeStack.empty()){
                        opeStack.push(s);
                    }else  if(is_priority(s, opeStack.peek())){
                        opeStack.push(s);
                    }else{
                        while(!opeStack.empty()&&!opeStack.peek().equals("(")&&!is_priority(s, opeStack.peek())){
                            expression_suffix.add(opeStack.pop());
                        }
                        opeStack.push(s);
                    }
                }
            }else{
                expression_suffix.add(s);
            }
        }
        //for(String s:opeStack) System.out.println(s);    //for test!!!
        while(!opeStack.empty()&&!opeStack.peek().equals("(")){
            expression_suffix.add(opeStack.pop());
        }
        if(!opeStack.empty()) return false;
        for(int i=0;i<expression_suffix.size();i++){
            String ss=expression_suffix.get(i);
            if(isOperator(ss)){
                String n1=expression_suffix.get(i-2);
                String n2=expression_suffix.get(i-1);
                String type1=getType(n1);
                String type2=getType(n2);
                String value1=getValue(n1);
                String value2=getValue(n2);
               // System.out.println(n1+" "+n2+" "+type1+" "+type2+" "+value1+" "+" "+value2);    //for test
                if(ss.equals("+")){
                    expression_suffix.set(i, 
                    (type1.equals("int")||type1.equals("long")?Integer.parseInt(value1):Double.parseDouble(value1))
                    +
                    (type2.equals("int")||type2.equals("long")?Integer.parseInt(value2):Double.parseDouble(value2))
                    +""
                    );
                }else if(ss.equals("-")){
                    expression_suffix.set(i, 
                    (type1.equals("int")||type1.equals("long")?Integer.parseInt(value1):Double.parseDouble(value1))
                    -
                    (type2.equals("int")||type2.equals("long")?Integer.parseInt(value2):Double.parseDouble(value2))
                    +""
                    );

                }else if(ss.equals("*")){
                    expression_suffix.set(i, 
                    (type1.equals("int")||type1.equals("long")?Integer.parseInt(value1):Double.parseDouble(value1))
                    *
                    (type2.equals("int")||type2.equals("long")?Integer.parseInt(value2):Double.parseDouble(value2))
                    +""
                    );
                }else if(ss.equals("/")){
                    expression_suffix.set(i, 
                    (type1.equals("int")||type1.equals("long")?Integer.parseInt(value1):Double.parseDouble(value1))
                    /
                    (type2.equals("int")||type2.equals("long")?Integer.parseInt(value2):Double.parseDouble(value2))
                    +""
                    );
                }else if(ss.equals("%")){
                    expression_suffix.set(i, 
                    (type1.equals("int")||type1.equals("long")?Integer.parseInt(value1):Double.parseDouble(value1))
                    %
                    (type2.equals("int")||type2.equals("long")?Integer.parseInt(value2):Double.parseDouble(value2))
                    +""
                    );
                }
                expression_suffix.remove(i-1);
                expression_suffix.remove(i-2);
                i-=2;
            }
        }
        ml.info("output:"+expression_suffix.get(0));
        System.out.println(expression_suffix.get(0));
        return true;
    }
    static void getInput() throws Exception{        //jshell
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter(";|\n");
        begin:while (sc.hasNextLine()) {
            
            String s = sc.nextLine();
            ml.info("input:"+s);
            strlist.add(s);
            Pattern p = Pattern.compile("((int)|(long)|(double)|(float)) (.+)=(-?\\d+.?\\d*);?");  //update: processing negative numbers
            Matcher m = p.matcher(s);
            boolean b = m.matches();

            Pattern p_assign=Pattern.compile(" *([a-zA-Z_$][\\w$_]*) *= *(-?\\d+.?\\d*) *");
            Matcher m_assign=p_assign.matcher(s);
            if (b) {        //To make the variable definition
                //System.out.println("1");     //for test
                Pattern pp=Pattern.compile("(int)|(long)|(double)|(float)|([$a-zA-Z_][\\w$_]*)|(-?\\d+.?\\d*)");
                Matcher mm=pp.matcher(s);
                if (mm.find()) {
                    Var var = new Var();
                    var.type = mm.group();
                    if(mm.find()) var.name = mm.group();
                    if(mm.find()) var.value = mm.group();
                    //System.out.println(var.name);   //for test

                    try{
                        var.isValid();
                    }catch(VariableTypeException vte){
                        System.out.println("Incorrect Varibale Type!");
                        continue begin;
                    }catch(VariableNameException vne){
                        System.out.println("Invalid Variable Name!");
                    }
                    varlist.add(var);
                }
            }else if(m_assign.matches()){
                //System.out.println("2");     //for test
                String name=new String();
                String value=new String();
                 name=m_assign.group(1);
                 value=m_assign.group(2);
                //System.out.println(name+" "+value);   //for test
                if(!verify_name_valid(name)){
                    System.out.println("Variable Undefined OR Invalid Operation");
                    continue begin;
                }
                for(Var i:varlist){
                    if(i.name.equals(name)) i.value=value;
                }
            }else{    //To make the expression-convert!
                //System.out.println("3");    //for test!
                LinkedList<String> expression_suffix=new LinkedList<>();
                LinkedList<String> expression=new LinkedList<>();
                Pattern expre_P=Pattern.compile("((\\()|(\\+)|(-)|(\\*)|(/)|(%)|(\\))|([\\w$]+))");
                Matcher expre_m=expre_P.matcher(s);
              //  System.out.println(expre_m.find());
                while(expre_m.find()){
                    String expre_s=expre_m.group();
                    if(!expre_s.equals("+")&&!expre_s.equals("-")&&!expre_s.equals("/")&&!expre_s.equals("*")&&!expre_s.equals("%")){
                        if(!verify_name_valid(expre_s)) {
                            System.out.println("Variable Undefined!");
                            continue begin;
                        }
                    }
                    expression.add(expre_s);
                }
                /*for(String sss:expression)    //for test!!!
                System.out.println(sss);   */
               try{ 
                   if(!expre_convertTo_expreSuffix(expression,expression_suffix)){
                    //System.out.println("Operator matching exception"); 
                    throw new OperatorNotMatchException();
                    }
                }catch(Exception e){
                    continue begin;
                }
            }
        }
        sc.close();
    }

}

class Var {     //simplify the input
    String type = null;
    String name = null;
    String value = null;

    public boolean isValid() throws Exception{
        if (!type.equals("int")  && !type.equals("double")&&!type.equals("long")&&!type.equals("float"))
            return false;
        boolean b1 = Pattern.matches("\\d.*", name);
        boolean b2 = Pattern.matches("[^\\w_$]*", name);
        if (b1 || b2)
            return false;
        return true;
    }
   
}

class OperatorNotMatchException extends Exception{
    public OperatorNotMatchException(Exception e){
        super(e);
    }
    public OperatorNotMatchException(){

    }
}

class VariableTypeException extends Exception{
    public VariableTypeException(Exception e){
        super(e);
    }
    public VariableTypeException(){

    }
}

class VariableNameException extends Exception{
    static final long serialVersionUID=45L;
    public VariableNameException(Exception e){
        super(e);
    }
    public VariableNameException(){

    }
}

