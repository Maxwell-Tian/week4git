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
    protected Invoice invoice;
    protected Map<String, Play> plays;
    // privious checkstyle error: attributs should be private and have accesspr method

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
        StatementData statementData = new StatementData(invoice, plays);
        return renderPlainText(statementData);
    }

    protected String renderPlainText(StatementData statementData) {
        final StringBuilder statementString = new StringBuilder("Statement for " + statementData.getCustomer() + "\n");
        // previous checkstyle error: not stating StringBuilder statementString to be final
        for (PerformanceData performanceData : statementData.getPerformances()) {
            // print line for this order
            statementString.append(String.format(
                    "  %s: %s (%s seats)%n",
                    performanceData.getName(),
                    usd(performanceData.amountFor()),
                    performanceData.getAudience())
            );
        }
        statementString.append(String.format("Amount owed is %s%n", usd(statementData.totalAmount())));
        statementString.append(String.format("You earned %s credits\n", statementData.volumeCredits()));
        return statementString.toString();
    }

    protected String usd(int usdLocalTemp) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(usdLocalTemp / Constants.PERCENT_FACTOR);
    }




}
