package com.example.android.justjavacafe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private int price = 5;
    private int quantity = 1;
    private boolean addCream = false;
    private boolean addChocolate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void check(View view) {
        findViewById(R.id.cream);
        addCream = true;
    }

    public void check2(View view) {
        findViewById(R.id.chocolate);
        addChocolate = true;
    }


    public void submitOrder(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        String typedName = name.getText().toString();
        Price();
        calculatePrice();
        String priceMessage = createOrderSummary(quantity, typedName, calculatePrice());
        createEmail(priceMessage, typedName);
    }


    public int Price() {
        if (addCream  && !addChocolate) {
            price = 7;
        } else if (!addCream && addChocolate ) {
            price = 6;
        } else if (addCream && addChocolate) {
            price = 8;
        } else {
            price = 5;
        }
        return price;
    }

    private int calculatePrice() {
        return quantity * price;
    }


    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.more_than_100_coffees), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }


    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.less_than_1_coffee), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }


    private String createOrderSummary(int quantity, String typedName, int totalPrice) {
        String priceMessage = getString(R.string.mName) + typedName;
        priceMessage += "\n" + getString(R.string.mQuantity) + quantity;
        priceMessage += "\n" + getString(R.string.mAddCream) + addCream;
        priceMessage += "\n" + getString(R.string.mAddChoco) + addChocolate;
        priceMessage += "\n" + getString(R.string.mPrice1Cup) + price + ")";
        priceMessage += "\n" + getString(R.string.mTotal) + totalPrice;
        priceMessage += "\n" + getString(R.string.mThank_you);
        return priceMessage;
    }


    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void createEmail(String priceMessage, String typedName) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_for) + typedName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}