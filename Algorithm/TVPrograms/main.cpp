#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;

int main()
{
	int n;
	while (cin >> n&&n) {
		vector<int> s;
		vector<int> e;
		s.resize(n + 1);
		e.resize(n + 1);
		for (int i = 1; i <= n; i++) {
			int a, b;
			cin >> a >> b;
			s[i] = a; e[i] = b;
		}
		for (int i = 1; i <= n - 1; i++) {
			for (int j = i+1; j <= n; j++) {
				if (e[j]<e[i]) {
					int t = e[i];
					e[i] = e[j];
					e[j] = t;
					t = s[i];
					s[i] = s[j];
					s[j] = t;
				}
			}
		}
		int count_ = 0;
		int last = 0;
		for (int i = 1; i <= n; i++) {
			if (i == 1) { count_++; last = e[1]; }
			else {
				if (s[i] >= last) { count_++; last = e[i]; }
				else continue;
			}
		}
		cout << count_ << endl;
	}
	return 0;
}