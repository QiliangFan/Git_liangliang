package firstProblem;

import java.util.*;

public class Main{
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		int a=0,b=0,n=0;
		int p=1,q=1,temp=0;
		do{
			a=sc.nextInt();
			b=sc.nextInt();
			n=sc.nextInt();
			if(a==0&&b==0&&n==0) break;
			if(n==1||n==2) System.out.println(1);
			p=1;
			q=1;
			temp=0;
			for(int i=3;i<=n;i++){
				temp=(a*q+b*p)%7;
				p=q;
				q=temp;
			}
			System.out.println(q);
		}while(true);
		sc.close();
	}
}