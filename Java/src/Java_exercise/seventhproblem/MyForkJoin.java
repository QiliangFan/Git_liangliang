package Java_exercise.seventhproblem;

import java.util.*;
import java.util.concurrent.*;

public class MyForkJoin {
    public static void main(String[] args) {
        ForkJoinPool fp = new ForkJoinPool();
        ComputePiFJ cp = new ComputePiFJ(1, 1,9000000 );
        fp.submit(cp);
        System.out.println(4*cp.join());
    }
}

class ComputePiFJ extends RecursiveTask<Double> {

    private Integer numberDown = 0;
    private Integer numberUp = 0;
    private Integer numberEnd = 0;

    public ComputePiFJ(Integer d, Integer u, Integer end) {
        numberDown = d;
        numberUp = u;
        numberEnd = end;
    }

    @Override
    protected Double compute() {
        if (numberEnd - numberDown <= 4000000) {
            Double r = 0.0;
            Double sign = numberDown % 4 == 3 ? -1.0 : 1.0;
            for (int i = numberDown; i <= numberEnd; i += 2) {
                r += sign / i;
                sign = -sign;
            }
            return r;
        }
        Double result = 0.0;
        Integer temp1=((numberDown + numberEnd) / 4 - 1) * 2 + numberDown;
        ComputePiFJ c1 = new ComputePiFJ(numberDown, numberUp, temp1%2==0?temp1-1:temp1);
        ComputePiFJ c2 = new ComputePiFJ(temp1%2==0?temp1-1:temp1+2, numberUp, numberEnd);
        invokeAll(c1, c2); //better than c1.fork(),c2.fork()!
        Double r1 = c1.join();
        Double r2 = c2.join();
        return r1 + r2;
    }
}