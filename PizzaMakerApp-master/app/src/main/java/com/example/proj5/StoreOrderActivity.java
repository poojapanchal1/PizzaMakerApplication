package com.example.proj5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * This class handles the stores order operations, specifically from the store orders button.
 * This class shows the customers who have placed an order (only 1), and their order total.
 * Below the total is a display of all of the pizzas that they have ordered.
 * Users are able to cancel an order that they desire, and it removes their phone number from
 * the list of orders.
 * @author Krish Govind, Pooja Panchal
 */
public class StoreOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final double TAX_AMOUNT = 0.06625;
    public static final int STARTING_AMOUNT = 0;

    public TextView storeOrderText, orderTotalText, storeOrderTotalAmount;
    public Spinner phoneNumbers;
    public ListView storeOrderList;
    public Button cancelOrderButton;
    public ArrayList<Order> storeOrders;
    public ArrayList<String> cancelledOrders = new ArrayList<String>();
    public int indexSelected;
    public Intent savedState;

    /**
     * This method initializes the store orders screen, displaying the phone number and details
     * of the corresponding order.
     * @param savedInstanceState the bundle of the saved state.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        Intent intent = getIntent();
        savedState = intent;
        storeOrders = (ArrayList<Order>) intent.getSerializableExtra("storeOrders");
        initSpinner();
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpCancelOrderButton();
    }

    /**
     * Defines the action to cancel an order from the list when the cancel order button is clicked.
     */
    private void setUpCancelOrderButton() {
        Button cancelOrder = (Button) findViewById(R.id.cancelOrderButton);
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelledOrders.add(storeOrders.get(indexSelected).getPhoneNum());
                storeOrders.remove(storeOrders.get(indexSelected));
                initSpinner();
            }
        });
    }

    /**
     * This method preserves the state of the screen and calls finish() to save changes.
     * @return true if the state was saved
     */
    @Override
    public boolean onSupportNavigateUp() {
        this.savedState.putStringArrayListExtra("cancelledOrders", cancelledOrders);
        setResult(RESULT_OK, savedState);
        finish();
        return true;
    }

    /**
     * Initializes the spinner of customers with their phone numbers.
     */
    public void initSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.phoneNumbers);
        ArrayList<String> storeOrderNumbers = new ArrayList<String>();
        for(int i = 0; i < storeOrders.size(); i ++){
            storeOrderNumbers.add(storeOrders.get(i).getPhoneNum());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                storeOrderNumbers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(!storeOrders.isEmpty()) {
            spinner.setAdapter(arrayAdapter);
        }
        else{
            setResult(RESULT_CANCELED, savedState);
            finish();
        }
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * Displays the order of the selected customer from the spinner and the order total.
     * @param parent the AdapterView for the spinner.
     * @param view the view to find.
     * @param indexOfCustomer the position of the item selected in the Adapter.
     * @param id the id of the view to find.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int indexOfCustomer, long id) {
        ListView list = (ListView) findViewById(R.id.storeOrderList);
        ArrayList<Pizza> storeCustomerPizzaOrder = storeOrders.get(indexOfCustomer).getOrderList();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                storeCustomerPizzaOrder);
        list.setAdapter(arrayAdapter);
        setListView(indexOfCustomer);
        setUpTotalAmount(indexOfCustomer);
        indexSelected = indexOfCustomer;
    }

    /**
     * Displays the total cost of the order for the selected customer.
     * @param indexOfCustomer the index of the customer in the spinner to find the total for.
     */
    @SuppressLint("DefaultLocale")
    private void setUpTotalAmount(int indexOfCustomer) {
        TextView totalAmount = (TextView) findViewById(R.id.storeOrderTotalAmount);
        double amount = STARTING_AMOUNT;
        if(storeOrders.isEmpty()){
            totalAmount.setText(String.format("%,.2f", amount));
        }
        else{
            for(int i = 0; i < storeOrders.get(indexOfCustomer).getOrderList().size(); i ++){
                amount += storeOrders.get(indexOfCustomer).getOrderList().get(i).price();
            }
            amount += amount * TAX_AMOUNT;
            totalAmount.setText(String.format("%,.2f", amount));
        }
    }

    /**
     * Sets up the ListView of the pizzas in a customer's order.
     * @param indexOfCustomer the index of the customer in the spinner to display the order for.
     */
    private void setListView(int indexOfCustomer) {
        ListView list = (ListView) findViewById(R.id.storeOrderList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    /**
     * Empty method for when nothing is selected in the spinner
     * @param parent the AdapterView for the spinner.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
