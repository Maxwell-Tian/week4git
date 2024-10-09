package edu.csc207.fall2024;

public abstract class AbstractPerformanceCalculator {

   private Performance performance;
   private Play play;

   public AbstractPerformanceCalculator(Performance performance, Play play) {
       this.play = play;
       this.performance = performance;
   }

   public abstract int amountFor();

   public abstract int volumeCredits();

   public Play getPlay() {
       return play;
   }

   public Performance getPerformance() {
       return performance;
   }

   public static AbstractPerformanceCalculator createPerformanceCalculator(Performance performance, Play play){
       /*
       this function creates different calculator based on the type of show
        */
       String type = play.getType().toLowerCase();
       if (type.equals("history")){
           return new HistoryCalculator(performance, play);
       }
       else if (type.equals("comedy")){
           return new ComedyCalculator(performance, play);
       }
       else if (type.equals("pastoral")){
           return new PastoralCalculator(performance, play);
       }
       else if (type.equals("tragedy")){
           return new TragedyCalculator(performance, play);
       }
       else{
           throw new IllegalArgumentException("Unknown performance type: " + type);
       }
   }

}
