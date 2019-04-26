package Java_exercise.sixthproblem_serialize;


import java.util.*;
import java.io.*;

public class ReadMyObject {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileOutputStream fo=new FileOutputStream("./output.txt");
        FileInputStream fi=new FileInputStream("./output.txt");
        LiangLiang liangliang=new LiangLiang();
        ObjectOutputStream oo=new ObjectOutputStream(fo);
        ObjectInputStream oi=new ObjectInputStream(fi);
        oo.writeObject(liangliang);
        Object s=oi.readObject();
        oo.close();
        oi.close();
    }
}

class LiangLiang implements Serializable{
    int age=20;
    String gender="male";
    double weight=65.2;
}
