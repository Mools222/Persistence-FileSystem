import java.io.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        /**
         * a. Plain-text .txt fil
         */

        File file1 = new File("text.txt");

        try (java.io.PrintWriter output = new java.io.PrintWriter(file1)) {
            output.print("Plain-text .txt fil");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /**
         * b. Billede gemt som image fil (f.eks. bmp, gif eller jpg)
         */

        URL url = null;
        File file2 = new File("billede.png");

        try {
            url = new URL("https://www.smileysapp.com/emojis/ok-smiley.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (
                BufferedInputStream input = new BufferedInputStream(url.openStream());
                BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file2))
        ) {
            int r;
            while ((r = input.read()) != -1)
                output.write(r);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * c. Serialize
         */

        // Create 5 objects and serialize them
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("LoanObjects.dat")))) {
            for (int i = 1; i <= 5; i++) {
                objectOutputStream.writeObject(new Loan(i + 5, i + 10, i * 10000));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retrieve and display the serialized objects
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("LoanObjects.dat")))) {
            for (int i = 0; i < 5; i++) {
                Loan loan = (Loan) objectInputStream.readObject();
                System.out.println("Loan " + (i + 1) + ":");
                System.out.println("Annual interest rate: " + loan.getAnnualInterestRate());
                System.out.println("Number of years: " + loan.getNumberOfYears());
                System.out.println("Loan amount: " + loan.getLoanAmount());
                System.out.println(); // LF
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Loan implements Serializable {
    private double annualInterestRate;
    private int numberOfYears;
    private double loanAmount;
    private java.util.Date loanDate;

    public Loan() {
        this(2.5, 1, 1000);
    }

    public Loan(double annualInterestRate, int numberOfYears, double loanAmount) {
        this.annualInterestRate = annualInterestRate;
        this.numberOfYears = numberOfYears;
        this.loanAmount = loanAmount;
        loanDate = new java.util.Date();
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getMonthlyPayment() {
        double monthlyInterestRate = annualInterestRate / 1200;
        double monthlyPayment = loanAmount * monthlyInterestRate / (1 - (1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12)));
        return monthlyPayment;
    }

    public double getTotalPayment() {
        double totalPayment = getMonthlyPayment() * numberOfYears * 12;
        return totalPayment;
    }

    public java.util.Date getLoanDate() {
        return loanDate;
    }
}
