package com.example.alex.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


// This app displays an order form to order coffee.

public class MainActivity extends AppCompatActivity {
    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // This method is called when the plus botton is clicked.

    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot add more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method eary because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    // This method is called when the minus botton is clicked.

    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    // This method is called when the order button is clicked.
    public void submitOrder(View view) {
        //User input his name
        EditText nameField = findViewById(R.id.name);
        String value = nameField.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.notify_me_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolate = findViewById(R.id.notify_me_checkbox2);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, value);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: vasilenco.iulia@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Jusr order a coffee " + value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Price of one cup of tee
        int basePrice = 5;

        // Add 1$ if the user wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        // Add 2$ if the user wants chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        // Calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @param price           of the order
     * @return text summary
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = "Name: " + name +
                "\nAdd whipped cream? " + addWhippedCream +
                "\nAdd chocolate? " + addChocolate +
                "\nQuantity: " + quantity +
                "\nTotal: $" + price + getString(R.string.thank_you);
        return priceMessage;
    }

    // This method displays the given quantity value on the screen.

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}