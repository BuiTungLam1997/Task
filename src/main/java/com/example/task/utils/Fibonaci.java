package com.example.task.utils;

public class Fibonaci {
    public static int unFibonaci(int point) {
        if (point <= 1) return 1;
        else {
            int i = 2;
            while (fib(i) != point) {
                i++;
            }
            return i;
        }
    }

    public static int fib(int n) {
        if (n < 2) return 1;
        else return fib(n - 1) + fib(n - 2);
    }
}
