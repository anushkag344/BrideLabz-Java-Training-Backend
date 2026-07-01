package com.greet.model;

/**
 * Model class that holds the greeting message.
 * This is a simple POJO (Plain Old Java Object).
 */
public class Greeting {

    private String message;

    // -------------------- Constructors --------------------

    /**
     * Default no-argument constructor.
     * Required for Spring bean instantiation.
     */
    public Greeting() {
    }

    /**
     * Parameterized constructor.
     *
     * @param message the greeting message
     */
    public Greeting(String message) {
        this.message = message;
    }

    // -------------------- Getter & Setter --------------------

    /**
     * Returns the greeting message.
     *
     * @return the greeting message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the greeting message.
     *
     * @param message the greeting message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    // -------------------- toString --------------------

    @Override
    public String toString() {
        return "Greeting{message='" + message + "'}";
    }
}