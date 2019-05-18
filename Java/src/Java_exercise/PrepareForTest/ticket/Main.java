package Java_exercise.PrepareForTest.ticket;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] arg) {
        Scanner sc = new Scanner(System.in);
        int type = sc.nextInt();
        int[] number = new int[7];
        for (int i = 0; i < 7; i++) {
            number[i] = sc.nextInt();
        }
        switch (type) {
            case 1: {
                Arrays.sort(number, 0, 5);
                DoubleLotery dl = new DoubleLotery();
                if (dl.checkFormat(number)) {
                    System.out.println("ERROR");
                    System.exit(0);
                }
                System.out.println(dl.getLever(number));
                break;
            }
            case 2: {
                Arrays.sort(number, 0, 4);
                Arrays.sort(number,5,6);
                BigLotery bl=new BigLotery();
                if(bl.checkFormat(number)){
                    System.out.println("ERROR");
                    System.exit(0);
                }
                System.out.println(bl.getLever(number));
            }
        }
    }

}


abstract class Lottery {

    int[] befornumber;

    int[] afternumber;

    int[] winbnumber;

    int[] winanumber;

    abstract boolean checkFormat(int[] arg1);

    abstract int getLever(int[] arg1);

}

class DoubleLotery extends Lottery {

    @SuppressWarnings("empty-statement")
    DoubleLotery() {

        winbnumber = new int[]{3, 7, 9, 22, 27, 33};

        winanumber = new int[]{12};
    }


    @Override
    boolean checkFormat(int[] arg1) {
        for(int i=0;i<6;i++)
            if(arg1[i]<1||arg1[i]>33) return true;
        if(arg1[6]<1||arg1[6]>16) return true;
        return false;
    }


    @Override
    int getLever(int[] arg1) {
        int bCount=0;
        int aCount=0;
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++)
            if(arg1[i]==winbnumber[j])
                bCount++;
        }
        if(arg1[6]==winanumber[0]) aCount++;
        switch (aCount+bCount){
            case 7:{
                return 1;
            }
            case 6:{
                if(aCount==0){
                    return 2;
                }else if(aCount==1){
                    return 3;
                }
            }
            case 5:{
                return 4;
            }
            case 4:{
                return 5;
            }
            case 3:{}
            case 2:{}
            case 1:{
                if(aCount==1) return 6;
            }
        }
        return 0;
    }


}

class BigLotery extends Lottery {


    BigLotery() {

        winbnumber = new int[]{3, 7, 9, 17, 27};

        winanumber = new int[]{6, 12};


    }


    @Override
    boolean checkFormat(int[] arg1) {
        for(int i=0;i<5;i++){
            if(arg1[i]<1||arg1[i]>35) return true;
        }
        for(int i=5;i<7;i++){
            if(arg1[i]<1||arg1[i]>12) return true;
        }
        return false;
    }


    @Override
    int getLever(int[] arg1) {
        int front=getFront(arg1);
        int back=getBack(arg1);
        if(front==5&&back==2) return 1;
        if(front==5&&back==1) return 2;
        if(front==5||(front==4&&back==2)) return 3;
        if(front==4&&back==1||front==3&&back==2) return 4;
        if(front==4||front==3&&back==1||front==2&&back==2) return 5;
        if(front+back==3) return 6;

        return 1;

    }
    int getFront(int[] arg){
        int count=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(arg[i]==winbnumber[j]) count++;
            }
        }
        return count;
    }
    int getBack(int[] arg){
        int count=0;
        for(int i=5;i<7;i++){
            for(int j=0;j<2;j++){
                if(arg[i]==winanumber[j]) count++;
            }
        }
        return count;
    }

}


