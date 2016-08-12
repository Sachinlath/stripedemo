package com.stripe.example.activity;

/**
 * Created by insonix on 10/8/16.
 */

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.stripe.example.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MainActivity extends Activity {
    private JSONObject jsonObject;

    String strParsedValue = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView output = (TextView) findViewById(R.id.textView1);
//        String strJson="{ \"Employee\" :[{\"id\":\"101\",\"name\":\"Sonoo Jaiswal\",\"salary\":\"50000\"},{\"id\":\"102\",\"name\":\"Vimal Jaiswal\",\"salary\":\"60000\"}] }";
        String strJson=" <com.stripe.model.Customer@231093155 id=cus_8zERBGDjkBtmmE> JSON:  {" +
                "                                                                           \"account_balance\": 0," +
                "                                                                           \"business_vat_id\": null," +
                "                                                                           \"cards\": {" +
                "                                                                             \"count\": null," +
                "                                                                             \"data\": [" +
                "                                                                               {" +
                "                                                                                 \"address_city\": null,\n" +
                "                                                                                 \"address_country\": null,\n" +
                "                                                                                 \"address_line1\": null,\n" +
                "                                                                                 \"address_line1_check\": null,\n" +
                "                                                                                 \"address_line2\": null,\n" +
                "                                                                                 \"address_state\": null,\n" +
                "                                                                                 \"address_zip\": null,\n" +
                "                                                                                 \"address_zip_check\": null,\n" +
                "                                                                                 \"brand\": \"Visa\",\n" +
                "                                                                                 \"country\": \"US\",\n" +
                "                                                                                 \"currency\": null,\n" +
                "                                                                                 \"cvc_check\": \"pass\",\n" +
                "                                                                                 \"default_for_currency\": null,\n" +
                "                                                                                 \"dynamic_last4\": null,\n" +
                "                                                                                 \"exp_month\": 7,\n" +
                "                                                                                 \"exp_year\": 2018,\n" +
                "                                                                                 \"fingerprint\": \"3UPGUoNwPGaZbxWe\",\n" +
                "                                                                                 \"funding\": \"credit\",\n" +
                "                                                                                 \"last4\": \"4242\",\n" +
                "                                                                                 \"name\": null,\n" +
                "                                                                                 \"recipient\": null,\n" +
                "                                                                                 \"status\": null,\n" +
                "                                                                                 \"tokenization_method\": null,\n" +
                "                                                                                 \"type\": \"Visa\",\n" +
                "                                                                                 \"account\": null,\n" +
                "                                                                                 \"customer\": \"cus_8yrtJ8MZWmblq7\",\n" +
                "                                                                                 \"id\": \"card_18gu9n4QXGdwfkx6HB0roejm\",\n" +
                "                                                                                 \"metadata\": {},\n" +
                "                                                                                 \"object\": \"card\"\n" +
                "                                                                               }\n" +
                "                                                                             ],\n" +
                "                                                                             \"has_more\": false,\n" +
                "                                                                             \"request_options\": null,\n" +
                "                                                                             \"request_params\": null,\n" +
                "                                                                             \"total_count\": 1,\n" +
                "                                                                             \"url\": \"/v1/customers/cus_8yrtJ8MZWmblq7/cards\"\n" +
                "                                                                           },\n" +
                "                                                                           \"created\": 1470825716,\n" +
                "                                                                           \"currency\": null,\n" +
                "                                                                           \"default_card\": \"card_18gu9n4QXGdwfkx6HB0roejm\",\n" +
                "                                                                           \"default_source\": \"card_18gu9n4QXGdwfkx6HB0roejm\",\n" +
                "                                                                           \"deleted\": null,\n" +
                "                                                                           \"delinquent\": false,\n" +
                "                                                                           \"description\": \"Retrive customer\",\n" +
                "                                                                           \"discount\": null,\n" +
                "                                                                           \"email\": null,\n" +
                "                                                                           \"id\": \"cus_8yrtJ8MZWmblq7\",\n" +
                "                                                                           \"livemode\": false,\n" +
                "                                                                           \"metadata\": {},\n" +
                "                                                                           \"next_recurring_charge\": null,\n" +
                "                                                                           \"shipping\": null,\n" +
                "                                                                           \"sources\": {\n" +
                "                                                                             \"count\": null,\n" +
                "                                                                             \"data\": [\n" +
                "                                                                               {\n" +
                "                                                                                 \"address_city\": null,\n" +
                "                                                                                 \"address_country\": null,\n" +
                "                                                                                 \"address_line1\": null,\n" +
                "                                                                                 \"address_line1_check\": null,\n" +
                "                                                                                 \"address_line2\": null,\n" +
                "                                                                                 \"address_state\": null,\n" +
                "                                                                                 \"address_zip\": null,\n" +
                "                                                                                 \"address_zip_check\": null,\n" +
                "                                                                                 \"brand\": \"Visa\",\n" +
                "                                                                                 \"country\": \"US\",\n" +
                "                                                                                 \"currency\": null,\n" +
                "                                                                                 \"cvc_check\": \"pass\",\n" +
                "                                                                                 \"default_for_currency\": null,\n" +
                "                                                                                 \"dynamic_last4\": null,\n" +
                "                                                                                 \"exp_month\": 7,\n" +
                "                                                                                 \"exp_year\": 2018,\n" +
                "                                                                                 \"fingerprint\": \"3UPGUoNwPGaZbxWe\",\n" +
                "                                                                                 \"funding\": \"credit\",\n" +
                "                                                                                 \"last4\": \"4242\",\n" +
                "                                                                                 \"name\": null,\n" +
                "                                                                                 \"recipient\": null,\n" +
                "                                                                                 \"status\": null,\n" +
                "                                                                                 \"tokenization_method\": null,\n" +
                "                                                                                 \"type\": \"Visa\",\n" +
                "                                                                                 \"account\": null,\n" +
                "                                                                                 \"customer\": \"cus_8yrtJ8MZWmblq7\",\n" +
                "                                                                                 \"id\": \"card_18gu9n4QXGdwfkx6HB0roejm\",\n" +
                "                                                                                 \"metadata\": {},\n" +
                "                                                                                 \"object\": \"card\"\n" +
                "                                                                               }\n" +
                "                                                                             ],\n" +
                "                                                                             \"has_more\": false,\n" +
                "                                                                             \"request_options\": null,\n" +
                "                                                                             \"request_params\": null,\n" +
                "                                                                             \"total_count\": 1,\n" +
                "                                                                             \"url\": \"/v1/customers/cus_8yrtJ8MZWmblq7/sources\"\n" +
                "                                                                           },\n" +
                "                                                                           \"subscription\": null,\n" +
                "                                                                           \"subscriptions\": {\n" +
                "                                                                             \"count\": null,\n" +
                "                                                                             \"data\": [],\n" +
                "                                                                             \"has_more\": false,\n" +
                "                                                                             \"request_options\": null,\n" +
                "                                                                             \"request_params\": null,\n" +
                "                                                                             \"total_count\": 0,\n" +
                "                                                                             \"url\": \"/v1/customers/cus_8yrtJ8MZWmblq7/subscriptions\"\n" +
                "                                                                           },\n" +
                "                                                                           \"trial_end\": null\n" +
                "                                                                         }";

        String[] strJso=strJson.split("JSON:");
        String strjso=strJso[0];
        String strjsonfinal=strJso[1];
        String data = "";
        try {
            JSONObject jsonObject;

            jsonObject = new JSONObject(strjsonfinal);

            JSONObject object = jsonObject.getJSONObject("cards");
            JSONArray subArray = object.getJSONArray("data");

            strParsedValue += "\n Array Length => " + subArray.length();

            for (int i = 0; i < subArray.length(); i++) {
                data=  subArray.getJSONObject(i).getString("exp_year").toString();

            }
            output.setText(data);
        } catch (JSONException e) {e.printStackTrace();}
    }
}