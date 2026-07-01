package com.greet.service;

import com.greet.model.Greeting;

/**
 * Implementation of the GreetingService interface.
 *
 * This bean is configured in applicationContext.xml with:
 * - Property injection for 'prefix'
 * - init-method="init"
 * - destroy-method="destroy"
 */
public class GreetingServiceImpl implements GreetingService {

    /**
     * The greeting prefix (e.g., Hello, Hi, Welcome).
     */
    private String prefix;

    // -------------------- Getter & Setter --------------------

    /**
     * Returns the greeting prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the greeting prefix.
     *
     * @param prefix the greeting prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    // -------------------- Lifecycle Methods --------------------

    /**
     * Called by Spring after bean creation.
     */
    public void init() {
        System.out.println("=== GreetingServiceImpl initialized ===");
        System.out.println("Prefix set to: " + prefix);
    }

    /**
     * Called by Spring before bean destruction.
     */
    public void destroy() {
        System.out.println("=== GreetingServiceImpl destroyed ===");
    }

    // -------------------- Business Logic --------------------

    /**
     * Generates a personalized greeting.
     *
     * @param name the user's name
     * @return Greeting object containing the message
     */
    @Override
    public Greeting greet(String name) {
        String message = prefix + ", " + name + "!";
        System.out.println("GreetingServiceImpl.greet() -> " + message);
        return new Greeting(message);
    }
}