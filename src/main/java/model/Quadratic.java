package model;

import util.Utility;

public class Quadratic extends Problem {
    private double a;
    private double b;
    private double c;
    private String answer;
    private String question;

    public void setQuestion(String question) {
        this.question = question + getStringForm();
    }

    public Quadratic(double a, double b, double c, String question) {
        this.a = a;
        this.b = b;
        this.c = c;
        setQuestion(question + "Solve for x: ");
        setAnswer((String.valueOf(getSolution()[0]) + "," + String.valueOf(getSolution()[1])).replaceAll("\\.0", ""));
    }

    public Quadratic(String databaseForm, String answer, String question) {
        this.a = parseDatabaseForm(databaseForm)[0];
        this.b = parseDatabaseForm(databaseForm)[1];
        this.c = parseDatabaseForm(databaseForm)[2];
        this.answer = answer;
        this.question = question;
    }


    public Quadratic(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        setQuestion("Solve for x: ");
        setAnswer((String.valueOf(getSolution()[0]) + "," + String.valueOf(getSolution()[1])).replaceAll("\\.0", ""));
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


    private String getStringForm() {
        String a = String.valueOf(this.a);
        if (this.a == 1) {
            a = "";
        } else if (this.a == -1) {
            a = "-";
        }
        String b = (this.a != 0) ? addSign(this.b) : String.valueOf(this.b);

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
            sb.append(addSign(c));
        }

        return Utility.superscript(sb.toString().replaceAll("\\.0", ""));
    }

    @Override
    public String toString() {
        return question;
    }

    private String addSign(double d) {
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
        String[] inputSolutions = input.trim().replaceAll("\\.0", "").split(",");
        double[] inputSolutionsDouble = new double[2];
        for (int i = 0; i < inputSolutions.length; i++) {
            inputSolutionsDouble[i] = Double.parseDouble(inputSolutions[i]);
        }
        return (getSolution()[0] == inputSolutionsDouble[0] && getSolution()[1] == inputSolutionsDouble[1]) ||
                (getSolution()[1] == inputSolutionsDouble[0] && getSolution()[0] == inputSolutionsDouble[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quadratic quadratic = (Quadratic) o;

        if (Double.compare(quadratic.a, a) != 0) return false;
        if (Double.compare(quadratic.b, b) != 0) return false;
        if (Double.compare(quadratic.c, c) != 0) return false;
        if (answer != null ? !answer.equals(quadratic.answer) : quadratic.answer != null) return false;
        return question != null ? question.equals(quadratic.question) : quadratic.question == null;
    }
}
