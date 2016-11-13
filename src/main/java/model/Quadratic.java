package model;

import module.Util;

public class Quadratic extends Problem {
    private double a;
    private double b;
    private double c;
    private String answer;

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Quadratic(double a, double b, double c, String answer, String question) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.answer = answer;
        this.question = question;
    }

    public Quadratic(String databaseForm, String answer, String question) {
        this.a = parseDatabaseForm(databaseForm)[0];
        this.b = parseDatabaseForm(databaseForm)[1];
        this.c = parseDatabaseForm(databaseForm)[2];
        this.answer = answer;
        this.question = question;
    }

    private String question;

    public Quadratic(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public double[] getSolution() {
        double[] solutions = new double[2];
        solutions[0] = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        solutions[1] = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);

        for (int i = 0; i < solutions.length; i++) {
            if (solutions[i] == 0) {
                solutions[i] = Math.abs(solutions[i]);
            }
        }
        return solutions;
    }

    @Override
    public String toString() {
        String a = String.valueOf(this.a);
        if (this.a == 1) {
            a = "";
        } else if (this.a == -1) {
            a = "-";
        }
        String b = (this.a != 0) ? addSign(this.b, false) : String.valueOf(this.b);

        if (this.b == 1 && this.a != 0) {
            b = "+";
        } else if (this.b == 1) {
            b = "";
        } else if (this.b == -1) {
            b = "-";
        }

        StringBuilder sb = new StringBuilder();
        if (this.a != 0) {
            sb.append(a).append("x^2");
        }
        if (this.b != 0) {
            sb.append(b).append("x");
        }
        if (this.c != 0) {
            sb.append(addSign(c, false));
        }
        return Util.superscript(sb.toString().replaceAll("\\.0", ""));
    }

    public String toFactorisedForm() {
        double x1 = getSolution()[0];
        double x2 = getSolution()[1];
        if (x1 != 0.0 && x2 != 0.0) {
            return ("(x" + addSign(x1, true) + ")(x" + addSign(x2, true) + ")").replaceAll("\\.0", "");
        } else if (x1 == 0.0) {
            return "x(x" + addSign(x2, true) + ")";
        } else if (x2 == 0.0) {
            return "x(x" + addSign(x1, true) + ")";
        }
        System.err.println("Equation is equal 0: " + toString());
        return null;
    }

    private String addSign(double d, boolean reverseSign) {
        if (reverseSign) {
            d = d - 2 * d;
        }
        if (d >= 0) {
            return "+" + d;
        } else {
            return String.valueOf(d);
        }

    }

    public String toDatabaseForm() {
        return this.a + "," + this.b + "," + this.c;
    }

    private double[] parseDatabaseForm(String quadratic) {
        double result[] = new double[3];
        String[] bits = quadratic.split("\\,");

        for (int i = 0; i < result.length; i++) {
            result[i] = Double.parseDouble(bits[i]);
        }
        return result;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean checkAnswer(String input) {
        String[] inputSolutions = input.split(",");
        double[] inputSolutionsDouble = new double[2];
        for (int i = 0; i < inputSolutions.length; i++) {
            inputSolutionsDouble[i] = Double.parseDouble(inputSolutions[i]);
        }
        return (getSolution()[0] == inputSolutionsDouble[0] && getSolution()[1] == inputSolutionsDouble[1]) ||
                (getSolution()[1] == inputSolutionsDouble[0] && getSolution()[0] == inputSolutionsDouble[1]);
    }
}