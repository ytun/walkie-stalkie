package com.ytun.walkie_stalkie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Yamin Tun on 5/21/17.
 */

public class FBActivity extends AppCompatActivity{ // implements View.OnClickListener {

    static Logger LOGGER = LoggerFactory.getLogger(FBActivity.class);

    Button stalkBtn, logOutBtn;
    EditText nameEdTxt;
    ArrayList<TextView> listNameTxtView = new ArrayList<TextView>();
    ArrayList<TextView> listDetailTxtView = new ArrayList<TextView>();
    ArrayList<ImageView> listImageView = new ArrayList<ImageView>();

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this); //SDK needs to be initiated
        setContentView(R.layout.activity_main);

        listNameTxtView.add((TextView) findViewById(R.id.name1_txtv));
        listNameTxtView.add((TextView) findViewById(R.id.name2_txtv));
        listNameTxtView.add((TextView) findViewById(R.id.name3_txtv));

        listDetailTxtView.add((TextView) findViewById(R.id.detail1_txtv));
        listDetailTxtView.add((TextView) findViewById(R.id.detail2_txtv));
        listDetailTxtView.add((TextView) findViewById(R.id.detail3_txtv));

        listImageView.add((ImageView)findViewById(R.id.profileImage1));
        listImageView.add((ImageView)findViewById(R.id.profileImage2));
        listImageView.add((ImageView)findViewById(R.id.profileImage3));

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
        String query = "search?q=" +name +"&type=user&fields=id,name,link,picture,is_verified&limit=3";

        try {
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(), query, null, HttpMethod.GET,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            LOGGER.debug(response.toString());

                            clearScreen();

                            JSONObject data=null;
                            JSONArray jsonArr = null;
                            JSONObject jsonObj=response.getJSONObject();

                            String name = "";
                            String link="";
                            String picLink="";
                            boolean verified=false;


                            try {

                                jsonArr = jsonObj.getJSONArray("data");

                                for(int i=0; i<jsonArr.length(); i++){
                                    data=jsonArr.getJSONObject(i);
                                    LOGGER.debug("data: "+data.toString());

                                    name = data.get("name").toString();
                                    link = data.get("link").toString();
                                    verified = (boolean)data.get("is_verified");

                                    picLink = data.getJSONObject("picture").getJSONObject("data").get("url").toString();

                                    LOGGER.debug("name: "+name);
                                    LOGGER.debug("link: "+link);
                                    LOGGER.debug("picLink: "+picLink);

                                    //gender,locale,timezone,updated_time,verified

                                    listNameTxtView.get(i).setText(Html.fromHtml(
                                            "<a href=\""+link+"\">"+name+"</a> ") );
                                    listNameTxtView.get(i).setMovementMethod(LinkMovementMethod.getInstance());
                                    listDetailTxtView.get(i).setText((verified)?"verified": "not verified");

                                    new FBActivity.DownloadImage(listImageView.get(i)).execute(picLink);
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
                netInfo.getState() ==  android.net.NetworkInfo.State.CONNECTING  ) {
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

    public void clearScreen(){

        for(int i=0; i<listNameTxtView.size(); i++){
            listNameTxtView.get(i).setText("");
            listDetailTxtView.get(i).setText("");
            listImageView.get(i).setImageResource(android.R.color.transparent);
        }

    }

    // Reference: http://web.archive.org/web/20120802025411/http://developer.aiwgame.com/imageview-show-image-from-url-on-android-4-0.html
    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}



