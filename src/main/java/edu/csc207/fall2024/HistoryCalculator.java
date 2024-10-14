package edu.csc207.fall2024;

public class HistoryCalculator extends AbstractPerformanceCalculator{

    public HistoryCalculator(Performance performance, Play play, StatementData statementData) {
        super(performance, play, statementData);
    }
    // TODO: finish amountFor() and volumeCredits()

    @Override
    public int amountFor() {
        int result = 20000;
        if (getPerformance().getAudience() > 20) {
            result += 1000 * (getPerformance().getAudience() - 20);
        }
        return result;
    }

    @Override
    public int volumeCredits() {
        return Math.max(getPerformance().getAudience() - 20, 0);
    }

}
