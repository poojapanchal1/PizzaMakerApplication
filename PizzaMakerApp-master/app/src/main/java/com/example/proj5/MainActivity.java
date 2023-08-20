package com.example.proj5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

/**
 * This class holds the main interface of the application. This class makes sure that the customer's
 * phone number is valid before they choose their desired pizza or view orders. They can access
 * multiple functions of the application including the customization of the Deluxe Pizza, Hawaiian
 * Pizza, and Pepperoni Pizza, as well as the store orders and current order.
 * @author Krish Govind, Pooja Panchal
 */
public class MainActivity extends AppCompatActivity {
    private static final int MAX_PHONE_NUM_DIGITS = 10;
    private static final int INIT_INDEX = -1;
    private static final int FIRST_INDEX = -1;
    private static final int SECOND_INDEX = -1;
    private static final int REQUEST_CODE_ONE = 1;
    private static final int REQUEST_CODE_TWO = 2;
    private static final int REQUEST_CODE_THREE = 3;
    private static final int EMPTY_LENGTH = 0;
    private static final int BREAK_VAL = 1000;

    public ImageButton hawaiianPizza, deluxePizza, storeOrderButton, pepperoniPizza, orderPizzas;
    public TextView customerText, titleOfPizzeria;
    public TextInputEditText textInputEditText; //Edit Text
    public TextInputLayout textInputLayout;
    public ArrayList<Order> customerOrders = new ArrayList<Order>();
    public ArrayList<Order> storeOrders = new ArrayList<Order>();
    public String phoneNumber;

