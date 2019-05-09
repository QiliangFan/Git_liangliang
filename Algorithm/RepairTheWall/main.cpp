#include<iostream>
#include<algorithm>
#include<vector>

using namespace std;


bool cmp(int a,int b){
    return a>b;
}
int main(){
    int l,n;
    vector<int> a;
    while(cin>>l&&cin>>n){
        int count=0;
        for(int i=1;i<=n;i++){
            int m;cin>>m;
            a.push_back(m);
        }
        sort(a.begin(),a.end(),cmp);
        for(int i=0;i<a.size();i++){
            if(l<=0) break;
            if(a[i]<=l) {l-=a[i];count++;}
            else {l=0;count++;}
        }
        if(count&&l==0) cout<<count<<endl;
        else cout<<"impossible"<<endl;
        a.clear();
    }   
    return 0;
}