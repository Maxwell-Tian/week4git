package edu.csc207.fall2024;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {
    private static final int FORTYTHOUSAND = 40000;
    private static final int THOUSAND = 1000;
    private static final int THIRTY = 30;
    private Invoice invoice;
    private Map<String, Play> plays;
    // privious checkstyle error: attributs should be private and have accesspr method

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Map<String, Play> getPlays() {
        return plays;
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        final StringBuilder statementString = new StringBuilder("Statement for " + invoice.getCustomer() + "\n");
        // previous checkstyle error: not stating StringBuilder statementString to be final
        for (Performance performance : invoice.getPerformances()) {
            // print line for this order
            statementString.append(String.format(
                    "  %s: %s (%s seats)%n",
                    getPlay(performance).getName(),
                    usd(getAmount(performance)),
                    performance.getAudience())
            );
        }
        statementString.append(String.format("Amount owed is %s%n", usd(totalAmount())));
        statementString.append(String.format("You earned %s credits\n", getVolumeCredits()));
        return statementString.toString();
    }

    private int totalAmount() {
        int totalAmount = 0;
        for (Performance performance : invoice.getPerformances()) {
            final int thisAmount = getAmount(performance);
            totalAmount += thisAmount;
        }
        return totalAmount;
    }

    private int getVolumeCredits() {
        int volumeCredits = 0;
        for (Performance performance : invoice.getPerformances()) {
            // add volume credits
            volumeCredits += volumeCreditsFor(performance);
        }
        return volumeCredits;
    }

    private static String usd(int usdLocalTemp) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(usdLocalTemp / Constants.PERCENT_FACTOR);
    }

    private int volumeCreditsFor(Performance performance) {
        int result = Math.max(performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        // add extra credit for every five comedy attendees
        if ("comedy".equals(getPlay(performance).getType())) {
            result += performance.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return result;
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
                resultAmount = FORTYTHOUSAND;
                if (performance.getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    resultAmount += THOUSAND * (performance.getAudience() - THIRTY);
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
