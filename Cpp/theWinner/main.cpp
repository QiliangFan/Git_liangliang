#include<iostream>
#include<string>
#include<vector>
using namespace std;

struct container {
	vector<string> name;
	vector<int> order;
	int ORDER;
	container() { ORDER = 0; }
	void add(string n) {
		for (int i = 0; i<name.size(); i++) {
			if (n == name[i]) return;
		}
		name.push_back(n);
		order.push_back(++ORDER);
	}
	string operator[](int n) {
		return name[n];
	}
	int size() {
		return (int)name.size();
	}
	int getorder(string n) {
		for (int i = 0; i<name.size(); i++) {
			if (name[i] == n) return order[i];
		}
		return -1;
	}
};

void multi_self(vector<vector<int> > &v) {
	vector<vector<int> > a, b;
	a = v;
	b = v;
	int size =(int) v.size();
	for (int i = 1; i < size; i++)
		for (int j = 1; j < size; j++)
		{
			for (int k = 1; k < size; k++) {
				v[i][j] += a[i][k] * b[k][j];
			}
			if (v[i][j]) v[i][j] = 1;
		}
}

bool is_full(vector<vector<int> > v, int row) {
	for (int j = 1; j < v.size(); j++) {
		if (row == j) continue;
		if (v[row][j] == 0) return false;
	}
	return true;
}
int main()
{
	int mark = 0;
	ios::sync_with_stdio(false);
	int n;
	while (cin >> n&&n) {
		container cont;
		vector<string> a, b;
		for (int i = 0; i <= n - 1; i++)
		{
			string m, n;
			cin >> m >> n;
			a.push_back(m);
			b.push_back(n);
			cont.add(m);
			cont.add(n);
		}
		vector<vector<int> > v;
		v.resize(cont.size() + 1);
		for (int i = 0; i<cont.size()+1; i++) {
			v[i].resize(cont.size() + 1, 0);
		}
		for (int i = 0; i < a.size(); i++) {
			int r = cont.getorder(a[i]);
			int c = cont.getorder(b[i]);
			v[r][c] = 1;
		}
		for (int i = 1; i < v.size(); i++) {
			multi_self(v);
		}
		for (int i = 1; i < v.size(); i++) {
			if (is_full(v, i)) mark = 1;
		}
		if (mark) {
			cout << "Yes" << endl;
			mark = 0;
		}
		else {
			cout << "No" << endl;
			mark = 0;
		}
	}
	return 0;
}