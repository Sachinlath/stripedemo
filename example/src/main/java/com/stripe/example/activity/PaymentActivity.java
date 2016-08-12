package com.stripe.example.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.example.BuildConfig;
import com.stripe.example.PaymentForm;
import com.stripe.example.R;
import com.stripe.example.TokenList;
import com.stripe.example.dialog.ErrorDialogFragment;
import com.stripe.example.dialog.ProgressDialogFragment;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.stripe.example.activity.PaymentActivity.PUBLISHABLE_KEY;


public class PaymentActivity extends FragmentActivity {

    /*
     * Change this to your publishable key.
     *
     * You can get your key here: https://manage.stripe.com/account/apikeys
     */
    public static final String PUBLISHABLE_KEY = "pk_test_NQXIx2VDts9TMQkakqJmlc1G";
    final String publishableApiKey = BuildConfig.DEBUG ?
            "pk_test_NQXIx2VDts9TMQkakqJmlc1G" :
            //"sk_test_00000000000000000000000" :
            getString(R.string.com_stripe_publishable_key);

    private ProgressDialogFragment progressFragment;
    TextView output;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        output = (TextView) findViewById(R.id.textpayment);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        progressFragment = ProgressDialogFragment.newInstance(R.string.progressMessage);
    }

    public void saveCreditCard(PaymentForm form) {

        Card card = new Card(
                form.getCardNumber(),
                form.getExpMonth(),
                form.getExpYear(),
                form.getCvc());
        card.setCurrency(form.getCurrency());

        boolean validation = card.validateCard();
        if (validation) {
            startProgress();

// Create a Customer

//            Customer.create(HashMap,customerParams);
//            Customer customer = Customer.create(customerParams);
//
//// Charge the Customer instead of the card
//            Map<String, Object> chargeParams = new HashMap<String, Object>();
//            chargeParams.put("amount", 1000); // amount in cents, again
//            chargeParams.put("currency", "usd");
//            chargeParams.put("customer", customer.getId());
//
//            Charge.create(chargeParams);
            new Stripe().createToken(
                    card,
                    PUBLISHABLE_KEY,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            getTokenList().addToList(token);
                            Log.d("token_id", "token" + token);
//                            chargeClient("100", "I1004", "USD", token);
                            createCustomer("Testing customer 2", token);

                            finishProgress();
//                            Customer customer = Customer.create(HashMap<String,Object>,customerParams);
                        }

                        public void onError(Exception error) {
                            handleError(error.getLocalizedMessage());
                            finishProgress();
                        }
                    });
        } else if (!card.validateNumber()) {
        	handleError("The card number that you entered is invalid");
        } else if (!card.validateExpiryDate()) {
        	handleError("The expiration date that you entered is invalid");
        } else if (!card.validateCVC()) {
        	handleError("The CVC code that you entered is invalid");
        } else {
        	handleError("The card details that you entered are invalid");
        }
    }

    private void startProgress() {
        progressFragment.show(getSupportFragmentManager(), "progress");
    }

    private void finishProgress() {
        progressFragment.dismiss();
    }
//commit code test

    private void handleError(String error) {
        DialogFragment fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error);
        fragment.show(getSupportFragmentManager(), "error");
    }

    private TokenList getTokenList() {
        return (TokenList)(getSupportFragmentManager().findFragmentById(R.id.token_list));
    }
    public void createCustomer( String description, Token token) {
        // set secret key
        com.stripe.Stripe.apiKey  = "sk_test_haYuNR5AWbF4a5xYDkxK2PqE";
        Map<String, Object> customerParams = new HashMap<String, Object>();
//        customerParams.put("source", "Sachin address");
        customerParams.put("description", "Retrive customer");
        customerParams.put("card", token.getId());
//        try {
//            Customer customer= Customer.create(customerParams);
//
//            Log.d("customer_id","cutomer_id"+customer.getId());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        Log.d("NUM", "charge params put");

        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Customer customer =  Customer.create(customerParams, "sk_test_haYuNR5AWbF4a5xYDkxK2PqE");
            Log.d("CUstomer", "Successful" + customer.getId());
//            Log.d("CUstomerInformation", "Successful_rating" + customer.retrieve(customer.getId()));
                String strJson= String.valueOf(customer.retrieve(customer.getId()));

            Toast.makeText(PaymentActivity.this, "Successful"+customer.getId(), Toast.LENGTH_LONG).show();
                Log.d("strJson","strJson"+strJson);
                String[] strJso=strJson.split("JSON:");
                String strjso=strJso[0];
                String strjsonfinal=strJso[1];
                String data = "";
                try {
                    JSONObject jsonObject;

                    jsonObject = new JSONObject(strjsonfinal);

                    JSONObject object = jsonObject.getJSONObject("cards");
                    JSONArray subArray = object.getJSONArray("data");


                    for (int i = 0; i < subArray.length(); i++) {
                        data+=  subArray.getJSONObject(i).getString("last4").toString();
                        data+= "\n"+ subArray.getJSONObject(i).getString("exp_year").toString();
                        data+= "\n"+ subArray.getJSONObject(i).getString("exp_month").toString();

                    }
                    Log.d("strdata","data"+data);
                    output.setText(data);
                } catch (JSONException e) {e.printStackTrace();}
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (CardException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        }
    }


}
