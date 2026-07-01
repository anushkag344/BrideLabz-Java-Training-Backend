package com.greet.service;
import com.greet.model.Greeting;
/**
 * Service interface for greeting operations.
 * Defines the contract that any greeting implementation must follow.
 spring-greeting-webapp-plan-solution.md 2026-06-21
 10 / 23
 */
public interface GreetingService {
    /**
     * Generates a greeting for the given user name.
     *
     * @param name the name of the user to greet
     * @return a Greeting object containing the personalized message
     */
    Greeting greet(String name);
}