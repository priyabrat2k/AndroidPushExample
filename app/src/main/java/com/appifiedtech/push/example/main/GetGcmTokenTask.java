package com.appifiedtech.push.example.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Priyabrat on 21-07-2015.
 */
public class GetGcmTokenTask extends AsyncTask<Void,Void,String> {
    String TAG = getClass().getName();
    ProgressDialog progressDialog;
    Activity activity;
    private GoogleCloudMessaging gcm;

    public GetGcmTokenTask(Activity activity){
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Registering...");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String responseStr = null;
        if(gcm == null){
            gcm = GoogleCloudMessaging.getInstance(activity);
        }
        InstanceID instanceID = InstanceID.getInstance(activity);
        String tokedId = null;
        try {
            tokedId = instanceID.getToken(Config.GCM_SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
            Log.d(TAG,"GCM token is "+tokedId);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error in fetching GCM token due to "+e);
        }
        if(tokedId != null)
        {
            String dataUrl = "http://appifiedtechforum-hello12345.rhcloud.com/doGcmRegister.php";
            String dataUrlParameters = "userId="+"admin"+"&gcmId="+tokedId;
            URL url;
            HttpURLConnection connection = null;
            try {
                // Create connection
                url = new URL(dataUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length","" + Integer.toString(dataUrlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                // Send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(dataUrlParameters);
                wr.flush();
                wr.close();
                // Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                responseStr = response.toString();
                Log.d("Server response",responseStr);

            } catch (Exception e) {

                e.printStackTrace();

            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        return responseStr;
    }

    @Override
    protected void onPostExecute(String resp) {
        if(resp != null)
        {
            Toast.makeText(activity,resp,Toast.LENGTH_LONG).show();
        }
    }
}
