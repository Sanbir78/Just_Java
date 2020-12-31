package android.example.com;

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
import java.util.concurrent.Callable;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
     int quantity=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
       EditText nameFeild = (EditText) findViewById(R.id.name_feild);
        String name= nameFeild.getText().toString();
        Log.v("MainActivity","Name:"+name );
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox =( CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        Log.v("MainActivity","Has Whipped Cream: " +hasWhippedCream);
        Log.v("MainActivity","Has Chocolate: " +hasChocolate);
       int price= calculatePrice( hasWhippedCream,hasChocolate);
       String priceMessage= createOrderSummary(name,price ,hasWhippedCream ,hasChocolate);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
        }
       displayMessage(priceMessage);
    }
    private int calculatePrice (boolean addWhippdCream , boolean addChocolate)
    {
        int basePrice = quantity*5;
       if (addWhippdCream)
           basePrice = basePrice+1;
      if(addChocolate)
          basePrice = basePrice+2;
      int price = quantity*basePrice;
        return price;
    }
    private String createOrderSummary(String name ,int price , boolean addWhippedCream , boolean addChocolate)
    {
        String priceMessage="Name: "+ name;
        priceMessage=priceMessage+"\nAdd Whipped Cream? " +addWhippedCream;
        priceMessage=priceMessage+"\nAdd Chocolate? "+addChocolate;
        priceMessage= priceMessage+"\nQuantity: "+quantity;
        priceMessage =priceMessage+"\nTotal: $"+ price;
        priceMessage=priceMessage+"\nThank You!";
        return priceMessage;

    }
    private void displayMessage(String message)
    {
        TextView orderSummarytextview= (TextView) findViewById(R.id.order_summary_text_view);
        orderSummarytextview.setText(message);
    }
    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this,"You cannot order more than 100 coffee",Toast.LENGTH_SHORT).show();
            return;}
        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this,"You cannot order less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;}
        quantity=quantity-1;
        display(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.quantity_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}