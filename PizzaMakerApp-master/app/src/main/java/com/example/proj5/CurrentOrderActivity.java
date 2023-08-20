package com.example.proj5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * The CurrentOrderActivity controls the actions which take place when the Customer Order button is pressed.
 * This activity displays the list of pizzas the customer has selected, and shows the subtotal, sales tax,
 * and final total of the order. They have the ability to remove selected pizzas from their order and
 * finally, place their order.
 * @author Krish Govind, Pooja Panchal
 */
public class CurrentOrderActivity extends AppCompatActivity {
    private static final double TAX_RATE = 0.06625;
    private static final int EMPTY_LIST = 0;
    private static final double INIT_AMT = 0;
    private static final int INIT_INDEX = -1;
    private static final int ERR_INDEX = -1;

    public TextView pizzaOrder, subTotal, subTotalText, salesTax, salesTaxAmount, totalAmount,
            totalAmountText,orderPhoneNum;
    public Button removePizzaButton, placeOrderButton;
    public ListView orderListView;
    public double subTotalAmount;
    public double salesTaxPay;
    public double subTotalAndSalesTax;
    public int indexSelected;
    public ArrayList<String> dataPizzaRemoved = new ArrayList<String>();
    public ArrayList<String> dataPizzaPlaced = new ArrayList<String>();
    public Intent savedState;
    public ArrayList<Order> customerOrders;
    public ArrayList<Order> storeOrders;
    public int indexOfCustomer;
    public String phoneNumber;

