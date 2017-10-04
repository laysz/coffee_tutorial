package com.example.android.justjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */


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

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    boolean isWhipppedCream = false;
    boolean isChocolate = false;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {

        if (quantity == 10) {
            Toast.makeText(this, "Quantity cannot be above 10", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order");
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(this, "Quantity cannot be below 1", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        display(quantity);


    }

    public String createOrderSummary() {
        updateVariablesFromScreen();
        String final_summary = getString(R.string.order_summary_name, name) + "\n";
        final_summary +=  "\n" + getString(R.string.order_summary_whipped_cream, isWhipppedCream) + "\n";
        final_summary +=  "\n" + getString(R.string.order_summary_chocolate, isChocolate) + "\n";
        final_summary +=  "\n" + getString(R.string.order_summary_quantity, quantity) + "\n";
        final_summary +=  "\n" + getString(R.string.order_summary_price, calculatePrice()) + "\n";

        return final_summary;

    }

    private void updateVariablesFromScreen() {
        CheckBox checkbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        isWhipppedCream = checkbox.isChecked();
        checkbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        isChocolate = checkbox.isChecked();
        EditText editText = (EditText) findViewById(R.id.edit_name);
        name = editText.getText().toString();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
        Log.v("MainActivity", "The price is " + calculatePrice());

    }

    private String calculatePrice() {
        int toppings = ((isChocolate) ? 2 : 0) + ((isWhipppedCream) ? 1 : 0);
        return "" + quantity * (5 + toppings);
    }

}
