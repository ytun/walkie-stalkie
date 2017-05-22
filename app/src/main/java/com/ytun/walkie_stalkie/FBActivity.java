package com.ytun.walkie_stalkie;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


/**
 * Created by ytun on 5/21/17.
 */

public class FBActivity extends AppCompatActivity{ // implements View.OnClickListener {

    static Logger LOGGER = LoggerFactory.getLogger(FBActivity.class);

    Button stalkBtn, logOutBtn;
    EditText nameEdTxt;
    ArrayList<TextView> listTxtView = new ArrayList<TextView>();
    TextView result1TxtV, result2TxtV, result3TxtV;


    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this); //SDK needs to be initiated
        setContentView(R.layout.activity_main);

        result1TxtV = (TextView) findViewById(R.id.result1_txtv);
        result2TxtV = (TextView) findViewById(R.id.result2_txtv);
        result3TxtV = (TextView) findViewById(R.id.result3_txtv);

        listTxtView.add(result1TxtV);
        listTxtView.add(result2TxtV);
        listTxtView.add(result3TxtV);

        nameEdTxt =(EditText) findViewById(R.id.search_edtxt);

        stalkBtn=(Button) findViewById(R.id.stalk_btn);
        logOutBtn=(Button) findViewById(R.id.logout);

        stalkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkInternetConnection();

                try {
                    String name = nameEdTxt.getText().toString();
                    search(name);
                }
                catch(Exception e){
                    LOGGER.error("UI Exception while fetching facebook search results, error_message=[{}], \nerror_stack=[{}]", e.getMessage(), e.getStackTrace());

                    Toast.makeText(v.getContext(), "Invalid Name - Try again", Toast.LENGTH_LONG).show();
                }

            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();

            }
        });

    }


    private void search(String name) {
        String query = "search?q=" +name +"&type=user&fields=id,name,link&limit=3";


        try {
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(), query, null, HttpMethod.GET,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            LOGGER.debug(response.toString());

                            JSONObject data=null;
                            JSONArray jsonArr = null;
                            JSONObject jsonObj=response.getJSONObject();

                            String name = "";
                            String link="";

                            try {

                                jsonArr = jsonObj.getJSONArray("data");

                                for(int i=0; i<jsonArr.length(); i++){
                                    data=jsonArr.getJSONObject(i);
                                    LOGGER.debug("data: "+data.toString());

                                    name = data.get("name").toString();
                                    link = data.get("link").toString();
                                    LOGGER.debug("name: "+name);
                                    LOGGER.debug("link: "+link);


                                    listTxtView.get(i).setText(name + "\n"+ link);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }

            ).executeAsync();

        }
        catch(Exception e){
            LOGGER.error("Search Exception while fetching facebook search results, error_message=[{}], \nerror_stack=[{}]", e.getMessage(), e.getStackTrace());
        }


    }



    private boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        NetworkInfo netInfo= connec.getActiveNetworkInfo();

        if(netInfo==null){
            return false;
        }
        // Check for network connections
        if ( netInfo.getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                netInfo.getState() ==
                        android.net.NetworkInfo.State.CONNECTING  ) {
            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getActiveNetworkInfo().getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED   ) {
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }


    private void logout(){
        LoginManager.getInstance().logOut();
        Intent login = new Intent(FBActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
////            case R.id.share:
////                share();
////                break;
//
//            case R.id.stalk_btn:
//                System.out.println("        Stalking in progress...");
//                checkInternetConnection();
//                System.out.println("        Stalking started...");
//                try {
//                    String name = nameEdTxt.getText().toString();
//                    search(name);
//
////                    if(name.length()>0){ //& searchOnFb(name)){
////                        User[] resultArr= search(name);
////
////                        for(int i=0; i<resultArr.length; i++){
////                            listTxtView.get(i).setText(resultArr[i].getName());
////                        }
////
////                    }
//                }
//                catch(Exception e){
//                    LOGGER.error("UI Exception while fetching facebook search results, error_message=[{}], \nerror_stack=[{}]", e.getMessage(), e.getStackTrace());
//
//                    Toast.makeText(view.getContext(), "Invalid Name - Try again", Toast.LENGTH_LONG).show();
//                }
//                break;
//
//            case R.id.logout:
//                logout();
//                break;
//        }
//    }

//    @Override
//    public void onClick(View v) {
//
//        switch(v.getId()){
//            case R.id.stalk_btn:
//                search();
//                break;
//        }
//    }

}



