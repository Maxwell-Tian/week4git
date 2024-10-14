package edu.csc207.fall2024;

public class PerformanceData {
    private static final int FORTYTHOUSAND = 40000;
    private static final int THOUSAND = 1000;
    private static final int THIRTY = 30;
    private Performance performance;
    private Play play;

    public PerformanceData(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    public String getType(){
        return play.getType();
    }

    public int amountFor() {
        int resultAmount;
        switch (getType()) {
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
                throw new RuntimeException(String.format("unknown type: %s", getType()));
        }
        return resultAmount;
    }

    public Performance getPerformance() {
        return performance;
    }

    public int getAudience(){
        return performance.getAudience();
    }

    public String getName() {
        return play.getName();
    }
}
