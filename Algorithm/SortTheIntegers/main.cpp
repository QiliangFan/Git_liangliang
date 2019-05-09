#include<iostream>
#include<string>
#include<vector>
#include<sstream>
#include<algorithm>
using namespace std;

void output(const vector<int>& integer) {
	unsigned size = integer.size();
	for (unsigned i = 0; i < size; i++) {
		cout << integer[i];
		if (i < size - 1) cout << ' ';
	}
	cout << endl;
}
int main()
{
	string s;
	stringstream ss;
	stringstream source;
	cin >> s;
	source << s;
	s.clear();
	vector<int> integer;
	while (getline(source, s, '5')) {
		int a;
		ss << s;
		ss >> a;
		if(s.size()) integer.push_back(a);
		s.clear();
		ss.clear();
	}
	sort(integer.begin(), integer.end());
	output(integer);
	return 0;
}