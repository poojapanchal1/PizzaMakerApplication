package com.example.proj5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * This class handles the pizza customization options, dependent on the pizza flavor chosen.
 * This class presents the customer with the size options for their selected pizza.
 * Once the size is chosen, they can choose to add additional toppings to the store selected ones
 * or remove and add any other toppings from the provided list. Users can view a running total
 * of their customized pizza as they are making it, and place the order.
 * @author Krish Govind and Pooja Panchal
 */
public class PizzaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int MAX_TOPPINGS = 7;

    public TextView pizzaType;
    public ImageView imageView;
    public Spinner sizeSpinner;
    public CheckBox hamButton, chickenButton, pepperoniButton, greenPeppersButton, pineappleButton,
            olivesButton, mushroomsButton, onionsButton, spinachButton, tomatoesButton;
    public int numToppingsSelected = 0;
    public Pizza newPizza = null;
    public TextView totalDisplay;
    public Button addToOrderButton;
    public String phoneNumber = "";

    /**
     * This method initializes the pizza customization screen, depending on the type of pizza the user
     * has chosen, and specifies the action taken by the add to order button.
     * @param savedInstanceState the bundle of the saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        Intent intent = getIntent();
        String whatPizza = intent.getStringExtra("pizzaType");
        phoneNumber = intent.getStringExtra("phoneNumber");
        initSpinner();
        if (whatPizza.equals("Pepperoni")) {
            setImageTextPepperoni();
            setPepperoniToppings();
            newPizza = new Pepperoni();
            addTopping(Topping.Pepperoni);
        } else if (whatPizza.equals("Deluxe")) {
            setImageTextDeluxe();
            setDeluxeToppings();
            newPizza = new Deluxe();
            addDeluxeToppings();
        } else if(whatPizza.equals("Hawaiian")) {
            setImageTextHawaiian();
            setHawaiianToppings();
            newPizza = new Hawaiian();
            setPizzaHawaiianToppings();
        }
        setUpOnClick();
        ArrayList<Order> customerOrders = (ArrayList<Order>) intent.getSerializableExtra("customerOrders");
        Button btnAdd = (Button)findViewById(R.id.addToOrderButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String mainPizza = addPizza(customerOrders);
                intent.putExtra("pizzaName", mainPizza);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Initializes the pizza with the store-selected hawaiian toppings.
     */
    private void setPizzaHawaiianToppings() {
        addTopping(Topping.Pineapple);
        addTopping(Topping.Ham);
    }

    /**
     * This method preserves the state of the screen and calls finish() to save changes.
     * @return true if the state was preserved.
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /**
     * Initializes the pizza with the store-selected deluxe toppings.
     */
    private void addDeluxeToppings() {
        addTopping(Topping.Onions);
        addTopping(Topping.Olives);
        addTopping(Topping.GreenPeppers);
        addTopping(Topping.Mushrooms);
        addTopping(Topping.Tomatoes);
    }

    /**
     * The details of the pizza to add to the customer orders ArrayList.
     * @param customerOrders the ArrayList of customer orders to add a pizza to.
     * @return the details of the pizza to add.
     */
    public String addPizza(ArrayList<Order> customerOrders){
        return newPizza.toString();
    }

    /**
     * Initializes the ImageView and screen title to Pepperoni.
     */
    public void setImageTextPepperoni() {
        ImageView pizzaImage = (ImageView) findViewById(R.id.imageView);
        pizzaImage.setImageResource(R.mipmap.pepperoni);
        TextView type = (TextView) findViewById(R.id.pizzaType);
        type.setText(R.string.pepPizza);
    }

    /**
     * Initializes the ImageView and screen title to Deluxe.
     */
    public void setImageTextDeluxe() {
        ImageView pizzaImage = (ImageView) findViewById(R.id.imageView);
        pizzaImage.setImageResource(R.mipmap.deluxe);
        TextView deluxe = (TextView) findViewById(R.id.pizzaType);
        deluxe.setText(R.string.deluxePizza);
    }

    /**
     * Initializes the ImageView and screen title to Hawaiian.
     */
    public void setImageTextHawaiian() {
        ImageView pizzaImage = (ImageView) findViewById(R.id.imageView);
        pizzaImage.setImageResource(R.mipmap.ic_launcher);
        TextView hawaiian = (TextView) findViewById(R.id.pizzaType);
        hawaiian.setText(R.string.hawaiianPizza);
    }

    /**
     * Initializes the checkboxes to check the store-selected pepperoni topping options.
     */
    public void setPepperoniToppings() {
        CheckBox peppToppings = (CheckBox) findViewById(R.id.pineappleButton);
        peppToppings.setChecked(false);
        peppToppings = (CheckBox) findViewById(R.id.hamButton);
        peppToppings.setChecked(false);
        peppToppings = (CheckBox) findViewById(R.id.onionsButton);
        peppToppings.setChecked(false);
        peppToppings = (CheckBox) findViewById(R.id.olivesButton);
        peppToppings.setChecked(false);
        peppToppings = (CheckBox) findViewById(R.id.greenPeppersButton);
        peppToppings.setChecked(false);
        peppToppings = (CheckBox) findViewById(R.id.mushroomsButton);
        peppToppings.setChecked(false);
        peppToppings = (CheckBox) findViewById(R.id.tomatoesButton);
        peppToppings.setChecked(false);
        peppToppings = (CheckBox) findViewById(R.id.pepperoniButton);
        peppToppings.setChecked(true);
        peppToppings = (CheckBox) findViewById(R.id.spinachButton);
        peppToppings.setChecked(false);
        peppToppings = (CheckBox) findViewById(R.id.chickenButton);
        peppToppings.setChecked(false);
    }

    /**
     * Initializes the checkboxes to check the store-selected deluxe topping options.
     */
    public void setDeluxeToppings() {
        CheckBox deluxeToppings = (CheckBox) findViewById(R.id.onionsButton);
        deluxeToppings.setChecked(true);
        deluxeToppings = (CheckBox) findViewById(R.id.olivesButton);
        deluxeToppings.setChecked(true);
        deluxeToppings = (CheckBox) findViewById(R.id.greenPeppersButton);
        deluxeToppings.setChecked(true);
        deluxeToppings = (CheckBox) findViewById(R.id.mushroomsButton);
        deluxeToppings.setChecked(true);
        deluxeToppings = (CheckBox) findViewById(R.id.tomatoesButton);
        deluxeToppings.setChecked(true);
        deluxeToppings = (CheckBox) findViewById(R.id.hamButton);
        deluxeToppings.setChecked(false);
        deluxeToppings = (CheckBox) findViewById(R.id.pepperoniButton);
        deluxeToppings.setChecked(false);
        deluxeToppings = (CheckBox) findViewById(R.id.pineappleButton);
        deluxeToppings.setChecked(false);
        deluxeToppings = (CheckBox) findViewById(R.id.spinachButton);
        deluxeToppings.setChecked(false);
        deluxeToppings = (CheckBox) findViewById(R.id.chickenButton);
        deluxeToppings.setChecked(false);
    }

    /**
     * Initializes the checkboxes to check the store-selected hawaiian topping options.
     */
    public void setHawaiianToppings() {
        CheckBox hawaiianToppings = (CheckBox) findViewById(R.id.pineappleButton);
        hawaiianToppings.setChecked(true);
        hawaiianToppings = (CheckBox) findViewById(R.id.hamButton);
        hawaiianToppings.setChecked(true);
        hawaiianToppings = (CheckBox) findViewById(R.id.onionsButton);
        hawaiianToppings.setChecked(false);
        hawaiianToppings = (CheckBox) findViewById(R.id.olivesButton);
        hawaiianToppings.setChecked(false);
        hawaiianToppings = (CheckBox) findViewById(R.id.greenPeppersButton);
        hawaiianToppings.setChecked(false);
        hawaiianToppings = (CheckBox) findViewById(R.id.mushroomsButton);
        hawaiianToppings.setChecked(false);
        hawaiianToppings = (CheckBox) findViewById(R.id.tomatoesButton);
        hawaiianToppings.setChecked(false);
        hawaiianToppings = (CheckBox) findViewById(R.id.pepperoniButton);
        hawaiianToppings.setChecked(false);
        hawaiianToppings = (CheckBox) findViewById(R.id.spinachButton);
        hawaiianToppings.setChecked(false);
        hawaiianToppings = (CheckBox) findViewById(R.id.chickenButton);
        hawaiianToppings.setChecked(false);
    }

    /**
     * Initializes the functionalities of the individual topping buttons.
     */
    public void setUpOnClick() {
        setUpPineappleButton();
        setUpHamButton();
        setUpOnionsButton();
        setUpOlivesButton();
        setUpGreenPeppersButton();
        setUpMushroomsButton();
        setUpSpinachButton();
        setUpTomatoesButton();
        setUpChickenButton();
        setUpPepperoniButton();
    }

    /**
     * Adds or removes the topping when the pineapple topping option is either checked or unchecked.
     */
    private void setUpPineappleButton() {
        CheckBox pineapple = (CheckBox) findViewById(R.id.pineappleButton);
        pineapple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (pineapple.isChecked() == true){
                    addTopping(Topping.Pineapple);
                } else {
                    removeTopping(Topping.Pineapple);
                }
            }
        });
    }

    /**
     * Adds or removes the topping when the ham topping option is either checked or unchecked.
     */
    private void setUpHamButton() {
        CheckBox ham = (CheckBox) findViewById(R.id.hamButton);
        ham.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ham.isChecked() == true){
                    addTopping(Topping.Ham);
                } else {
                    removeTopping(Topping.Ham);
                }
            }
        });
    }

    /**
     * Adds or removes the topping when the onions topping option is either checked or unchecked.
     */
    private void setUpOnionsButton() {
        CheckBox onions = (CheckBox) findViewById(R.id.onionsButton);
        onions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (onions.isChecked() == true){
                    addTopping(Topping.Onions);
                } else {
                    removeTopping(Topping.Onions);
                }
            }
        });
    }

    /**
     * Adds or removes the topping when the olives topping option is either checked or unchecked.
     */
    private void setUpOlivesButton() {
        CheckBox olives = (CheckBox) findViewById(R.id.olivesButton);
        olives.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (olives.isChecked() == true){
                    addTopping(Topping.Olives);
                } else {
                    removeTopping(Topping.Olives);
                }
            }
        });
    }

    /**
     * Adds or removes the topping when the green peppers topping option is either checked or unchecked.
     */
    private void setUpGreenPeppersButton() {
        CheckBox greenPeppers = (CheckBox) findViewById(R.id.greenPeppersButton);
        greenPeppers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (greenPeppers.isChecked() == true){
                    addTopping(Topping.GreenPeppers);
                } else {
                    removeTopping(Topping.GreenPeppers);
                }
            }
        });
    }

    /**
     * Adds or removes the topping when the mushrooms topping option is either checked or unchecked.
     */
    private void setUpMushroomsButton() {
        CheckBox mushrooms = (CheckBox) findViewById(R.id.mushroomsButton);
        mushrooms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mushrooms.isChecked() == true){
                    addTopping(Topping.Mushrooms);
                } else {
                    removeTopping(Topping.Mushrooms);
                }
            }
        });
    }

    /**
     * Adds or removes the topping when the spinach topping option is either checked or unchecked.
     */
    private void setUpSpinachButton() {
        CheckBox spinach = (CheckBox) findViewById(R.id.spinachButton);
        spinach.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (spinach.isChecked() == true){
                    addTopping(Topping.Spinach);
                } else {
                    removeTopping(Topping.Spinach);
                }
            }
        });
    }

    /**
     * Adds or removes the topping when the tomatoes topping option is either checked or unchecked.
     */
    private void setUpTomatoesButton() {
        CheckBox tomatoes = (CheckBox) findViewById(R.id.tomatoesButton);
        tomatoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tomatoes.isChecked() == true){
                    addTopping(Topping.Tomatoes);
                } else {
                    removeTopping(Topping.Tomatoes);
                }
            }
        });
    }

    /**
     * Adds or removes the topping when the chicken topping option is either checked or unchecked.
     */
    private void setUpChickenButton() {
        CheckBox chicken = (CheckBox) findViewById(R.id.chickenButton);
        chicken.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chicken.isChecked() == true){
                    addTopping(Topping.Chicken);
                } else {
                    removeTopping(Topping.Chicken);
                }
            }
        });
    }

    /**
     * Adds or removes the topping when the pepperoni topping option is either checked or unchecked.
     */
    private void setUpPepperoniButton() {
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoniButton);
        pepperoni.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (pepperoni.isChecked() == true){
                    addTopping(Topping.Pepperoni);
                } else {
                    removeTopping(Topping.Pepperoni);
                }
            }
        });
    }

    /**
     * Adds the specified topping to the pizza if applicable, and displays the running total.
     * @param topping the topping to add to the pizza.
     */
    private void addTopping(Topping topping){
        numToppingsSelected = countToppings();
        if(numToppingsSelected == MAX_TOPPINGS){
            Toast toast=Toast.makeText(getApplicationContext(),"Cannot add more than 7 toppings!",
                    Toast.LENGTH_LONG);
            toast.show();
            greyOutCheckBoxes();
            return;
        }
        newPizza.toppings.add(topping);
        TextView price = (TextView) findViewById(R.id.totalDisplay);
        price.setText(String.format("%,.2f", newPizza.price()));
        enableCheckBoxes();
    }

    /**
     * Removes a topping from the pizza and displays the running total.
     * @param topping the topping to remove from the pizza.
     */
    private void removeTopping(Topping topping){
        newPizza.toppings.remove(topping);
        TextView price = (TextView) findViewById(R.id.totalDisplay);
        price.setText(String.format("%,.2f", newPizza.price()));
        enableCheckBoxes();
    }

    /**
     * Gets the count of how many toppings are checked.
     * @return the number of toppings selected by the user.
     */
    private int countToppings(){
        int count = 0;
        CheckBox topping = (CheckBox) findViewById(R.id.hamButton);
        if(topping.isChecked() == true){
            count ++;
        }
        topping = (CheckBox) findViewById(R.id.pepperoniButton);
        if(topping.isChecked() == true){
            count ++;
        }
        topping = (CheckBox) findViewById(R.id.pineappleButton);
        if(topping.isChecked() == true){
            count ++;
        }
        topping = (CheckBox) findViewById(R.id.greenPeppersButton);
        if(topping.isChecked() == true){
            count ++;
        }
        topping = (CheckBox) findViewById(R.id.mushroomsButton);
        if(topping.isChecked() == true){
            count ++;
        }
        topping = (CheckBox) findViewById(R.id.spinachButton);
        if(topping.isChecked() == true){
            count ++;
        }
        topping = (CheckBox) findViewById(R.id.tomatoesButton);
        if(topping.isChecked() == true){
            count ++;
        }
        topping = (CheckBox) findViewById(R.id.chickenButton);
        if(topping.isChecked() == true){
            count ++;
        }
        topping = (CheckBox) findViewById(R.id.olivesButton);
        if(topping.isChecked() == true){
            count ++;
        }
        topping = (CheckBox) findViewById(R.id.onionsButton);
        if(topping.isChecked() == true) {
            count ++;
        }
        return count;
    }

    /**
     * Sets all topping checkboxes to be enabled.
     */
    private void enableCheckBoxes(){
        CheckBox topping = (CheckBox) findViewById(R.id.hamButton);
        if(topping.isChecked() == false){
            topping.setEnabled(true);
        }
        topping = (CheckBox) findViewById(R.id.pepperoniButton);
        if(topping.isChecked() == false){
            topping.setEnabled(true);
        }
        topping = (CheckBox) findViewById(R.id.pineappleButton);
        if(topping.isChecked() == false){
            topping.setEnabled(true);
        }
        topping = (CheckBox) findViewById(R.id.greenPeppersButton);
        if(topping.isChecked() == false){
            topping.setEnabled(true);
        }
        topping = (CheckBox) findViewById(R.id.mushroomsButton);
        if(topping.isChecked() == false){
            topping.setEnabled(true);
        }
        topping = (CheckBox) findViewById(R.id.spinachButton);
        if(topping.isChecked() == false){
            topping.setEnabled(true);
        }
        topping = (CheckBox) findViewById(R.id.tomatoesButton);
        if(topping.isChecked() == false){
            topping.setEnabled(true);
        }
        topping = (CheckBox) findViewById(R.id.chickenButton);
        if(topping.isChecked() == false){
            topping.setEnabled(true);
        }
        topping = (CheckBox) findViewById(R.id.olivesButton);
        if(topping.isChecked() == false){
            topping.setEnabled(true);
        }
        topping = (CheckBox) findViewById(R.id.onionsButton);
        if(topping.isChecked() == false){
            topping.setEnabled(true);
        }
    }

    /**
     * Sets topping checkboxes to disabled if they are unchecked.
     */
    private void greyOutCheckBoxes(){
        CheckBox topping = (CheckBox) findViewById(R.id.hamButton);
        if(topping.isChecked() == false){
            topping.setEnabled(false);
        }
        topping = (CheckBox) findViewById(R.id.pepperoniButton);
        if(topping.isChecked() == false){
            topping.setEnabled(false);
        }
        topping = (CheckBox) findViewById(R.id.pineappleButton);
        if(topping.isChecked() == false){
            topping.setEnabled(false);
        }
        topping = (CheckBox) findViewById(R.id.greenPeppersButton);
        if(topping.isChecked() == false){
            topping.setEnabled(false);
        }
        topping = (CheckBox) findViewById(R.id.mushroomsButton);
        if(topping.isChecked() == false){
            topping.setEnabled(false);
        }
        topping = (CheckBox) findViewById(R.id.spinachButton);
        if(topping.isChecked() == false){
            topping.setEnabled(false);
        }
        topping = (CheckBox) findViewById(R.id.tomatoesButton);
        if(topping.isChecked() == false){
            topping.setEnabled(false);
        }
        topping = (CheckBox) findViewById(R.id.chickenButton);
        if(topping.isChecked() == false){
            topping.setEnabled(false);
        }
        topping = (CheckBox) findViewById(R.id.olivesButton);
        if(topping.isChecked() == false){
            topping.setEnabled(false);
        }
        topping = (CheckBox) findViewById(R.id.onionsButton);
        if(topping.isChecked() == false){
            topping.setEnabled(false);
        }
    }

    /**
     * Initializes the spinner of pizza size options.
     */
    public void initSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.sizeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sizes,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * Sets the pizza to the selected size when a size is chosen from the spinner.
     * @param parent the AdapterView for the spinner.
     * @param view the view to find.
     * @param position the position of the item selected in the Adapter.
     * @param id the id of the view to find.
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String size = parent.getItemAtPosition(position).toString();
        if (size.equals("Small")){
            newPizza.setSize(Size.Small);
        }
        if (size.equals("Medium")){
            newPizza.setSize(Size.Medium);
        }
        if (size.equals("Large")){
            newPizza.setSize(Size.Large);
        }
        TextView price = (TextView) findViewById(R.id.totalDisplay);
        price.setText(String.format("%,.2f", newPizza.price()));
    }

    /**
     * Empty method for when nothing is selected in the spinner
     * @param parent the AdapterView for the spinner.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    //can leave it empty
    }
}



