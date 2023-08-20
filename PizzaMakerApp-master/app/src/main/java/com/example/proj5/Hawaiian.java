package com.example.proj5;

import java.io.Serializable;

/**
 * A Hawaiian pizza as extended by a pizza.
 * This class contains specific data and methods that are particular to this class.
 * Operations relate to Hawaiian class and textual representation of the object.
 * @author Pooja Panchal
 */
public class Hawaiian extends Pizza implements Serializable {
    private static double INITAL_PRICE = 0;
    private static int INITIAL_TOPPINGS = 2;
    private static int NO_TOPPINGS = 0;
    private static double EXTRA_TOPPING_RATE = 1.49;
    private static double SMALL_BASE_PRICE = 10.99;
    private static double MED_BASE_PRICE = 12.99;
    private static double LARGE_BASE_PRICE = 14.99;

    /**
     * This method calculates the price for a pepperoni pizza.
     * This includes the size and toppings.
     * @return the price of a pepperoni pizza with its customization options
     */
    @Override
    public double price() {
        double price = INITAL_PRICE;
        int extraToppings = toppings.size() - INITIAL_TOPPINGS;
        if (extraToppings < NO_TOPPINGS){
            price = INITAL_PRICE;
        } else {
            price = extraToppings * EXTRA_TOPPING_RATE;
        }
        if (size == Size.Small){
            price += SMALL_BASE_PRICE;
        } else if (size == Size.Medium){
            price += MED_BASE_PRICE;
        } else {
            price += LARGE_BASE_PRICE;
        }
        return price;
    }
    /**
     * This method returns the string representation of a hawaiian pizza.
     * @return The string representation of a hawaiian pizza, inclusive of its size and prize excluding tax.
     */
    @Override
    public String toString(){
        return "Hawaiian Pizza, " + toppings + "," + getSize() + ", $" + String.valueOf(price());
    }
    /**
     * This method is only towards the export() method for a textual representation of a string.
     * Returns aspects of the pizza such as the type of pizza, toppings and size.
     * @return A string representation of a pepperoni pizza not including the price of the pizza.
     */
    public String exportString(){
        return "Hawaiian Pizza " + toppings + ", " + getSize();
    }
}
