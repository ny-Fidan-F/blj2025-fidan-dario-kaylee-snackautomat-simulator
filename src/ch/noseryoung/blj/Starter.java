package ch.noseryoung.blj;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Starter {
    static void main() {
        int select = 0;
        Scanner input = new Scanner(System.in);

        System.out.println("hi");
        System.out.println("-------------------------\n");
        System.out.println("1 : Three-Two-One");
        System.out.println("2 : Weihnachtsbaum");
        System.out.println("3 : Stoffwechselrate");
        System.out.println("0 : Quit");

        try {
            select = input.nextInt();
            throw new RuntimeException("Kill yourself");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        if (select == 1) {
            new ThreeTwoOne().present();
        }

        if (select == 2) {
            new Weihnachtsbaum().present();
        }

        if (select == 2) {
            new Weihnachtsbaum().present();
        }

        if (select == 3) {
            new Stoffwechselrate().present();
        }

        if (select == 4) {
            new Baumstammvolumen().present();
        }

        if (select == 0) {
            System.exit(0);
        }

    }
}
