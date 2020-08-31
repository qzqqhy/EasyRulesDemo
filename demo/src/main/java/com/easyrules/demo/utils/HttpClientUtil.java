package com.easyrules.demo.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**
 * 应用jar包 httpclient-4.3.6/httpcore-4.4
 * @author Administrator
 *
 */
public class HttpClientUtil {
    
    private static String defaultCharset = "UTF-8";//默认编码
    private static int connectionRequestTimeout = 60000;
    private static int connectTimeout = 60000;
    private static int socketTimeout = 60000;
    
    /**
     * httpclient post请求(不带编码，默认UTF-8)
     * @param uri
     * @param param 参数支持string map
     * @return
     */
    public static String doHttpPost(String uri,Object param){
        return doHttpPost(uri, param, defaultCharset);
    }
    
    /**
     * httpclient post请求
     * @param uri
     * @param param 支持string map
     * @param charset
     * @return
     */
    public static String doHttpPost(String uri,Object param,String charset){
        if(uri==null||"".equals(uri)){
            System.out.println("请求uri为空！");
            return null;
        }
        int index = uri.indexOf("?");
        if(index>-1){
            uri = uri.substring(0, index);
        }

        CloseableHttpClient httpClient = createHttpClient(uri);
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build());
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset="+charset);
        //有些服务器做了限制，访问不到，需要伪装浏览器请求
        httpPost.setHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
//        httpPost.getParams().setParameter(HttpHeaders.USER_AGENT,
//                "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");//如果放到header中不管用,就打开注释
        
        //将参数封装到HttpEntity
        HttpEntity requestEntity = null;
        if(param!=null&&!"".equals(param)){
            requestEntity = getRequestEntity(param,charset);
            httpPost.setEntity(requestEntity);
        }

        CloseableHttpResponse response = null;
        HttpEntity responseEntity = null;
        try {
            response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            System.out.println("HttpClientUtil(doHttpPost)返回状态码statusCode："+statusCode);
            if(statusCode!=HttpStatus.SC_OK){
                return null;
            }
            responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity, charset);
            System.out.println("HttpClientUtil(doHttpPost)返回info："+result);
            return result;
            
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                EntityUtils.consume(responseEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpClientUtils.closeQuietly(response);
            try {
                EntityUtils.consume(requestEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpClientUtils.closeQuietly(httpClient);
        }
        
        return null;
    }
    
    
    /**
     * 根据传入的参数类型获取对应的封装了参数的httpEntity
     * @param param
     * @return
     */
    private static HttpEntity getRequestEntity(Object param,String charset){
        if(param==null){
            return null;
        }
        
        if(param instanceof String){
            String paramStr = (String)param;
            int index = paramStr.indexOf("?");//问号位置
            if(index>-1){
                paramStr = paramStr.substring(index+1);
            }
            StringEntity stringEntity = new StringEntity(paramStr,charset);
            stringEntity.setContentType("application/x-www-form-urlencoded;charset="+charset);
            return stringEntity;
        }else if(param instanceof Map){
            Map paramMap = (Map)param;
            int paramSize = paramMap.size();
            if(paramSize==0){
                return null;
            }
            List<NameValuePair> list = new ArrayList<NameValuePair>(paramSize);
            
            Iterator iterator = paramMap.keySet().iterator();
            while(iterator.hasNext()){
                Object key = iterator.next();
                Object value = paramMap.get(key);
                NameValuePair nameValuePair = new BasicNameValuePair(key.toString(), value.toString());
                list.add(nameValuePair);
            }

            UrlEncodedFormEntity urlEncodedFormEntity = null;
            try {
                urlEncodedFormEntity = new UrlEncodedFormEntity(list,charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return urlEncodedFormEntity;
        }
        
        return null;

    }
    
    
    /**
     * httpclient get请求(不带编码，默认UTF-8)
     * @param uri
     * @param param
     * @return
     */
    public static String doHttpGet(String uri,String param){
        return doHttpGet(uri,param ,defaultCharset);
    }
    
    /**
     * httpclient get请求
     * @param uri
     * @param param
     * @param charset
     * @return
     */
    public static String doHttpGet(String uri,String param,String charset){
        if(uri==null||"".equals(uri)){
            System.out.println("请求uri为空！");
            return null;
        }
        
        int index = uri.indexOf("?");
        if(index>-1){
            uri = uri.substring(0, index);
        }
        
        uri += encodeParam(param, charset);

        CloseableHttpClient httpClient = createHttpClient(uri);
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build());
        httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset="+charset);
        //有些服务器做了限制，访问不到，需要伪装浏览器请求
        httpGet.setHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
//        httpGet.getParams().setParameter(HttpHeaders.USER_AGENT,
//                "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");//如果放到header中不管用,就打开注释
                
        CloseableHttpResponse response = null;
        HttpEntity responseEntity = null;
        try {
            response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            System.out.println("HttpClientUtil(doHttpGet)返回状态码statusCode："+statusCode);
            if(statusCode!=HttpStatus.SC_OK){
                return null;
            }
            responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity, charset);
            System.out.println("HttpClientUtil(doHttpGet)返回info："+result);
            return result;
            
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                EntityUtils.consume(responseEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        
        return null;
    }
    
    /**
     * 将get请求的参数进行编码
     * @param param
     * @param charset
     * @return
     */
    private static String encodeParam(String param,String charset){
        if(param==null){
            return "";
        }
        int index = param.indexOf("?");//问号位置
        if(index>-1){
            param = param.substring(index+1);
        }
        if(param.length()==0){
            return "";
        }
        String[] keyAndValues = param.split("&");
        if(keyAndValues==null||keyAndValues.length==0){
            return "";
        }
        
        String encodedParam = "";
        
        int count = 0;
        for(String keyAndValue:keyAndValues){
            count ++;
            String[] arr = keyAndValue.split("=",2);
            if(arr==null||arr.length==0){
                System.out.println("参数("+keyAndValue+")异常！");
                continue;
            }
            
            String key = arr[0];
            String value = arr[1];
            
            if("null".equals(value)){//处理参数值为字符串的null，置空
                value = "";
            }
            
            if(value!=null&&!"".equals(value)){
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            encodedParam  += (key +"="+ value);
            
            if(count<keyAndValues.length){
                encodedParam += "&";
            }
            
        }
        
        if(encodedParam.length()!=0){
            encodedParam = "?"+ encodedParam;
        }
        
        return encodedParam;
    }
    

    /**
     * 创建HttpClient
     * @param uri
     * @return
     */
    private static CloseableHttpClient createHttpClient(String uri){
        
        if(uri!=null&&uri.indexOf("https")>-1){
            try {
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    //信任所有
                    @Override
                    public boolean isTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                        return true;
                    }
                }).build();
       
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
                return HttpClients.custom().setSSLSocketFactory(sslsf).build();
             } catch (KeyManagementException e) {
                 e.printStackTrace();
             } catch (NoSuchAlgorithmException e) {
                 e.printStackTrace();
             } catch (KeyStoreException e) {
                 e.printStackTrace();
             }
        }
        
        return  HttpClients.createDefault();
    }
    
}