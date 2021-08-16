package preparation;

import util.TimeUtil;

public class Main {

    // 递归计算斐波那契
    // 0 1 1 2 3 5 8 13 21
    public static int fib1(int n) {
        if (n <= 1) {
            return n;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

    // 非递归计算
    public static int fib2(int n) {
        if (n <= 1) {
            return n;
        }

        int prev = 0;
        int curr = 1;

        for(int i = 0; i < n - 1; i++) {
            int sum = prev + curr;
            prev = curr;
            curr = sum;
        }

        return curr;
    }

    public static void main(String[] args) {
        TimeUtil.check("递归调用测试", () -> fib1(40));
        TimeUtil.check("非递归调用测试", () -> fib2(40));
    }

}
