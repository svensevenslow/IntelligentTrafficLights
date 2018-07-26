package com.example.kainaat.gpscircle;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    Location location;
    double longitude, latitude;
    float speed ;
    TextView response;
    String send;

    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button) findViewById(R.id.button);
        response = (TextView)findViewById(R.id.textView);
        Button startbtn= (Button)findViewById(R.id.startbtn);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    GetText();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Error 404",Toast.LENGTH_LONG).show();
                }
            }
        });
        /*startbtn.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        startService(new Intent(getBaseContext(),my_service.class));

                    }
                }
        );*/




        b.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                boolean enabled = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (!enabled) {
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
                gps = new GPSTracker(MainActivity.this);
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    speed = (gps.getSpeed()) / 1000;

                    double latintersect = 30.7660589;
                    double longintersect = 76.7812687;//values of center of circle which at distance = radius from intersection
                    int checkWithinCircle = 0;
                    double latintersect1 = 30.8031709;
                    double longintersect1 = 76.7489822;//values of center of circle which at distance = radius from intersection
                    int checkWithinCircle1 = 0;
                    double latintersect2 = 30.8031709;
                    double longintersect2 = 76.7589822;//values of center of circle which at distance = radius from intersection
                    int checkWithinCircle2 = 0;
                    double latintersect3 = 30.7660578;
                    double longintersect3 = 76.7812666;//values of center of circle which at distance = radius from intersection
                    int checkWithinCircle3 = 0;


                        try {
                            double distant = distance(latitude, longitude, latintersect, longintersect);
                            double distant1 =  distance(latitude, longitude, latintersect1, longintersect1);
                            double distant2 =  distance(latitude, longitude, latintersect1, longintersect1);
                            double distant3 =  distance(latitude, longitude, latintersect1, longintersect1);
                            if (distant <= 6 && distant >= -6 && speed >= 0 && speed <= 5) {
                                checkWithinCircle = 1;//circle of radius 25,approx 10 cars
                                send = "1";
                            }
                            else if(distant1 <= 6 && distant1 >= -6 && speed >= 0 && speed <= 5){
                                checkWithinCircle1 = 1;//circle of radius 25,approx 10 cars
                                send = "2";
                            }
                            else if(distant2 <= 6 && distant2 >= -6 && speed >= 0 && speed <= 5){
                                checkWithinCircle2 = 1;//circle of radius 25,approx 10 cars
                                send = "3";
                            }
                            else if(distant3 <= 6 && distant3 >= -6 && speed >= 0 && speed <= 5){
                                checkWithinCircle3 = 1;//circle of radius 25,approx 10 cars
                                send = "4";
                            }
                            else {
                                checkWithinCircle = 0;
                                send = "0";
                        }

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    if (checkWithinCircle == 1 || checkWithinCircle1 == 1 || checkWithinCircle2 == 1 || checkWithinCircle3 == 1) {
                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                                        + latitude + "\nLong: " + longitude + "\nSpeed:" + speed + "\nThe car lies in the demarcated region"
                                , Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                                        + latitude + "\nLong: " + longitude + "\nSpeed:" + speed + "\nThe car does not lie in the demarcated region"
                                , Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }
    public void GetText() throws UnsupportedEncodingException {
        String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(send, "UTF-8");;//string for values sent to server
        String text = "Data sent";
        BufferedReader reader=null;
        try{
            //defining url
            URL url = new URL("http://10.42.0.1:3134");
            //Log.w("R","pre");
            //send post request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();
            //Log.w("R1","post");
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

    private double distance(double lat1,double lon1,double lat2,double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))*Math.sin(deg2rad(lat2))+ Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist*60 * 1.1515;
        return(dist*1000);

    }


    private double rad2deg(double rad) {

        return(rad * 180.0/Math.PI);
    }


    private double deg2rad(double deg) {
        return(deg*Math.PI/180.0);

    }
    }
