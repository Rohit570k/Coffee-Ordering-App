package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int quantity=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this,"You can't have more than 100 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity+=1;
        display(quantity);

        CheckBox whippedCreamCheckBox =(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream =whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
        int price =calculatePrice(hasWhippedCream,hasChocolate);
        displayPrice(price);
    }
    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this,"You can't have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity-=1;
        display(quantity);

        CheckBox whippedCreamCheckBox =(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream =whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
        int price =calculatePrice(hasWhippedCream,hasChocolate);
        displayPrice(price);
    }
    public void submit(View view){

        CheckBox whippedCreamCheckBox =(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream =whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
      int price= calculatePrice(hasWhippedCream,hasChocolate);
      displayPrice(price);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {


        EditText nameField =(EditText)findViewById(R.id.name_field);
        String name =nameField.getText().toString();

        CheckBox whippedCreamCheckBox =(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream =whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage =createOrderSummary(price,hasWhippedCream,hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);

    }
    private int calculatePrice(boolean addWhippedCream , boolean addChocolate){
        int basePrice = 5;
        if(addWhippedCream){
            basePrice = basePrice + 1;
        }
        if(addChocolate){
            basePrice= basePrice + 2;
        }
        return quantity*basePrice;
    }
    private  String createOrderSummary(int price,boolean addWhippedCream ,boolean addChocolate, String name){
        String priceMessage = "Name: " + name;
        if(addWhippedCream){
            priceMessage += "\nAdd Whipped Cream ? "+ "\n YES";
        }
        else {priceMessage += "\nAdd Whipped Cream ? "+ "\n NO";}

        if(addChocolate){
            priceMessage += "\nAdd Chocolate ? "+ "\n YES";
        }
        else{ priceMessage += "\nAdd Chocolate ? "+ "\n NO";}

        priceMessage+= "\nPrice: $" + price;
        priceMessage = priceMessage + "\n"+ "Thank You!";
        return (priceMessage);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
       // priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
        priceTextView.setText("$"+number);
    }
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }
}