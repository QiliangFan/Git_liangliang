package Java_exercise.sixthproblem_serialize;
import java.io.*;
import java.util.*;

public class Employee implements Serializable
{
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String n, double s, int year, int month, int day)
    {
        name = n;
        salary = s;
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        hireDay = calendar.getTime();
    }


    private static final String SAVED_PATH = "G:/GIT_liangliang/java/src/Java_exercise/sixthproblem_serialize/employee.dat";
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // 持久化到本地存储中
        Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVED_PATH))) {
            out.writeObject(harry);
        }

    }
}
