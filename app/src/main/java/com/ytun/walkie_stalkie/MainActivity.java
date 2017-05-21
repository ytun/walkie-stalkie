package com.ytun.walkie_stalkie;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.restfb.types.User;

import java.util.ArrayList;

import static com.ytun.walkie_stalkie.Fb.LOGGER;


public class MainActivity extends Activity {

    Button stalkBtn;
    EditText nameEdTxt;
    ArrayList<TextView> listTxtView = new ArrayList<TextView>();
    TextView result1TxtV, result2TxtV, result3TxtV;


    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result1TxtV=(TextView) findViewById(R.id.result1_txtv);
        result2TxtV=(TextView) findViewById(R.id.result2_txtv);
        result3TxtV=(TextView) findViewById(R.id.result3_txtv);

        listTxtView.add(result1TxtV);
        listTxtView.add(result2TxtV);
        listTxtView.add(result3TxtV);

        nameEdTxt =(EditText) findViewById(R.id.search_edtxt);

        stalkBtn=(Button) findViewById(R.id.stalk_btn);
        stalkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkInternetConnection();

                try{
                    String name = nameEdTxt.getText().toString();
                    System.out.println("Name: "+name);

                    if(name.length()>0){ //& searchOnFb(name)){
                        User[] resultArr= Fb.searchOnFb(name);

                        result1TxtV.setText(resultArr[0].getName());
                        result2TxtV.setText(resultArr[1].getName());
                        result3TxtV.setText(resultArr[2].getName());
//                        for(int i=0; i<resultArr.length; i++){
//                            listTxtView.get(i).setText(resultArr[i].getName());
//                        }

                    }
                }
                catch(Exception e){
                    LOGGER.error("UI Exception while fetching facebook search results, error_message=[{}], \nerror_stack=[{}]", e.getMessage(), e.getStackTrace());

                    Toast.makeText(v.getContext(), "Invalid Name - Try again", Toast.LENGTH_LONG).show();

                }
            }
        });






//        nameEdTxt.setOnEditorActionListener(new TextView.OnEditorActionListener(){
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if(actionId == EditorInfo.IME_ACTION_SEARCH){
//                    searchOnFb();
//                    handled= true;
//                }
//                return handled;
//            }
//        });



    }


    private boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getActiveNetworkInfo().getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getActiveNetworkInfo().getState() ==
                        android.net.NetworkInfo.State.CONNECTING  ) {
            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

//    protected boolean searchOnFb(String name){
//        result1TxtV.setText("fake result1");
//        result2TxtV.setText("fake result2");
//        result3TxtV.setText("fake result3");
//        return true;
//    }


}
