package com.example.proj5;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Pizza class defines common data items and operations for all pizza instances.
 * Each pizza must have specific toppings and a size. This identifies the type of pizza
 * for an order.
 * @author Pooja Panchal
 */
public abstract class Pizza implements Serializable {
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;

    /**
     * Calculates the price of a pizza
     * @return the price of a pizza.
     */
    public abstract double price();

    /**
     * The getter method of the size of the pizza
     * @return the size of the pizza as an ENUM
     */
    public Size getSize(){
        return this.size;
    }

    /**
     * The setter method of the size of the pizza
     * @param size The size of the pizza as an ENUM
     */
    public void setSize(Size size){
        this.size = size;
    }

    /**
     * A method specifically for a textual represntation for the export() method for a File.
     * This method returns particular aspects of the pizza such as the type, toppings and size.
     * @return A string represenation for a pizza object.
     */
    public abstract String exportString();

}
