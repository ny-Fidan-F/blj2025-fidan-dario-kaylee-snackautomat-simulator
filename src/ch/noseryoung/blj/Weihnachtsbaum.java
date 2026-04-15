package ch.noseryoung.blj;

public class Weihnachtsbaum {

        static final String RESET = "\u001B[0m";
        static final String GREEN = "\u001B[32m";
        static final String BROWN = "\u001B[38;5;130m";
        static final String RED = "\u001B[31m";
        static final String YELLOW = "\u001B[33m";


    public void present () {

            int max_stars = 11;
            int max_rows = 6;

            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < max_rows - 1; j++) {
                    System.out.print(" ");
                }
                System.out.print(YELLOW + " ☆\n" + RESET);
            }
            for (int row = 1; row <= max_rows; row++) {

                int stars = 2 * row - 1;
                int space = (max_stars - stars) / 2;
                int randPos = (int)(Math.random() * stars);
                StringBuilder line = new StringBuilder();

                for (int k = 0; k < stars; k++) {
                    if (k == randPos) {
                        line.append(RED + "@" + RESET);
                    } else {
                        line.append(GREEN + "*" + RESET );
                    }
                }


                String row_space = " ".repeat(space);
                System.out.println(GREEN + row_space + "/" + RESET + line + GREEN+ "\\" + RESET);


            }


            for (int l = 0; l < 2; l++) {
                System.out.println(BROWN + " ".repeat(max_rows - 2) + " ###" + RESET);
            }

            System.out.println("--------------------");
        }
    }
