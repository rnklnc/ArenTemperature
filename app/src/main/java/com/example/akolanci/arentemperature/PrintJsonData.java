package com.example.akolanci.arentemperature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class PrintJsonData{
    final Animation in = new AlphaAnimation(0.0f, 1.0f);
    final Animation out = new AlphaAnimation(1.0f, 0.0f);

    public PrintJsonData(Activity activity) {
		 TextView txtTemp = (TextView) activity.findViewById(R.id.txtTemp);
		 
		 JSONObject json = getJson();
		 try {
			JSONObject main = json.getJSONObject("main");
			JSONObject sys = json.getJSONObject("sys");
			String country = sys.getString("country");
			String region = json.getString("name");
			String temp = main.getString("temp");
             in.setDuration(2000);
             out.setDuration(500);
			txtTemp.setText("Region: "+region+","+country+"\n"+"Temperature:"+temp);
		} catch (JSONException e) {
			txtTemp.setText("Error");
		}
	      
	}

	private static String readAll(Reader rd) throws IOException {
	        StringBuilder sb = new StringBuilder();
	        int cp;
	        while ((cp = rd.read()) != -1) {
	            sb.append((char) cp); 
	        }
	        return sb.toString(); 
	    }  

	    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	        InputStream is = new URL(url).openStream();
	        try {
	            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	            String jsonText = readAll(rd);
	            JSONObject json = new JSONObject(jsonText);
	            return json;
	        } finally {
	            is.close();
	        }
	    }

	    private JSONObject getJson() {
	        JSONObject json = null;
	        try {
	            json = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?lat="+ LocationProvider.getLatitude()+"&lon="+LocationProvider.getLongitude());
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }

	        return json;
	    }
}
