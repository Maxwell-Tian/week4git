package edu.csc207.fall2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatementData {
    private final Invoice invoice;
    private final Map<String, Play> plays;
    private final List<PerformanceData> performanceData = new ArrayList<>();

    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        for (Performance performance : invoice.getPerformances()) {
            Play local_play = plays.get(performance.getPlayID());
            this.performanceData.add(new PerformanceData(performance, local_play));

        }
    }

    public int totalAmount() {
        int totalAmount = 0;
        for (PerformanceData performanceData : getPerformances()) {
            final int thisAmount = performanceData.amountFor();
            totalAmount += thisAmount;
        }
        return totalAmount;
    }

    public List<PerformanceData> getPerformances() {
        return performanceData;
    }

    public String getCustomer() {
        return invoice.getCustomer();
    }

    public int volumeCredits() {
        int volumeCredits = 0;
        for (PerformanceData performanceData : getPerformances()) {
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
}
