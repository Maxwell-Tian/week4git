package edu.csc207.fall2024;

/**
 *  Representing a play.
 */
public class Play {

    private String name;
    private String type;

    public Play(String name, String type) {
        this.name = name;
        this.type = type;
    }
    // previous checkstyle error: there should be an empty line between each method!!!

    public String getName() {
        return name;
    }

    public String getType() {
        // previous checkstyle error: there should be a space between getType() and '{'
        return type;
    }
}
