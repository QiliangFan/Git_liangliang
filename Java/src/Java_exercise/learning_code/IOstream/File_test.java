package Java_exercise.learning_code.IOstream;
import java.util.*;
import java.io.File;

public class File_test {
    public static void main(String[] args){
        File f=new File("./folder1");

        String name=f.getName();
        String filePath=f.getAbsolutePath();
        System.out.println(name+' '+filePath+' ');

    }
}
