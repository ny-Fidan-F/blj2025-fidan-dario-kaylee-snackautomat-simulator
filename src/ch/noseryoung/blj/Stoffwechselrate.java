package ch.noseryoung.blj;

import java.util.Scanner;

public class Stoffwechselrate {
    public void present() {

        Scanner input = new Scanner(System.in); // scanner object


        System.out.println("Enter your age:");
        int age = input.nextInt(); // reads age user input

        System.out.println("Enter your weight in kg:");
        double weight = input.nextDouble();

        System.out.println("Enter your height in cm:");
        double height = input.nextDouble();

        double result_male = 66.47 + 13.7 * weight + 5 * height - 6.8 * age;
        double result_female = 655.1 + 9.6 * weight + 1.8 * height - 4.7 * age;

        System.out.println("Results:");
        System.out.println("------------------------");
        System.out.println("Male: " + result_male + " calories a day");
        System.out.println("Female: " + result_female + " calories a day");

    }
}
