package com.example;

public class App {

    public static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        System.out.println("Hello from Maven Jenkins Project-3!");

        int result = add(10, 20);

        System.out.println("Addition result: " + result);
    }
}
