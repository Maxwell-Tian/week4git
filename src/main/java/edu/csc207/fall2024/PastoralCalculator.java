package edu.csc207.fall2024;

public class PastoralCalculator extends AbstractPerformanceCalculator{

    public PastoralCalculator(Performance performance, Play play, StatementData statementData) {
        super(performance, play, statementData);
    }
    // TODO: finish amountFor() and volumeCredits()

    @Override
    public int amountFor() {
        int result = 40000;
        if (getPerformance().getAudience() > 20) {
            result += 2500 * (getPerformance().getAudience() - 20);
        }
        return result;
    }

    @Override
    public int volumeCredits() {
        return Math.max(getPerformance().getAudience() - 20, 0) + getPerformance().getAudience() / 2;
    }
}
