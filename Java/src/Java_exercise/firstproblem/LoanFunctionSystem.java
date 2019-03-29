import java.util.*;
import java.util.Scanner;
import java.lang.Math;
enum ReturnFund{
		AVERAGE_CAPITAL_PLUS_INTEREST,
		AVERAGE_CAPITAL,
		PAYMENT_OF_INTEREST_AND_PRINCIPAL_ON_TIME;
	}

class LoanFunction{
	protected double totalNeedToReturn=0.0d;  
	protected double totalInterests=0.0d;
	protected double interestRate=0.00375d;
	protected double capital=800000.0d;
	protected int currentTime=1;
	protected int totalTime=240;
	protected int times_return_fund=0;
	ReturnFund returnMethod=null;

	protected void getCaculated(){}
	public LoanFunction(){
		totalInterests=capital*Math.pow(1+interestRate,(double)totalTime);
		totalNeedToReturn=totalInterests+capital;
	}
	public void printPlan(){
		String s=new String("              capital(B)    interests    total amounts");
		System.out.println(s);
	}
	public void returnFund(){
		times_return_fund++;
	}
	public boolean modifyInterestRate(double rate){
		interestRate=rate;
		return true;
	}
	public double returnAllOnce(){return 0.0d;}
}

class ACPI_LoanFunction extends LoanFunction{
	private double BX=0d;
	private double B=0d;
	protected void getCaculated(){
		BX=capital*interestRate*Math.pow(1+interestRate,(double)totalTime)+(Math.pow(1+interestRate,totalTime)-1);
		B=capital*interestRate*Math.pow(1+interestRate,(double)(currentTime-1))+(Math.pow(1+interestRate,totalTime)-1);
	}
	public ACPI_LoanFunction(){
		
	}
	public void printPlan(){
		super.printPlan();
		for(int i=1;i<=totalTime;i++)
		{
		getCaculated();
		System.out.format("%4d%20.2f%13.2f%15.2f\n",i,B,BX-B,BX);
		currentTime++;
	 	}
	 	currentTime=1;
	}
	public void returnFund(){
		super.returnFund();
	}
}

class AC_LoanFunction extends LoanFunction{
	private double BX=0d;
	private double B=0d;
	protected void getCaculated(){
		B=capital/totalTime;
		BX=B+(capital-currentTime*B)*interestRate;
	}
	public AC_LoanFunction(){
		
	}
	public void printPlan(){
		super.printPlan();
		for(int i=1;i<=totalTime;i++){
			getCaculated();
			System.out.format("%4d%20.2f%13.2f%15.2f\n",i,B,BX-B,BX);
			currentTime++;
		}
		currentTime=1;
	}
	public void returnFund(){
		super.returnFund();
	}
}

class POAPOT_LoanFunction extends LoanFunction{     
	int duration=0;
	public POAPOT_LoanFunction(int duration){
		this.duration=duration;
	}
	public void printPlan(){
		super.printPlan();
	}
	public void returnFund(){
		super.returnFund();
	}
}

public class LoanFunctionSystem{
	public static void main(String[] args){
		LoanFunction lf;
		Scanner in=new Scanner(System.in);
		System.out.print("Please input the number corresponding the method:(1/2/3) ");
		int method=in.nextInt();
		switch(method){
			case 1:lf=new ACPI_LoanFunction();break;
			case 2:lf=new AC_LoanFunction();break;
			case 3:{
				System.out.print("please choose the duration(month): ");
				int duration=in.nextInt();
				lf=new POAPOT_LoanFunction(duration);break;
			}
			default: return;
		}
		lf.printPlan();
		lf.returnFund();
		lf.modifyInterestRate(0.00370d);
		lf.printPlan();
	}
}
