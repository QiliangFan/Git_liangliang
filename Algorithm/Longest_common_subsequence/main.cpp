#include<iostream>
#include<string>
#include<vector>

using namespace std;
string a,b;
vector<vector<int> > v;

int get(int i,int j){
    if(i<0||j<0) return 0;
    int m=0,n=0,p=0;
    if(v[i][j]!=-1) return v[i][j];
    if(a[i]==b[j])
        m=get(i-1,j-1)+1;
    n = get(i - 1, j);
    p = get(i, j - 1);
    return v[i][j]=max(max(m,n),p);
}


int main(){
    while(cin>>a>>b){
        int m=a.size()-1;
        int n=b.size()-1;
        v.resize(m+1);
        for(int i=0;i<=m;i++)
            v[i].resize(n+1,-1);
        cout<<get(m,n)<<endl;
        v.clear();
    }
    return 0;
}