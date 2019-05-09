#include<iostream>
#include<iomanip>

using namespace std;

double sum(int n){
    if(n==0){
        return 0;
    }else if(n<=4){
        return 10;
    }else if(n<=8){
        return 10+2*(n-4);
    }else if(n<=13) {
        return 18+2.4*(n-8);
    }else{
        if(n<24)
            return 18+sum(n-8);
        else{
            int count=n/8;
            return 18*(count-1)+sum(n-8*(count-1));
        }

    }
}
int main(){
    int n;
    while(cin>>n&&n){
            if(n<=0) return 0;
            
            double a=sum(n);
            long long a_long=(long long)a;
            if((a-a_long)<0.00000001){
                cout<<fixed<<setprecision(0)<<a<<endl;
            }else{
                cout<<fixed<<setprecision(1)<<a<<endl;
            }
    }
    return 0;
}