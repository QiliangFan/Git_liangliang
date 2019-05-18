package Java_exercise.PrepareForTest.sortMethod;

import java.util.*;

public class Main {
    static Vector<Integer> sign=null;
    public static void main(String[] args){
        int n=0;
        Scanner sc=new Scanner(System.in);
        n=sc.nextInt();
        Vector<Integer> v=new Vector<>();
        for(int i=1;i<=n;i++){
            int a=sc.nextInt();
            v.add(a);
        }
        Vector<Integer> vec=new Vector<>();
        for(int i=1;i<=n;i++){
            int a=sc.nextInt();
            vec.add(a);
        }
        sign=vec;
        Vector<Integer> v1=new Vector<>();v1.addAll(v);
        Vector<Integer> v2=new Vector<>();v2.addAll(v);
        if(mergeSort(v1,0,n-1, n-1/2)){
            System.out.println("Merge Sort");
            for(int i=0;i<v1.size();i++){
                System.out.print(v1.elementAt(i));
                if(i<v1.size()-1){
                    System.out.print(' ');
                }
            }
            System.exit(0);
        }
        if(insertionSort(v2)) {
            System.out.println("Insertion Sort");
            for(int i=0;i<v2.size();i++){
                System.out.print(v2.elementAt(i));
                if(i<v2.size()-1){
                    System.out.print(' ');
                }
            }
            System.exit(0);
        }
    }

    static boolean mergeSort(Vector<Integer> v,int l,int r,int mid){
        int t=2;
        int _sign=0;
        while (t<2*v.size()) {
            for (int i = 0; i < v.size(); i += t) {
                for(int j=i;j<i+t-1&&j<v.size()-1;j++){
                    for(int k=j+1;k<i+t&&k<v.size();k++){
                        if(v.elementAt(k)<v.elementAt(j)){
                            int temp=v.elementAt(k);
                            v.set(k,v.elementAt(j));
                            v.set(j,temp);
                        }
                    }
                }
            }
            t*=2;
            if(_sign==1){
                return true;
            }
            if(v.equals(sign)){
                _sign=1;
            }

        }
        return false;
    }
    static boolean insertionSort(Vector<Integer> v){
        int _sign=0;
        int i=0,j=0,key=0;
        for(i=1;i<v.size();i++){
            key=v.elementAt(i);
            j=i-1;
            while((j>=0)&&(v.elementAt(j)>key)){
                v.set(j+1,v.elementAt(j));
                j--;
            }
            v.set(j+1,key);
            if(_sign==1){
                return true;
            }
            if(v.equals(sign)) {
                _sign=1;
            }

        }
        return false;
    }
}
