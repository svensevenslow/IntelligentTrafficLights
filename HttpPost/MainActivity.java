package com.example.kainaat.conn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    Button b;
    TextView response;
    String Name = "hi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button)findViewById(R.id.button3);
        response = (TextView)findViewById(R.id.textView2);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    GetText();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Error 404",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void GetText() throws UnsupportedEncodingException{
        String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");;//string for values sent to server
        String text = "Data sent";
        BufferedReader reader=null;
        try{
            //defining url
            URL url = new URL("http://192.168.1.104");
            //send post request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();
            //server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "hello";
            //read server response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");

            }
            text = sb.toString();
        }catch (Exception e){

        }
        finally {
            try {
                reader.close();
            }catch (Exception e){

            }
        }
        response.setText(text);

    }
}