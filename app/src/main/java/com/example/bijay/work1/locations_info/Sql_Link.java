package com.example.bijay.work1.locations_info;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import retrofit2.http.Url;

/**
 * Created by Bijay on 5/29/2018.
 */

public class Sql_Link extends AsyncTask<String,Void,String > {
    Context ctx;
    Sql_Link(Context ctx){

        this.ctx=ctx;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... params) {
        String reg_url="http://192.168.0.100/work12/register.php";
        String method=params[0];
        if(method.equals("save")){

            String hosname=params[1];
            String hosaddress=params[2];
            String hoslat=params[3];
            String hoslong=params[4];
            String hosopen=params[5];
            String hosclose=params[6];
            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data= URLEncoder.encode("hospital_name","UTF-8")+"="+URLEncoder.encode(hosname,"UTF-8")+"&"+
                        URLEncoder.encode("hospital_address","UTF-8")+"="+URLEncoder.encode(hosaddress,"UTF-8")+"&"+
                        URLEncoder.encode("hospital_lattitude","UTF-8")+"="+URLEncoder.encode(hoslat,"UTF-8")+"&"+
                        URLEncoder.encode("hospital_longitude","UTF-8")+"="+URLEncoder.encode(hoslong,"UTF-8")+"&"+
                        URLEncoder.encode("hospital_open","UTF-8")+"="+URLEncoder.encode(hosopen,"UTF-8")+"&"+
                        URLEncoder.encode("hospital_close","UTF-8")+"="+URLEncoder.encode(hosclose,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                return "Information Saved....";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }
}
