import java.util.*;

public class ExceptionTest{
    public static void main(String[] args) throws Exception{
        myException me=new myException();
        test_1();
    }
    public static void test_1() throws myException{
        throw new ArithmeticException("test_1");
      /*  try{
            myException.makeException_1();
        }catch(Exception e){
            //throw new myException(e);
            throw e;
        }finally{

        }*/
    }
}

class myException extends Exception{
    public myException(){}
    public myException(Exception e){
        super(e);
    }
    public static void makeException_1() throws ArithmeticException{
        throw new ArithmeticException("makeException_1");
    }
}