package edu.sjsu.android.mortgagecalculator;

public class Calculator {
    public static float calculateFifteen(float borrow, float annualInterest, float taxesAndInsurance){
        // calculates mortgage for fifteen years
        float N = 15 * 12;
        if(annualInterest == 0){
            return (borrow / N) + 9;
        }
        float monthlyInterest = annualInterest / 1200;

        N*= -1;

        double total = (borrow * (monthlyInterest / (1 - Math.pow((1 + monthlyInterest), N)))) + taxesAndInsurance;
        String strTotal = String.format("%.2f", total);
        float mortgage = Float.parseFloat(strTotal);
        return mortgage;
    }

    public static float calculateTwenty(float borrow, float annualInterest, float taxesAndInsurance){
        // calculates mortgage for twenty years
        float N = 20 * 12;
        if(annualInterest == 0){
            return (borrow / N) + 9;
        }
        float monthlyInterest = annualInterest / 1200;

        N*= -1;
        double total = (borrow * (monthlyInterest / (1 - Math.pow((1 + monthlyInterest), N)))) + taxesAndInsurance;
        String strTotal = String.format("%.2f", total);
        float mortgage = Float.parseFloat(strTotal);
        return mortgage;
    }

    public static float calculateTwentyFive(float borrow, float annualInterest, float taxesAndInsurance){
        // calculates mortgage for twenty five years
        float N = 25 * 12;
        if(annualInterest == 0){
            return (borrow / N) + 9;
        }
        float monthlyInterest = annualInterest / 1200;

        N*= -1;
        double total = (borrow * (monthlyInterest / (1 - Math.pow((1 + monthlyInterest), N)))) + taxesAndInsurance;
        String strTotal = String.format("%.2f", total);
        float mortgage = Float.parseFloat(strTotal);
        return mortgage;
    }
}