    /**
     * This method initializes the main screen, given the saved instances from the other views.
     * This sets up the buttons and displays the view.
     * @param savedInstanceState the bundle of the saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        MainActivity copyOfActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pepperoniPizzaButton();
        deluxePizzaButton();
        hawaiianPizzaButton();
        placeOrderButton();
        storeOrderButton();
    }

    /**
     * This method passes the current orders to the store orders, checking if the user already placed
     * an order. It also displays the store orders screen if applicable.
     */
    public void storeOrderButton() {
        ImageButton btnAdd = (ImageButton)findViewById(R.id.storeOrderButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkPhoneNum() == null){
                    return;
                }
                if (phoneNumber.length() != MAX_PHONE_NUM_DIGITS) {
                    invalidPhoneNumToast();
                } else {
                    if(storeOrders.isEmpty()){
                        String err = "There are no store orders currently!";
                        Toast toast = Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        Intent myIntent = new Intent(MainActivity.this, StoreOrderActivity.class);
                        myIntent.putExtra("storeOrders", storeOrders);
                        MainActivity.this.startActivityForResult(myIntent, REQUEST_CODE_THREE);
                    }
                }
            }
        });
    }

    /**
     * This method checks if the entered phone number is valid.
     * @return the phone number if it is valid, null otherwise.
     */
    public String checkPhoneNum(){
        TextInputEditText textInputEditText = findViewById(R.id.textInputEditText);
        phoneNumber = textInputEditText.getEditableText().toString();
        try {
            String tempNum = String.valueOf(Double.parseDouble(phoneNumber));
            return phoneNumber;
        } catch (Exception e) {
            invalidPhoneNumToast();
            return null;
        }
    }

    /**
     * This method sends the customer orders, phone numebr, and store orders to the order activity.
     * This also checks if the phone number is valid beforehand, and displays the order screen.
     */
    public void placeOrderButton() {
        ImageButton btnAdd = (ImageButton)findViewById(R.id.orderPizzas);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkPhoneNum() == null){
                    return;
                }
                if (phoneNumber.length() != MAX_PHONE_NUM_DIGITS) {
                    invalidPhoneNumToast();
                } else {
                    Intent myIntent = new Intent(MainActivity.this, CurrentOrderActivity.class);
                    myIntent.putExtra("customerOrders", customerOrders);
                    myIntent.putExtra("phoneNumber", phoneNumber);
                    myIntent.putExtra("storeOrders", storeOrders);
                    MainActivity.this.startActivityForResult(myIntent, REQUEST_CODE_TWO);
                    //printCustomerOrders();
                }
            }
        });
    }

    /**
     * This displays an "Invalid phone number" toast message.
     */
    public void invalidPhoneNumToast(){
        String err = "Invalid phone number!";
        Toast toast = Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * This method sends the customer orders array, phone number, and pepperoni pizza type to the
     * pizza activity, and displays the customization screen.
     */
    public void pepperoniPizzaButton(){
        ImageButton btnAdd = (ImageButton)findViewById(R.id.pepperoniPizza);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkPhoneNum() == null){
                    return;
                }
                if (phoneNumber.length() != MAX_PHONE_NUM_DIGITS) {
                    invalidPhoneNumToast();
                } else {
                    Intent myIntent = new Intent(MainActivity.this, PizzaActivity.class);
                    myIntent.putExtra("customerOrders", customerOrders);
                    myIntent.putExtra("phoneNumber", phoneNumber);
                    myIntent.putExtra("pizzaType", "Pepperoni");
                    MainActivity.this.startActivityForResult(myIntent, REQUEST_CODE_ONE);
                    //printCustomerOrders();
                }

            }
        });
    }

    /**
     * This method sends the customer orders array, phone number, and deluxe pizza type to the
     * deluxe activity, and displays the customization screen.
     */
    public void deluxePizzaButton(){
        ImageButton btnAdd = (ImageButton)findViewById(R.id.deluxePizza);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkPhoneNum() == null){
                    return;
                }
                if (phoneNumber.length() != MAX_PHONE_NUM_DIGITS) {
                    invalidPhoneNumToast();
                } else {
                    Intent myIntent = new Intent(MainActivity.this, PizzaActivity.class);
                    myIntent.putExtra("customerOrders", customerOrders);
                    myIntent.putExtra("phoneNumber", phoneNumber);
                    myIntent.putExtra("pizzaType", "Deluxe");
                    MainActivity.this.startActivityForResult(myIntent, REQUEST_CODE_ONE);
                    //printCustomerOrders();
                }
            }
        });
    }

    /**
     * This method sends the customer orders array, phone number, and hawaiian pizza type to the
     * hawaiian activity, and displays the customization screen.
     */
    public void hawaiianPizzaButton(){
        ImageButton btnAdd = (ImageButton)findViewById(R.id.hawaiianPizza);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkPhoneNum() == null){
                    return;
                }
                if (phoneNumber.length() != MAX_PHONE_NUM_DIGITS) {
                    invalidPhoneNumToast();
                } else {
                    Intent myIntent = new Intent(MainActivity.this, PizzaActivity.class);
                    myIntent.putExtra("customerOrders", customerOrders);
                    myIntent.putExtra("phoneNumber", phoneNumber);
                    myIntent.putExtra("pizzaType", "Hawaiian");
                    MainActivity.this.startActivityForResult(myIntent, REQUEST_CODE_ONE);
                    //printCustomerOrders();
                }
            }
        });
    }

    /*private void printCustomerOrders(){
        for(int i = 0; i < customerOrders.size(); i++){
            System.out.println("first part" + customerOrders.get(i).getPhoneNum() + " ");
            for(int j = 0; j < customerOrders.get(i).getOrderList().size(); j++){
                System.out.println("second line" + customerOrders.get(i).getOrderList().get(j).toString());
            }
        }
    } */

    /**
     * This method is the way we are able to get data from one activity to another.
     * This method is called when an activity is finished, once it is finished we are able to
     * send the data back using the intent, and able to detect when something goes wrong
     * through the error codes.
     * @param requestCode This is to detect which activity we used
     * @param resultCode The result code is the result of the activity, whether or not it was proper
     * @param data The data that is being sent back to the main activity.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ONE) {
            if(resultCode == RESULT_OK) {
                String pizzaAdded = data.getStringExtra("pizzaName");
                String extractType = extractType(pizzaAdded);
                String extractAmount = extractAmount(pizzaAdded);
                ArrayList<String> extractToppings = extractToppings(pizzaAdded);
                String extractSize = extractSize(pizzaAdded);
                Pizza convertToObject = makePizza(extractType, extractToppings, extractSize);
                addToCustomerOrder(convertToObject);
            }
        }
        if (requestCode == REQUEST_CODE_TWO) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> pizzaDeleted = data.getStringArrayListExtra("dataRemoved");
                ArrayList<String> pizzaOrdered = data.getStringArrayListExtra("dataPlaced");
                if(!(pizzaDeleted.isEmpty() && pizzaOrdered.isEmpty())){
                    updateCustomerAndStoreOrders(pizzaOrdered, pizzaDeleted, checkPhoneNum());
                }
            }
        }
        if(requestCode == REQUEST_CODE_THREE){
            if(resultCode == RESULT_CANCELED ){
                storeOrders = new ArrayList<Order>();
            }
            if(resultCode == RESULT_OK){
                ArrayList<String> removedStore = data.getStringArrayListExtra("cancelledOrders");
                for(int i = 0; i < removedStore.size(); i ++){
                    for(int j = 0; j < storeOrders.size(); j ++){
                        if(removedStore.get(i).equals(storeOrders.get(j).getPhoneNum())){
                            storeOrders.remove(storeOrders.get(j));
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * This method updates the customer and store order arrays when an order or customer is removed or added.
     * @param pizzaOrdered the pizza ordered by the customer.
     * @param pizzaDeleted the pizza deleted by the customer.
     * @param checkedPhoneNum the phone number of the customer.
     */
    private void updateCustomerAndStoreOrders(ArrayList<String> pizzaOrdered, ArrayList<String> pizzaDeleted, String checkedPhoneNum) {
        int indexOfCustomer = INIT_INDEX;
        for(int i = 0; i < customerOrders.size(); i ++){
            if(checkedPhoneNum.equals(customerOrders.get(i).getPhoneNum())){
                indexOfCustomer = i;
                i += customerOrders.size();
            }
        }

        for(int i = 0; i < pizzaDeleted.size(); i ++){
            for(int j = 0; j < customerOrders.get(indexOfCustomer).getOrderList().size(); j ++){
                if(customerOrders.get(indexOfCustomer).getOrderList().get(j).toString().equals(pizzaDeleted.get(i).toString())){
                    customerOrders.get(indexOfCustomer).getOrderList().remove(j);
                    break;
                }
            }
        }
        ArrayList<Pizza> a = new ArrayList<Pizza>();
        if(!pizzaOrdered.isEmpty()){
            for(int i = 0; i < pizzaOrdered.size(); i ++){
                for(int j = 0; j < customerOrders.get(indexOfCustomer).getOrderList().size(); j ++){
                    if(customerOrders.get(indexOfCustomer).getOrderList().get(j).toString().equals(pizzaOrdered.get(i).toString())){
                        a.add(customerOrders.get(indexOfCustomer).getOrderList().get(j));
                        break;
                    }
                }
            }
            customerOrders.get(indexOfCustomer).setOrderList(a);
            storeOrders.add(customerOrders.get(indexOfCustomer));
            customerOrders.remove(indexOfCustomer);
        }
        else{
            String err = "There are no pizzas to be ordered, please add pizzas to your order";
            Toast toast = Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * This method adds a pizza to the customer's order.
     * @param convertToObject the pizza to add to the order.
     */
    private void addToCustomerOrder(Pizza convertToObject) {
        TextInputLayout textInputLayout = findViewById(R.id.textInputLayout);
        String text = textInputLayout.getEditText().getText().toString();
        int phoneNumInCustomerOrders = INIT_INDEX;
        for(int i = 0; i < customerOrders.size(); i ++){
            if(text.equals(customerOrders.get(i).getPhoneNum())){
                phoneNumInCustomerOrders = i;
                i += customerOrders.size();
            }
        }
        if(phoneNumInCustomerOrders == INIT_INDEX){
            Order orderAdded = new Order();
            orderAdded.setPhoneNum(text);
            orderAdded.getOrderList().add(convertToObject);
            customerOrders.add(orderAdded);
        }
        else{
            customerOrders.get(phoneNumInCustomerOrders).getOrderList().add(convertToObject);
        }
    }

    /**
     * Creates the pizza specified by the customer after their customization.
     * @param extractType the user selected pizza type.
     * @param extractToppings the list of toppings selected by the user.
     * @param extractSize the user selected size of the pizza.
     * @return the pizza that the user created.
     */
    private Pizza makePizza(String extractType, ArrayList<String> extractToppings, String extractSize) {
        Pizza newPizza = null;
        if(extractType.equals("Pepperoni")){
            newPizza = new Pepperoni();
        }
        else if(extractType.equals("Hawaiian")){
            newPizza = new Hawaiian();
        }
        else{
            newPizza = new Deluxe();
        }
        if(extractSize.equals("Small")){
            newPizza.setSize(Size.Small);
        }
        else if(extractSize.equals("Medium")){
            newPizza.setSize(Size.Medium);
        }
        else{
            newPizza.setSize(Size.Large);
        }
        addToppings(newPizza, extractToppings);
        return newPizza;
    }

    /**
     * This method adds the user selected toppings to the pizza.
     * @param newPizza the pizza to add toppings to.
     * @param extractToppings the ArrayList to retrieve the toppings from.
     */
    private void addToppings(Pizza newPizza, ArrayList<String> extractToppings) {
        for(int i = 0; i < extractToppings.size(); i ++){
            if(extractToppings.get(i).equals("Pepperoni")){
                newPizza.toppings.add(Topping.Pepperoni);
            }
            if(extractToppings.get(i).equals("GreenPeppers")){
                newPizza.toppings.add(Topping.GreenPeppers);
            }
            if(extractToppings.get(i).equals("Pineapple")){
                newPizza.toppings.add(Topping.Pineapple);
            }
            if(extractToppings.get(i).equals("Tomatoes")){
                newPizza.toppings.add(Topping.Tomatoes);
            }
            if(extractToppings.get(i).equals("Chicken")){
                newPizza.toppings.add(Topping.Chicken);
            }
            if(extractToppings.get(i).equals("Ham")){
                newPizza.toppings.add(Topping.Ham);
            }
            if(extractToppings.get(i).equals("Mushrooms")){
                newPizza.toppings.add(Topping.Mushrooms);
            }
            if(extractToppings.get(i).equals("Olives")){
                newPizza.toppings.add(Topping.Olives);
            }
            if(extractToppings.get(i).equals("Onions")){
                newPizza.toppings.add(Topping.Onions);
            }
            if(extractToppings.get(i).equals("Spinach")){
                newPizza.toppings.add(Topping.Spinach);
            }
        }
    }

    /**
     * This method retrieves the size of the pizza the user customised.
     * @param pizzaAdded the pizza the customer added to their order.
     * @return the size of the customer's pizza.
     */
    private String extractSize(String pizzaAdded) {
        int firstCommaIndex = FIRST_INDEX;
        for(int i = pizzaAdded.length() - 1; i > EMPTY_LENGTH; i --){
            if(pizzaAdded.charAt(i) == ','){
                firstCommaIndex = i;
                i -= BREAK_VAL;
            }
        }
        int secondCommaIndex = SECOND_INDEX;
        for(int i = firstCommaIndex - 1; i > EMPTY_LENGTH; i --){
            if(pizzaAdded.charAt(i) == ','){
                secondCommaIndex = i;
                i -= BREAK_VAL;
            }
        }
        return pizzaAdded.substring(secondCommaIndex + 2, firstCommaIndex);
    }

    /**
     * This method gets the customer selected toppings and adds them to an ArrayList of toppings.
     * @param pizzaAdded the pizza to get the toppings from
     * @return the ArrayList of toppings.
     */
    private ArrayList<String> extractToppings(String pizzaAdded) {
        int indexOfFirstBracket = FIRST_INDEX;
        int indexOfSecondBracket = SECOND_INDEX;
        for(int i = 0; i < pizzaAdded.length(); i ++){
            if(pizzaAdded.charAt(i) == '['){
                indexOfFirstBracket = i;
                i += pizzaAdded.length();
            }
        }
        for(int i = pizzaAdded.length() - 1; i > EMPTY_LENGTH; i --){
            if(pizzaAdded.charAt(i) == ']'){
                indexOfSecondBracket = i;
                i -= BREAK_VAL;
            }
        }
        int left = indexOfFirstBracket + 1;
        int right = indexOfFirstBracket + 2;
        ArrayList<String> toppings = new ArrayList<String>();
        for(int i = indexOfFirstBracket; i < indexOfSecondBracket + 2; i ++){
            if(pizzaAdded.charAt(right) == ','){
                toppings.add(pizzaAdded.substring(left, right));
                right = right + 3;
                left = right -1;
            }
            else if(pizzaAdded.charAt(right) == ']'){
                toppings.add(pizzaAdded.substring(left, right));
                i += BREAK_VAL;
            }
            else{
                right ++;
            }
        }
        return toppings;
    }

    /**
     * This method retrieves the subtotal of the pizza.
     * @param pizzaAdded the pizza to get the cost from.
     * @return the string representation of the cost of the pizza.
     */
    private String extractAmount(String pizzaAdded) {
        int firstDollarIndex = FIRST_INDEX;
        for(int i = pizzaAdded.length() - 1; i > EMPTY_LENGTH; i --){
            if(pizzaAdded.charAt(i) == '$'){
                firstDollarIndex = i;
                i -= BREAK_VAL;
            }
        }
        return pizzaAdded.substring(firstDollarIndex + 1);
    }

    /**
     * This method gets the type of pizza the user chose to customize.
     * @param pizzaAdded the pizza the customer added to their order.
     * @return the type of pizza added to the order.
     */
    private String extractType(String pizzaAdded) {
        int indexOfFirstSpace = FIRST_INDEX;
        for(int i = 0; i < pizzaAdded.length(); i ++){
            if(pizzaAdded.charAt(i) == ' '){
                indexOfFirstSpace = i;
                i += pizzaAdded.length();
            }
        }
        return pizzaAdded.substring(0, indexOfFirstSpace);
    }
}
