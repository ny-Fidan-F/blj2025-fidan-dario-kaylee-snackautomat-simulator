package ch.noseryoung.blj;

import java.util.Scanner;

public class Baumstammvolumen {
    public void present() {

        Scanner input = new Scanner(System.in);


        System.out.println("Length in m:");
        double l = input.nextDouble();

        System.out.println("Diameter in cm:");
        double d = input.nextDouble();

        double result = Math.PI * d * d * l / 1000;


        System.out.println("Volume: " + result);


    }
}
