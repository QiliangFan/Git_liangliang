#include<iostream>
#include<algorithm>
#include<vector>

using namespace std;

struct road{
    int beg;
    int end;
    int len;
    int weight;
    road(){
        beg=end=len=weight=0;
    }
    void set(int b,int e,int l,int w){
        beg=b;
        end=e;
        len=l;
        weight=w;
    }
    road(int b,int e,int l,int w){
        set(b,e,l,w);
    }
};

bool getLen(vector<road> v,int b,int e,int& len,int& cost){
    for(int i=0;i<v.size();i++){
        if(v[i].beg==b){
            if(v[i].end==e) {
                len=v[i].len;
                cost=v[i].weight;
                return true;
            }else{
                len=cost=-1;
            }
        }else if(v[i].beg==e){
            if(v[i].end==b){
                len=v[i].len;
                cost=v[i].weight;
                return true;
            }else{
                len=cost=-1;
            }
        }
        
    }
    return false;
}
bool hasUnreach(int length[],int n){
    for(int i=1;i<=n;i++){
        if(length[i]==-1) return true;
    }
    return false;
}
int main(){
    int n,m;
    while(cin>>n&&cin>>m&&n&&m){
        int s=0,t=0;
        vector<road> v;
        int b,e,l,w;
        for(int i=1;i<=m;i++){
            cin>>b>>e>>l>>w;
            v.push_back(road(b,e,l,w));
        }
        cin>>s>>t;
        int length[n+1];
        int preNode[n+1];
        for(int i=0;i<=n;i++){length[i]=-1;preNode[i]=-1;}
        for(int i=1;i<=n;i++){
            int len=0,cost=0;
            if(i==s) {
                length[i]=0;
                preNode[i]=s;
                continue;
            }
            if(getLen(v,s,i,len,cost)) preNode[i]=s;
            length[i]=len;
            
        }
        while(hasUnreach(length,n)){
            for(int i=1;i<=n;i++){
                for(int j=1;j<=n;j++){
                    if(j==i||j==s||i==s) continue;
                    int len=0,cost=0;
                    if(getLen(v,j,i,len,cost)){
                        if(length[i]==-1){
                            if(length[j]==-1) continue;
                            length[i]=length[j]+len;
                            preNode[i]=j;
                        }else if(length[i]>0){
                            if(length[j]==-1) continue;
                            if(length[i]>length[j]+len){
                                length[i]=length[j]+len;
                                preNode[i]=j;
                            }
                        }
                    }else {
                        continue;
                    }
                }

            }
        }
        int k=t;
        int sum=0;
        int len=0,cost=0;
        while(k!=s){
            getLen(v,preNode[k],k,len,cost);
            sum+=cost;
            k=preNode[k];
        }
        cout<<length[t]<<' '<<sum<<endl;
    }
    return 0;
}