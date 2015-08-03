package com.cenatel_demo_presidencia.desarrollo.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class CustomHttpClient {

    public static final int HTTP_TIMEOUT=30*1000;
    public static HttpClient mhttpClient;

    private static HttpClient getHttpClient(){

        if(mhttpClient==null)
        {
            mhttpClient=new DefaultHttpClient();
            final HttpParams par=mhttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(par, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(par, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(par, HTTP_TIMEOUT);
        }

        return mhttpClient;
    }

    public static String executeHttpPost(String url, ArrayList postValores) throws Exception{
        BufferedReader in=null;

        try{
            HttpClient cliente =getHttpClient();
            HttpPost post=new HttpPost(url);
            UrlEncodedFormEntity formEntity=new  UrlEncodedFormEntity(postValores);
            post.setEntity(formEntity);

            HttpResponse respuesta=cliente.execute(post);

            in=new BufferedReader(new InputStreamReader(respuesta.getEntity().getContent()));
            StringBuffer sb=new StringBuffer("");
            String linea="";
            String NL= System.getProperty("line.separator");

            while ((linea=in.readLine())!=null) {
                sb.append(linea+NL);
            }
            in.close();
            String resultado=sb.toString();


            return resultado;
        }finally{
            if(in!=null)
            {
                try{
                    in.close();
                }
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

        }

    }

}