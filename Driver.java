import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String [] args) throws IOException {
        /* Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,0,0,5};
        Polynomial p1 = new Polynomial(c1);
        double [] c2 = {0,-2,0,0,-9};
        Polynomial p2 = new Polynomial(c2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

         */


        double[] c1 = {6, -2, 5};
        int[] e1 = {0, 1, 3};
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {-5, 2, -4, 5};
        int[] e2 = {0, 1, 3, 4};
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial file = new Polynomial(new File("TestFile.txt"));
        for (int i = 0; i < file.coefficients.length; i++) {
            System.out.format("%.1fx^%d\n",
                    file.coefficients[i],
                    file.exponents[i]
            );
        }

        /*p2.saveToFile("TestFile.txt");
        System.out.println("break1");
        Polynomial added = p1.add(p2);
        for (int i = 0; i < added.coefficients.length; i++) {
            System.out.format("%.1fx^%d\n",
                    added.coefficients[i],
                    added.exponents[i]
            );
        }*/

        System.out.println("break");
        Polynomial multiplied = p1.multiply(p2);
        for (int i = 0; i < multiplied.coefficients.length; i++) {
            System.out.format("%.1fx^%d\n",
                    multiplied.coefficients[i],
                    multiplied.exponents[i]
            );
        }
    }

}

