import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Polynomial {
    double []coefficients;
    int []exponents;

    public Polynomial() {
        coefficients = null;
        exponents = null;
    }

    public Polynomial(double []coefficients, int []exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);

        String s = sc.nextLine();

        String []polynomial = s.split("(?=[+-])");

        this.coefficients = new double[polynomial.length];
        this.exponents = new int[polynomial.length];

        for (int i = 0; i < polynomial.length; i++) {
            String []term = polynomial[i].split("x");
            if (term[0].equals(polynomial[i])) {
                this.coefficients[i] = parseDouble(term[0]);
                this.exponents[i] = 0;
            } else {
                this.coefficients[i] = parseDouble(term[0]);
                this.exponents[i] = parseInt(term[term.length - 1]);
            }
        }
    }

    Polynomial add(Polynomial polynomial) {
        int []result_exponents = new int[this.exponents.length + polynomial.exponents.length];
        double []result_coefficients = new double[this.exponents.length + polynomial.exponents.length];

        //initialize using the calling object polynomial
        int size = 0;
        for (int i = 0; i < this.exponents.length; i++) {
            result_exponents[i] = this.exponents[i];
            result_coefficients[i] = this.coefficients[i];
            size++;
        }

        //add argument polynomial to result
        for(int i = 0; i < polynomial.exponents.length; i++) {
            boolean exists = false;
            for (int j = 0; j < size; j++) {
                if (polynomial.exponents[i] == result_exponents[j]) {
                    result_coefficients[j] += polynomial.coefficients[i];
                    exists = true;
                    break;
                }
            }
            //if argument has exponents not in calling object, add one more term
            if (!exists) {
                result_exponents[size] = polynomial.exponents[i];
                result_coefficients[size] = polynomial.coefficients[i];
                size++;
            }
        }
        //TODO remove zero terms
        for (int i = 0; i < size; i++) {
            if (result_coefficients[i] == 0) {
                result_coefficients[i] = result_coefficients[size - 1];
                result_exponents[i] = result_exponents[size - 1];
                size--;
            }
        }
        Polynomial added = new Polynomial(
                Arrays.copyOfRange(result_coefficients, 0, size),
                Arrays.copyOfRange(result_exponents, 0, size)
        );

        if (added.coefficients[0] == 0) {
            return new Polynomial();
        }

        //remove redundant terms (trailing)
        return added;
    }

    Polynomial multiply(Polynomial polynomial) {
        if (this.coefficients == null || polynomial.coefficients == null) return null;

        int callerLength = this.coefficients.length;
        int argumentLength = polynomial.coefficients.length;

        Polynomial results = new Polynomial(
                new double[callerLength * argumentLength],
                new int[callerLength * argumentLength]
        );

        //expand each term in caller into argument polynomial, creating a temporary polynomial
        for (int i = 0; i < callerLength; i++) {
            Polynomial temp = new Polynomial(
                    new double[Math.max(callerLength, argumentLength)],
                    new int[Math.max(callerLength, argumentLength)]
            );

            for (int j = 0; j < polynomial.coefficients.length; j++) {
                temp.exponents[j] = this.exponents[i] + polynomial.exponents[j];
                temp.coefficients[j] = this.coefficients[i] * polynomial.coefficients[j];
            }

            results = results.add(temp);

        }
        return results;
    }

    double evaluate(double x) {
        double result = 0.0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * (Math.pow(x, this.exponents[i]));
        }
        return result;
    }

    boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    void saveToFile(String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        String text = "";
        for (int i = 0; i < this.coefficients.length; i++) {
            if (this.coefficients[i] > 0) {
                text = text + "+" + Double.toString(this.coefficients[i]);
            } else {
                text = text + Double.toString(this.coefficients[i]);
            }

            if (this.exponents[i] != 0) {
                text = text + "x" + Integer.toString(this.exponents[i]);
            }
        }
        writer.write(text);

        writer.close();
    }
}