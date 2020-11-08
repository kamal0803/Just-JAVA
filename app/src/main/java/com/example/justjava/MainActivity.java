package com.example.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity=1,price;
    boolean isWhippedCream=false;
    boolean isChocolate=false;

    public void submitOrder(View view) {

        displayQuantity(quantity);
        int price = calculatePrice();
        String customerName = displayName();
        String message = orderSummary(price,customerName,isWhippedCream,isChocolate,quantity);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order Summary");
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private int calculatePrice(){
        int price = quantity*5;
        if(isWhippedCream==true)
            price = price + quantity;
        if(isChocolate==true)
            price = price + 2*quantity;
        return price;
    }
    public void incrementOrder(View view) {

        if(quantity==100)
        {
            Context context = getApplicationContext();
            CharSequence text = "No. of Coffees can't be more than 100";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        if(quantity<100)
            quantity++;

        displayQuantity(quantity);
    }

    public void decrementOrder(View view) {

        if(quantity==1)
        {
            Context context = getApplicationContext();
            CharSequence text = "No. of Coffees can't be less than 1";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        if(quantity>1)
            quantity--;

        displayQuantity(quantity);
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox)view).isChecked();

        switch (view.getId())
        {
            case R.id.checkbox_whippedCream:
                if(checked)
                    isWhippedCream=true;
                break;
            case R.id.checkbox_chocolate:
                if(checked)
                    isChocolate=true;
                break;
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private String orderSummary(int price,String customerName,boolean isWhippedCream,boolean isChocolate,int quantity) {

        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        String message = customerName +"\n"+ "Add whipped cream? " + isWhippedCream+"\nAdd chocolate? "+ isChocolate+"\nQuantity: "+quantity+ "\nTotal " + NumberFormat.getCurrencyInstance().format(price)+"\n"+ "Thank you!";
        priceTextView.setText(message);

        return message;
    }

    public String displayName(){

        EditText nameCustomer = (EditText) findViewById(R.id.name_view);
        String customerName = nameCustomer.getText().toString();

        return customerName;
    }

}