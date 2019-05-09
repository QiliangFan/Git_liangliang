#include<iostream>
#include<vector>
#include<stack>
using namespace std;

bool isMarked(vector<int> m, int n) {
	for (int i = 0; i<m.size(); i++) {
		if (m[i] == n) return true;
	}
	return false;
}

int findMin(const vector<int> marked, const vector<int> &length) {
	int min_city = -1;
	int min = 999999999;
	for (int i = 1; i < length.size(); i++) {
		if (!isMarked(marked,i)) {
			if (length[i]&&length[i] < min) {
				min_city = i; min = length[i];
			}
		}
	}
	return min_city;
}
int main() {
	int m, n;
	cin >> n >> m;
	vector<vector<int> > v;
	v.resize(n + 1);
	for (int i = 0; i <= n; i++) v[i].resize(n + 1, 0);
	for (int i = 1; i <= m; i++) {
		int s, d, len;
		cin >> s >> d >> len;
		v[s][d] = len;
		v[d][s] = len;
	}
	vector<int> length; 
	length.resize(n + 1, 9999999);
	vector<int> marked;
	int x, y;
	cin >> x >> y;
	marked.push_back(x);
	length[x] = 0;
	for (int i = 1; i <= n; i++) {
		if (v[x][i]) {
			length[i] = v[x][i];
		}
	}
	while (marked.size()<n) {
		for (int i = 1; i <= n; i++) {
			int t = findMin(marked, length);
			marked.push_back(t);
			if (t != -1) {
				for (int j = 1; j <= n; j++) {
					if (j != t&&v[t][j] != 0) {
						if (length[j] > length[t] + v[t][j]) length[j] = length[t] + v[t][j];
					}
				}
			}
		}
	}
	if(length[y]!= 9999999) cout << length[y] << endl;
	else cout << 0 << endl;
	return 0;
}