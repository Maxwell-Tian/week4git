package edu.csc207.fall2024;

/**
 *  Representing a performance.
 */
// previous checkstyle error: missing the javadoc on the top of this line!!!
public class Performance {
    private String playID;
    private int audience;

    public Performance(String playID, int audience) {
        this.playID = playID;
        this.audience = audience;
    }

    public int getAudience() {
        return audience;
    }

    public String getPlayID() {
        return playID;
    }
}
