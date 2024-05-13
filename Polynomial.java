public class Polynomial {
    double []coefficients;

    public Polynomial() {
        coefficients = new double[0];
    }

    public Polynomial(double []coefficients) {
        this.coefficients = coefficients;
    }

    Polynomial add(Polynomial polynomial) {
        Polynomial result;
        if (this.coefficients.length < polynomial.coefficients.length) {
            result = new Polynomial(polynomial.coefficients);
            for (int i = 0; i < this.coefficients.length; i++) {
                result.coefficients[i] = this.coefficients[i] + polynomial.coefficients[i];
            }
        } else {
            result = new Polynomial(this.coefficients);
            for (int i = 0; i < this.coefficients.length; i++) {
                result.coefficients[i] = polynomial.coefficients[i] + polynomial.coefficients[i];
            }
        }
        return result;
    }

    double evaluate(double x) {
        double result = 0.0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * (Math.pow(x, i));
        }
        return result;
    }

    boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}