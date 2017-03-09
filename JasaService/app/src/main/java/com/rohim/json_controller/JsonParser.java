package com.rohim.json_controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.rohim.modal.User;

public class JsonParser {
	
	static InputStream is = null;
    static String json = "";

    // constructor
    public JsonParser() {

    }

    public String getJSONFromUrl(String url, List<NameValuePair> params) {
        // Making HTTP request
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {

            // defaultHttpClient

            HttpGet httpPost = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            is.close();
            json = sb.toString();
            Log.e("JSON", json);

        } catch (UnsupportedEncodingException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (ClientProtocolException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (IOException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }  catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
        return json;
    }

    public String setPost(String url, User user) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            json = new Gson().toJson(user);
            StringEntity se = new StringEntity(json);
            post.setEntity(se);

            HttpResponse httpResponse = httpClient.execute(post);
            String retSrc = EntityUtils.toString(httpResponse.getEntity());

            json = retSrc;
            //Log.e("JSON", json);

        } catch (UnsupportedEncodingException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (ClientProtocolException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (IOException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }  catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
        return json;
    }

    public String setPost(String url, String listJson) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            StringEntity se = new StringEntity(listJson);
            post.setEntity(se);

            HttpResponse httpResponse = httpClient.execute(post);
            String retSrc = EntityUtils.toString(httpResponse.getEntity());

            json = retSrc;
            //Log.e("JSON", json);

        } catch (UnsupportedEncodingException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (ClientProtocolException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (IOException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }  catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
        return json;
    }

    public String setPost(String url, Object ob) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            json = new Gson().toJson(ob);
            StringEntity se = new StringEntity(json);
            post.setEntity(se);

            HttpResponse httpResponse = httpClient.execute(post);
            String retSrc = EntityUtils.toString(httpResponse.getEntity());

            json = retSrc;
            //Log.e("JSON", json);

        } catch (UnsupportedEncodingException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (ClientProtocolException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (IOException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }  catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
        return json;
    }

    public String postFromUrl(String url) {
        // Making HTTP request
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {

            // defaultHttpClient

            HttpPost httpPost = new HttpPost(url);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            is.close();
            json = sb.toString();
            Log.e("JSON", json);

        } catch (UnsupportedEncodingException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (ClientProtocolException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (IOException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }  catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
        return json;
    }
}
