import java.util.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionConvert {
    static LinkedList<String> strlist = new LinkedList<String>();
    static LinkedList<Var> varlist = new LinkedList<Var>();

    public static void main(String[] args) {
        getInput();
    }

    static void outputVar(Var var){
        System.out.println(var.type + " " + var.name + " " + var.value);
    }
    static void getInput() {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter(";|\n");
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            strlist.add(s);
            Pattern p = Pattern.compile("((int)|(long)|(double)|(float)) (.+)=(\\d+.?\\d+)");
            Matcher m = p.matcher(s);
            boolean b = m.matches();
            System.out.println(b);
            if (b) {        //To make the variable definition
                Pattern pp=Pattern.compile("((int)|(long)|(double)|(float)|([$a-zA-Z][.&&^=]*)|(\\d+))");
                Matcher mm=pp.matcher(s);
                if (mm.find()) {
                    Var var = new Var();
                    System.out.println(mm.group());
                    var.type = mm.group();
                    if(mm.find()) var.name = mm.group();
                    if(mm.find()) var.value = mm.group();
                    if(!var.isValid()) {
                        System.out.println("The variable name is incorrect!");
                        return;
                    }
                    outputVar(var);
                }
            }
            else{    //To make the expression-convert!
                Stack<String> varStack=new Stack<>();
                Stack<String> opeStack=new Stack<>();
                LinkedList<String> expression=new LinkedList<>();
                Pattern expre_P=Pattern.compile("((\\()|(\\+)|(-)|(\\*)|(/)|(%)|(\\))|([\\w$]+))");
                Matcher expre_m=expre_P.matcher(s);
              //  System.out.println(expre_m.find());
                while(expre_m.find()){
                    System.out.println(expre_m.group());
                }
            }
        }
        sc.close();

    }

}

class Var {
    String type = null;
    String name = null;
    String value = null;

    public boolean isValid() {
        if (!type.equals("int")  && !type.equals("double"))
            return false;
        boolean b1 = Pattern.matches("\\d.*", name);
        boolean b2 = Pattern.matches(".*[\\D&&\\W&&^$&&^_].*", name);
        System.out.println(b1+" "+b2);
        if (b1 || b2)
            return false;
        return true;
    }

}