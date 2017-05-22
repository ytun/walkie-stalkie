//package com.ytun.walkie_stalkie;
//
//import android.app.Activity;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//
//public class MainActivity extends Activity {
//
//    Button stalkBtn;
//    EditText nameEdTxt;
//    ArrayList<TextView> listTxtView = new ArrayList<TextView>();
//    TextView result1TxtV, result2TxtV, result3TxtV;
//
//
//    String result;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        result1TxtV=(TextView) findViewById(R.id.result1_txtv);
//        result2TxtV=(TextView) findViewById(R.id.result2_txtv);
//        result3TxtV=(TextView) findViewById(R.id.result3_txtv);
//
//        listTxtView.add(result1TxtV);
//        listTxtView.add(result2TxtV);
//        listTxtView.add(result3TxtV);
//
//        nameEdTxt =(EditText) findViewById(R.id.search_edtxt);
//
//        stalkBtn=(Button) findViewById(R.id.stalk_btn);
//        stalkBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                checkInternetConnection();
//
////                try{
////                    changText();
//
//                    String name = nameEdTxt.getText().toString();
//                    System.out.println("Name: "+name);
//
//                    if(name.length()>0){ //& searchOnFb(name)){
////                        User[] resultArr= Fb.searchOnFb(name);
////
////                        for(int i=0; i<resultArr.length; i++){
////                            listTxtView.get(i).setText(resultArr[i].getName());
////                        }
//
//                    }
////                }
////                catch(Exception e){
////                    LOGGER.error("UI Exception while fetching facebook search results, error_message=[{}], \nerror_stack=[{}]", e.getMessage(), e.getStackTrace());
////
////                    Toast.makeText(v.getContext(), "Invalid Name - Try again", Toast.LENGTH_LONG).show();
////
////                }
//            }
//        });
//
//
//
//    }
//
//
//    private boolean checkInternetConnection() {
//        // get Connectivity Manager object to check connection
//        ConnectivityManager connec
//                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
//
//        NetworkInfo netInfo= connec.getActiveNetworkInfo();
//
//        if(netInfo==null){
//            return false;
//        }
//        // Check for network connections
//        if ( netInfo.getState() ==
//                android.net.NetworkInfo.State.CONNECTED ||
//                netInfo.getState() ==
//                        android.net.NetworkInfo.State.CONNECTING  ) {
//            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
//            return true;
//        }else if (
//                connec.getActiveNetworkInfo().getState() ==
//                        android.net.NetworkInfo.State.DISCONNECTED   ) {
//            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        return false;
//    }
//
////    public void changText() {
////
////
////        // Instantiate the RequestQueue.
////        RequestQueue queue = Volley.newRequestQueue(this);
////        String url ="http://www.google.com";
////
////// Request a string response from the provided URL.
////        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
////                new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////                        // Display the first 500 characters of the response string.
////                        result1TxtV.setText("Response is: "+ response.substring(0,500));
////                    }
////                }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                result1TxtV.setText("That didn't work!");
////            }
////        });
////// Add the request to the RequestQueue.
////        queue.add(stringRequest);
////
//////        BufferedReader in = null;
//////        String data = null;
//////
//////        try{
//////            HttpClient httpclient = new DefaultHttpClient();
//////
//////            HttpGet request = new HttpGet();
//////            URI website = new URI("http://alanhardin.comyr.com/matt24/matt28.php");
//////            request.setURI(website);
//////            HttpResponse response = httpclient.execute(request);
//////            in = new BufferedReader(new InputStreamReader(
//////                    response.getEntity().getContent()));
//////
//////            // NEW CODE
//////            String line = in.readLine();
//////            result1TxtV.append(" First line: " + line);
//////
//////            LOGGER.debug("RESPOLNE: "+line);
//////            // END OF NEW CODE
//////
//////            result1TxtV.append(" Connected ");
//////        }catch(Exception e){
//////            Log.e("log_tag", "Error in http connection "+e.toString());
//////        }
////    }
//
////    protected boolean searchOnFb(String name){
////        result1TxtV.setText("fake result1");
////        result2TxtV.setText("fake result2");
////        result3TxtV.setText("fake result3");
////        return true;
////    }
//
//
//}
