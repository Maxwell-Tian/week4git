package edu.csc207.fall2024;

public abstract class AbstractPerformanceCalculator {

    private Performance performance;
    private Play play;
    private static StatementData statementData;

    public AbstractPerformanceCalculator(Performance performance, Play play,StatementData statementData) {
        this.play = play;
        this.performance = performance;
        this.statementData = statementData;
    }

    public Play getPlay() {
        return play;
    }

    public Performance getPerformance() {
       return performance;
    }

    public int amountFor() {
        int totalAmount = 0;
        for (PerformanceData performanceData : statementData.getPerformances()) {
            final int thisAmount = performanceData.getAmount();
            totalAmount += thisAmount;
        }
        return totalAmount;
    }

    public int volumeCredits() {
        int volumeCredits = 0;
        for (PerformanceData performanceData : statementData.getPerformances()) {
            // add volume credits
            volumeCredits += volumeCreditsFor(performanceData);
        }
        return volumeCredits;
    }

    private int volumeCreditsFor(PerformanceData performanceData) {
        int result = Math.max(performanceData.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
        // add extra credit for every five comedy attendees
        if ("comedy".equals(performanceData.getType())) {
            result += performanceData.getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
        }
        return result;
    }

    public int getAmount() {
        int resultAmount;
        switch (play.getType()) {
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
                throw new RuntimeException(String.format("unknown type: %s", play.getType()));
        }
        return resultAmount;
    }

    public static AbstractPerformanceCalculator createPerformanceCalculator(Performance performance, Play play){
       /*
       this function creates different calculator based on the type of show
        */
        String type = play.getType().toLowerCase();
        return getAbstractPerformanceCalculator(performance, play, type);
    }

    private static AbstractPerformanceCalculator getAbstractPerformanceCalculator(Performance performance, Play play, String type) {
        if (type.equals("history")){
            return new HistoryCalculator(performance, play, statementData);
        }
        else if (type.equals("comedy")){
            return new ComedyCalculator(performance, play, statementData);
        }
        else if (type.equals("pastoral")){
            return new PastoralCalculator(performance, play, statementData);
        }
        else if (type.equals("tragedy")){
            return new TragedyCalculator(performance, play, statementData);
        }
        else{
            throw new IllegalArgumentException("Unknown performance type: " + type);
        }
    }

}
