package com.example.proj5;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is an Order object which stores customers phone numbers and
 * pizzas that they have ordered.
 * @author Pooja Panchal
 */
public class Order implements Serializable {
    private String phoneNum;
    private ArrayList<Pizza> orderList = new ArrayList<>();

    /**
     * Setter method for the phone number parameter
     * @param phoneNum the phone number which you would like to set the order to
     */
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    /**
     * Getter method for the phone number.
     * @return A string that returns the phone number for the specific order
     */
    public String getPhoneNum(){
        return this.phoneNum;
    }

    /**
     * An add method so that a pizza can be added to the arraylist of pizzas
     * @param pizza the pizza that you want to be added to the order
     */
    public void add(Pizza pizza){
        orderList.add(pizza);
    }

    /**
     * A getter method that gives the orderList. Used to access the instance variable
     * @return the arraylist of pizza for the specific order.
     */
    public ArrayList<Pizza> getOrderList(){
        return orderList;
    }

    public void setOrderList(ArrayList<Pizza> orderList){
        this.orderList = orderList;
    }
}
