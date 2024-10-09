package edu.csc207.fall2024;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {
    public Invoice invoice;
    public Map<String, Play> plays;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder statementString = new StringBuilder("Statement for " + invoice.getCustomer() + "\n");

        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance performance : invoice.getPerformances()) {
            final int thisAmount = getAmount(performance);
            // add volume credits
            volumeCredits += Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
            // add extra credit for every five comedy attendees
            if ("comedy".equals(getPlay(performance).getType())) volumeCredits += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;

            // print line for this order
            statementString.append(String.format("  %s: %s (%s seats)%n", getPlay(performance).getName(), frmt.format(thisAmount / 100), performance.getAudience()));
            totalAmount += thisAmount;
        }
        statementString.append(String.format("Amount owed is %s%n", frmt.format(totalAmount / 100)));
        statementString.append(String.format("You earned %s credits\n", volumeCredits));
        return statementString.toString();
    }

    private Play getPlay(Performance performance) {
        return plays.get(performance.getPlayID());
    }

    private int getAmount(Performance performance) {
        return amountFor(performance);
    }

    private int amountFor(Performance performance) {
        int resultAmount;
        switch (getPlay(performance).getType()) {
            case "tragedy":
                resultAmount = 40000;
                if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    resultAmount += 1000 * (performance.getAudience() - 30);
                }
                break;
            case "comedy":
                resultAmount = Constants.COMEDY_BASE_AMOUNT;
                if (performance.getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    resultAmount += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                            + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD));
                }
                resultAmount += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.getAudience();
                break;
            default:
                throw new RuntimeException(String.format("unknown type: %s", getPlay(performance).getType()));
        }
        return resultAmount;
    }


}
