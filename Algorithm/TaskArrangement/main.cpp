#include<iostream>
#include<cstdio>
using namespace std;
const int inf=1e9;
int F[5005],s,t[5005],n;
int f[5005];
int main(){
	scanf("%d%d",&n,&s);
	int i,j,x,y;
	for(i=1;i<=n;i++){
		scanf("%d%d",&x,&y);
		t[i]=t[i-1]+x;
		F[i]=F[i-1]+y;
		f[i]=inf;
	}
	for(i=1;i<=n;i++)
	    for(j=0;j<=i;j++)
	        f[i]=min(f[i],f[j]+t[i]*(F[i]-F[j])+s*(F[n]-F[j]));
	cout<<f[n];
}