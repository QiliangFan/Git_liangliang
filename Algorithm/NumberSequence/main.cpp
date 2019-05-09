#include<iostream>
using namespace std;


int main()
{
	long long int p,q,temp;
	long long int a,b,n;
	while(cin>>a&&cin>>b&&cin>>n){
		if(a==0&&b==0&&n==0){
			break;
		}
		if(n==1||n==2) {
			cout<<1<<endl;
		}
		else{
			p=1;
			q=1;
			for(int i=3;i<=n;i++)
			{
				temp=q;
				q=(a*q+b*p)%7ll;
				p=temp;
			}
			cout<<q<<endl;
		}
	}
	return 0;

}