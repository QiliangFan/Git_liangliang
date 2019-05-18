package Java_exercise.PrepareForTest.tree;

import java.util.ArrayList;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        RankBST<Rankable> tree=new RankBST<>();
        for(int i=1;i<=n;i++){
            int mark=sc.nextInt();
            if(mark==1){
                Student s=new Student();
                s.intputRank(sc);
                tree.insert(s);
            }else{
                Worker w=new Worker();
                w.intputRank(sc);
                tree.insert(w);
            }
        }
        tree.preSearch(tree.root);
    }

}


interface Rankable {

    int getRank();

    void intputRank(Scanner s);

}

class Worker implements Rankable {

    int salary;


    @Override

    public int getRank() {

        // example.

        return salary / 100;

    }


    @Override

    public void intputRank(Scanner s) {

        //example

        salary = s.nextInt();

    }

    public String toString() {

        return "Worker:" + getRank();

    }


}


class Student implements Rankable {

    @Override

    public void intputRank(Scanner s) {
        // //Need to Realize  ,get the course score list
        int n=s.nextInt();

        for(int i=1;i<=n;i++){
            int a = s.nextInt(), b = s.nextInt();
            courseList.add(new CourseScore(a, b));
        }

    }


    class CourseScore {

        int Credit;

        int score;

        CourseScore(int Credit, int score) {

            this.Credit = Credit;

            this.score = score;

        }

        int getCredit() {
            return Credit;
        }

        int getScore() {
            return score;
        }
    }


    ArrayList<CourseScore> courseList=new ArrayList<>();


    public int getRank() {
        int sum = 0;
        int scoreAll = 0;
        int creditAll = 0;
        for (int i = 0; i < courseList.size(); i++) {
            scoreAll += courseList.get(i).getScore() * courseList.get(i).getCredit();
            creditAll += courseList.get(i).getCredit();
        }
        return  scoreAll/creditAll;
    }

    @Override
    public String toString() {
        return "Student:"+getRank();
    }
}

class Node<T extends Rankable> {

    Node preNode;

    T data;

    Node leftChild;

    Node rightChild;


    public Node(T t, Node preNode) {

        this.preNode = preNode;

        this.data = t;

    }


}

class RankBST<T extends Rankable> {

    Node<T> root;


    void insert(T data) {
        Node<T> cur = root;
        Node<T> last = null;
        int sign = 0;
        while (cur != null) {
            if (data.getRank() >= cur.data.getRank()) {
                last = cur;
                cur = cur.rightChild;
                sign = 1;
            } else {
                last = cur;
                cur = cur.leftChild;
                sign = -1;
            }
        }
        if (sign == -1)
            last.leftChild = new Node<>(data, last);
        else if(sign==1)
            last.rightChild = new Node<>(data, last);
        else if(sign==0)
            root=new Node<>(data,null);
    }

    void delete(T data) {


    }


    void print(Node<T> n) {
        if(n!=root) System.out.print("\n");
        System.out.print(n.data);

    }
    void preSearch(Node<T> p){
        if(p!=null) print(p);
        if(p.leftChild!=null) preSearch(p.leftChild);
        if(p.rightChild!=null) preSearch(p.rightChild);
    }
}