package Java_exercise.PrepareForTest.readNumber1;


import java.math.BigDecimal;
import java.util.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String s=null;
        s=sc.next();
        Long bd = 0l;
        for(int i=0;i<s.length();i++){
            bd+=(s.charAt(i)-'0');
        }
        s=String.valueOf(bd);
        for(int i=0;i<s.length();i++){
            switch (s.charAt(i)){
                case '1':{
                    System.out.print("yi");
                    break;
                }case '2':{
                    System.out.print("er");
                    break;
                }case '3':{
                    System.out.print("san");
                    break;
                }case '4':{
                    System.out.print("si");
                    break;
                }case '5':{
                    System.out.print("wu");
                    break;
                }case '6':{
                    System.out.print("liu");
                    break;
                }case '7':{
                    System.out.print("qi");
                    break;
                }case '8':{
                    System.out.print("ba");
                    break;
                }case '9':{
                    System.out.print("jiu");
                    break;
                }case '0':{
                    System.out.print("ling");
                    break;
                }
            }
            if(i<s.length()-1){
                System.out.print(" ");
            }
        }
    }
}