    /**
     * This method initializes the order screen, given the instance created from the main view.
     * This sets up the phone number and shows the current pizzas in the order, as well as the totol.
     * @param savedInstanceState the bundle of the saved state.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        ListView list = (ListView) findViewById(R.id.orderListView);
        Intent intent = getIntent();
        savedState = intent;
        storeOrders = (ArrayList<Order>) intent.getSerializableExtra("storeOrders");
        ArrayList<Order> customerOrders = (ArrayList<Order>) intent.getSerializableExtra("customerOrders");
        this.customerOrders = customerOrders;
        String phoneNumber = intent.getStringExtra("phoneNumber");
        this.phoneNumber = phoneNumber;
        TextView num = (TextView) findViewById(R.id.orderPhoneNum);
        num.setText(phoneNumber);
        int indexOfCustomer = findIndexOfCustomer(customerOrders, phoneNumber);
        this.indexOfCustomer = indexOfCustomer;
        if (customerOrders.size() != EMPTY_LIST && indexOfCustomer != ERR_INDEX){
            setUpListView(customerOrders, list, indexOfCustomer);
            subTotalAmount = setUpSubTotal(customerOrders, indexOfCustomer);
            salesTaxPay = setUpSalesTax(subTotalAmount);
            subTotalAndSalesTax = setUpTotal(subTotalAmount, salesTaxPay);
            setListView(indexOfCustomer, customerOrders);
            setUpRemoveButton(customerOrders, indexOfCustomer, list);
        }
        setUpPlaceOrderButton();
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This method takes all of the pizzas that have been placed under the order and places the order to the
     * store. This includes the entire order which is exclusive to the customer's phone number.
     */
    private void setUpPlaceOrderButton() {
        Button btnAdd = (Button)findViewById(R.id.placeOrderButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(phoneNumberExists(storeOrders)){
                        Toast.makeText(CurrentOrderActivity.this,
                                "Phone number already corresponds to an existing order!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    for(int i = 0; i < customerOrders.get(indexOfCustomer).getOrderList().size(); i ++){
                        dataPizzaPlaced.add(customerOrders.get(indexOfCustomer).getOrderList().get(i).toString());
                    }
                    savedState.putStringArrayListExtra("dataRemoved", dataPizzaRemoved);
                    savedState.putStringArrayListExtra("dataPlaced", dataPizzaPlaced);
                    setResult(RESULT_OK, savedState);
                    finish();
                }
                catch(Exception ignored){
                }
            }
        });
    }

    /**
     * This method checks if the customer had already placed an order.
     * @param customerOrders the ArrayList of customer orders to look through.
     * @return true if the phone number exists, false otherwise.
     */
    private boolean phoneNumberExists(ArrayList<Order> customerOrders) {
        String num = phoneNumber;
        for(int i = 0; i < customerOrders.size(); i ++){
            if(num.equals(customerOrders.get(i).getPhoneNum())){
                return true;
            }
        }
        return false;
    }

    /**
     * This method preserves the state of the screen and calls finish() to save changes.
     * @return true if the state was saved
     */
    @Override
    public boolean onSupportNavigateUp() {
        try{
            this.savedState.putStringArrayListExtra("dataRemoved", dataPizzaRemoved);
            this.savedState.putStringArrayListExtra("dataPlaced", dataPizzaPlaced);
            setResult(RESULT_OK, this.savedState);
            finish();
            return true;
        }
        catch(Exception ignored){
            finish();
            return true;
        }
    }

    /**
     * Removes the pizza that the customer has selected to be removed.
     * @param customerOrders the ArrayList of customer orders to choose from.
     * @param indexOfCustomer the index of the customer in the list.
     * @param listView the ListView to update after the pizza has been removed.
     */
    private void setUpRemoveButton(ArrayList<Order> customerOrders, int indexOfCustomer, ListView listView) {
        Button btnAdd = (Button)findViewById(R.id.removePizzaButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPizzaRemoved.add(customerOrders.get(indexOfCustomer).getOrderList().get(indexSelected).toString());
                customerOrders.get(indexOfCustomer).getOrderList().remove(indexSelected);
                setUpListView(customerOrders, listView, indexOfCustomer);
                Toast.makeText(CurrentOrderActivity.this, "Successfully removed pizza.",
                        Toast.LENGTH_SHORT).show();
                subTotalAmount = setUpSubTotal(customerOrders, indexOfCustomer);
                salesTaxPay = setUpSalesTax(subTotalAmount);
                subTotalAndSalesTax = setUpTotal(subTotalAmount, salesTaxPay);
            }
        });
    }

    /**
     * Updates the list view after a selected pizza has been removed.
     * @param indexOfCustomer the index of the customer that has been changed.
     * @param customerOrders the ArrayList of customer orders to update.
     */
    private void setListView(int indexOfCustomer, ArrayList<Order> customerOrders) {
        ListView list = (ListView) findViewById(R.id.orderListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CurrentOrderActivity.this, "Selected Pizza: " +
                        customerOrders.get(indexOfCustomer).getOrderList().get(position).toString(),
                        Toast.LENGTH_LONG).show();
                indexSelected = position;
            }
        });
    }

    /**
     * Calculates and displays the total cost of the order.
     * @param subTotalAmount the subtotal cost to add.
     * @param salesTaxAmount the sales tax amount to add.
     * @return the total cost of the order.
     */
    @SuppressLint("DefaultLocale")
    private double setUpTotal(double subTotalAmount, double salesTaxAmount) {
        double total = subTotalAmount + salesTaxAmount;
        TextView sub = (TextView) findViewById(R.id.totalAmountText);
        sub.setText(String.format("%,.2f", total));
        return total;
    }

    /**
     * Calculates the sales tax amount for an order.
     * @param subTotalAmount the subtotal amount to multiply.
     * @return the sales tax amount for an order.
     */
    @SuppressLint("DefaultLocale")
    private double setUpSalesTax(double subTotalAmount) {
        double salesTax = subTotalAmount * TAX_RATE;
        TextView sub = (TextView) findViewById(R.id.salesTaxAmount);
        sub.setText(String.format("%,.2f", salesTax));
        return salesTax;
    }

    /**
     * Calculates the subtotal amount for an order.
     * @param customerOrders the ArrayList of customer orders.
     * @param indexOfCustomer the index of the customer to calculate the total for.
     * @return the subtotal amount for an order.
     */
    @SuppressLint("DefaultLocale")
    private double setUpSubTotal(ArrayList<Order> customerOrders, int indexOfCustomer) {
        ArrayList<Pizza> customerPizzaOrder = customerOrders.get(indexOfCustomer).getOrderList();
        TextView sub = (TextView) findViewById(R.id.subTotalText);
        double amount = INIT_AMT;
        for(int i = 0; i < customerPizzaOrder.size(); i ++){
            amount += customerPizzaOrder.get(i).price();
        }
        sub.setText(String.format("%,.2f", amount));
        return amount;
    }

    /**
     * Retrieves the index of a customer in the customer orders array.
     * @param customerOrders the ArrayList of customer orders to search from.
     * @param phoneNumber the phone number of the customer to look for.
     * @return the index of the customer in the arraylist.
     */
    private int findIndexOfCustomer(ArrayList<Order> customerOrders, String phoneNumber) {
        int indexOfCustomer = INIT_INDEX;
        for(int i = 0; i < customerOrders.size(); i ++){
            if(customerOrders.get(i).getPhoneNum().equals(phoneNumber)){
                indexOfCustomer = i;
                i += customerOrders.size();
            }
        }
        return indexOfCustomer;
    }

    /**
     * Initializes the ListView with the pizzas in the customer's order.
     * @param customerOrders the ArrayList of customer orders.
     * @param listView the ListView to initialize.
     * @param indexOfCustomer the index of the customer whose order is to be displayed.
     */
    public void setUpListView(ArrayList<Order> customerOrders, ListView listView, int indexOfCustomer) {
        if (customerOrders.size() != EMPTY_LIST){
            ArrayList<Pizza> customerPizzaOrder = customerOrders.get(indexOfCustomer).getOrderList();
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, customerPizzaOrder);
            listView.setAdapter(arrayAdapter);
        } else {
            return;
        }
    }
}
