    package Java_exercise.PrepareForTest.isPrimeNumber;


    import java.util.Scanner;
    public class Main {
        public static void main(String[] args){
            Scanner sc=new Scanner(System.in);
            String s=sc.next();
            int num=Integer.parseInt(s);
            int i=0;
            for(i=2;i*i<num;i++){
                if(num%i==0) break;
            }
            if(i*i<num) System.out.print("composite number ");
            else System.out.print("prime number ");
            for(int j=0;j<s.length();j++){
                switch (s.charAt(j)) {
                    case '1': {
                        System.out.print("one");
                        break;
                    }
                    case '2': {
                        System.out.print("two");
                        break;
                    }
                    case '3': {
                        System.out.print("three");
                        break;
                    }
                    case '4': {
                        System.out.print("four");
                        break;
                    }
                    case '5': {
                        System.out.print("five");
                        break;
                    }
                    case '6': {
                        System.out.print("six");
                        break;
                    }
                    case '7': {
                        System.out.print("seven");
                        break;
                    }
                    case '8': {
                        System.out.print("eight");
                        break;
                    }
                    case '9': {
                        System.out.print("nine");
                        break;
                    }
                    case '0': {
                        System.out.print("zero");
                        break;
                    }

                }
                if(j<s.length()-1){
                    System.out.print(" ");
                }
            }
        }
    }
