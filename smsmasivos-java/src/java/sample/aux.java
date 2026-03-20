/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.*;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
        

public class aux extends HttpServlet {

	/**
	 * A simple HelloWorld Servlet
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
            String mensaje ,numero, urlRequest, urlParametros, pais, sandbox, nombre, formato, code, compania = "";
            URL url;
            HttpsURLConnection conexion = null;
            String file = getServletContext().getRealPath("/../../web/xml");
            
            switch(req.getParameter("cmd")){
                case "send":
                    urlRequest="https://api.smsmasivos.com.mx/sms/send";
                    File tempFile_b = new File(file + "/config.xml");
                    
                    mensaje = req.getParameter("txtMensaje");
                    numero = req.getParameter("txtNumero");
                    pais = req.getParameter("textPais");
                    sandbox = req.getParameter("sandbox");
                    nombre = req.getParameter("nombre");
                    
                    if(nombre == ""){
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dateString = format.format( new Date()   );
                        nombre = "Escribe un nombre para tu campaña " + dateString;
                    }
                    
                    
                    urlParametros = "&message=" + URLEncoder.encode(mensaje, "UTF-8") +
                    "&numbers=" + URLEncoder.encode(numero, "UTF-8") +
                    "&country_code=" + URLEncoder.encode(pais, "UTF-8") +
                    "&name=" + URLEncoder.encode(nombre, "UTF-8") +
                    "&sandbox=" + URLEncoder.encode(sandbox, "UTF-8");
                     

                    try {
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(tempFile_b);
                        doc.getDocumentElement().normalize();

                        NodeList nList = doc.getElementsByTagName("config");
                        Node nNode = nList.item(0);
                        Element eElement = (Element) nNode;
                        //-----------------------crear conexion----------------------------
                        url = new URL(urlRequest);
                        conexion = (HttpsURLConnection)url.openConnection();
                        conexion.addRequestProperty("User-Agent", "Java/{version}");
                        conexion.setRequestMethod("POST");
                        conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                        conexion.setRequestProperty("apikey", eElement.getElementsByTagName("ApiKey").item(0).getTextContent());
                        conexion.setRequestProperty("Content-Length", "" + Integer.toString(urlParametros.getBytes().length));
                        conexion.setRequestProperty("Content-Language", "en-US");
                        conexion.setUseCaches (false);
                        conexion.setDoInput(true);
                        conexion.setDoOutput(true);
                        //-----------------------------------------------------------------

                        //--------------------Enviar Peticion------------------------------
                        DataOutputStream wr = new DataOutputStream ( conexion.getOutputStream() );
                        wr.writeBytes(urlParametros);
                        wr.flush();
                        wr.close();
                        //-----------------------------------------------------------------

                        //-----------------------Obtener Respuesta-------------------------
                        InputStream is = conexion.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                        String line;
                        StringBuffer responsee = new StringBuffer();
                        while((line = rd.readLine()) != null) {
                          responsee.append(line);
                          responsee.append('\r');
                        }
                        rd.close();
                        String respuesta = responsee.toString();
                        //-----------------------------------------------------------------

                        //-------------Decodificar JSON con libreria org.json--------------
                        JSONObject jsonObject = new JSONObject(respuesta);
                        res.getWriter().write(jsonObject.toString());
                        //-----------------------------------------------------------------
                    }catch (Exception e) {
                      res.getWriter().write(e.toString());
                    }finally {
                      if(conexion != null) conexion.disconnect();
                    }
                break;
                
                case "credits":
                    urlRequest="https://api.smsmasivos.com.mx/credits/consult";
                    File tempFile_c = new File(file + "/config.xml");
                       
                    try {
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(tempFile_c);
                        doc.getDocumentElement().normalize();

                        NodeList nList = doc.getElementsByTagName("config");
                        Node nNode = nList.item(0);
                        Element eElement = (Element) nNode;
                       
                        //-----------------------crear conexion----------------------------
                        url = new URL(urlRequest);
                        conexion = (HttpsURLConnection)url.openConnection();
             
                        conexion.addRequestProperty("User-Agent", "Java/{version}");
                        conexion.setRequestMethod("POST");
                        conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                        conexion.setRequestProperty("apikey", eElement.getElementsByTagName("ApiKey").item(0).getTextContent());
                        conexion.setRequestProperty("Content-Language", "en-US");
                        conexion.setUseCaches (false);
                        conexion.setDoInput(true);
                        conexion.setDoOutput(true);
                        
                        //-----------------------------------------------------------------

                        //--------------------Enviar Peticion------------------------------
                        DataOutputStream wr = new DataOutputStream ( conexion.getOutputStream() );
                        wr.writeBytes("");
                        wr.flush();
                        wr.close();
                        //-----------------------------------------------------------------

                        //-----------------------Obtener Respuesta-------------------------
                        InputStream is = conexion.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                        String line;
                        StringBuffer responsee = new StringBuffer();
                        while((line = rd.readLine()) != null) {
                          responsee.append(line);
                          responsee.append('\r');
                        }
                        rd.close();
                        String respuesta = responsee.toString();
                        //-----------------------------------------------------------------

                        //-------------Decodificar JSON con libreria org.json--------------
                        JSONObject jsonObject = new JSONObject(respuesta);
                        res.getWriter().write(jsonObject.toString());
                        //-----------------------------------------------------------------
                    }catch (Exception e) {
                      res.getWriter().write(e.toString());
                    }finally {
                      if(conexion != null) conexion.disconnect();
                    }
                break;
                
                case "2fa_register":
                    formato = req.getParameter("formato");
                    urlRequest="https://api.smsmasivos.com.mx/protected/"+formato+"/phones/verification/start";
                    File tempFile_d = new File(file + "/config.xml");
                    
                  
                    numero = req.getParameter("txtNumero");
                    pais = req.getParameter("txtPais");
                    code = req.getParameter("txtCode");
                    compania = req.getParameter("nombre");
                    
                    
                    urlParametros = "&phone_number=" + URLEncoder.encode(numero, "UTF-8") +                           
                    "&country_code=" + URLEncoder.encode(pais, "UTF-8") +
                    "&code_length=" + URLEncoder.encode(code, "UTF-8") +
                    "&company=" + URLEncoder.encode(compania, "UTF-8");

                    try {
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(tempFile_d);
                        doc.getDocumentElement().normalize();

                        NodeList nList = doc.getElementsByTagName("config");
                        Node nNode = nList.item(0);
                        Element eElement = (Element) nNode;
                        //-----------------------crear conexion----------------------------
                        url = new URL(urlRequest);
                        conexion = (HttpsURLConnection)url.openConnection();

                        conexion.addRequestProperty("User-Agent", "Java/{version}");
                        conexion.setRequestMethod("POST");
                        conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                        conexion.setRequestProperty("apikey", eElement.getElementsByTagName("ApiKey").item(0).getTextContent());
                        conexion.setRequestProperty("Content-Length", "" + Integer.toString(urlParametros.getBytes().length));
                        conexion.setRequestProperty("Content-Language", "en-US");
                        conexion.setUseCaches (false);
                        conexion.setDoInput(true);
                        conexion.setDoOutput(true);
                        //-----------------------------------------------------------------

                        //--------------------Enviar Peticion------------------------------
                        DataOutputStream wr = new DataOutputStream ( conexion.getOutputStream() );
                        wr.writeBytes(urlParametros);
                        wr.flush();
                        wr.close();
                        //-----------------------------------------------------------------

                        //-----------------------Obtener Respuesta-------------------------
                        InputStream is = conexion.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                        String line;
                        StringBuilder responsee = new StringBuilder();
                        while((line = rd.readLine()) != null) {
                          responsee.append(line);
                          responsee.append('\r');
                        }
                        rd.close();
                        String respuesta = responsee.toString();
                        //-----------------------------------------------------------------

                        //-------------Decodificar JSON con libreria org.json--------------
                        if(formato.equals("json")){
                            JSONObject jsonObject = new JSONObject(respuesta);
                            res.getWriter().write(jsonObject.toString());
                        }else{
                            res.getWriter().write(respuesta);
                        }
                        
                        //-----------------------------------------------------------------

                    }catch (Exception e) {
                      res.getWriter().write(e.toString());
                    }finally {
                      if(conexion != null) conexion.disconnect();
                    }
                break;
                
                case "2fa_validate":
                    formato = req.getParameter("formato");
                    urlRequest="https://api.smsmasivos.com.mx/protected/"+formato+"/phones/verification/check";
                    File tempFile_e = new File(file + "/config.xml");
                    
                    numero = req.getParameter("dest");
                    code = req.getParameter("code");
                    
                    urlParametros = "&phone_number=" + URLEncoder.encode(numero, "UTF-8") +                           
                    "&verification_code=" + URLEncoder.encode(code, "UTF-8");

                    try {
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(tempFile_e);
                        doc.getDocumentElement().normalize();

                        NodeList nList = doc.getElementsByTagName("config");
                        Node nNode = nList.item(0);
                        Element eElement = (Element) nNode;
                        //-----------------------crear conexion----------------------------
                        url = new URL(urlRequest);
                        conexion = (HttpsURLConnection)url.openConnection();

                        conexion.addRequestProperty("User-Agent", "Java/{version}");
                        conexion.setRequestMethod("POST");
                        conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                        conexion.setRequestProperty("apikey", eElement.getElementsByTagName("ApiKey").item(0).getTextContent());
                        conexion.setRequestProperty("Content-Length", "" + Integer.toString(urlParametros.getBytes().length));
                        conexion.setRequestProperty("Content-Language", "en-US");
                        conexion.setUseCaches (false);
                        conexion.setDoInput(true);
                        conexion.setDoOutput(true);
                        //-----------------------------------------------------------------

                        //--------------------Enviar Peticion------------------------------
                        DataOutputStream wr = new DataOutputStream ( conexion.getOutputStream() );
                        wr.writeBytes(urlParametros);
                        wr.flush();
                        wr.close();
                        //-----------------------------------------------------------------

                        //-----------------------Obtener Respuesta-------------------------
                        InputStream is = conexion.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                        String line;
                        StringBuilder responsee = new StringBuilder();
                        while((line = rd.readLine()) != null) {
                          responsee.append(line);
                          responsee.append('\r');
                        }
                        rd.close();
                        String respuesta = responsee.toString();
                        //-----------------------------------------------------------------

                        //-------------Decodificar JSON con libreria org.json--------------
                        if(formato.equals("json")){
                            JSONObject jsonObject = new JSONObject(respuesta);
                            res.getWriter().write(jsonObject.toString());
                        }else{
                            res.getWriter().write(respuesta);
                        }
                        //-----------------------------------------------------------------

                    }catch (Exception e) {
                      res.getWriter().write(e.toString());
                    }finally {
                      if(conexion != null) conexion.disconnect();
                    }
                break;
                
                case "auth":
                    try {                        
                        File tempFile = new File(file + "/config.xml");
                        tempFile.delete();

                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                        Document doc = docBuilder.newDocument();
                        Element rootElement = doc.createElement("xml");
                        doc.appendChild(rootElement);

                        Element config = doc.createElement("config");
                        rootElement.appendChild(config); 

                        Element api = doc.createElement("ApiKey");
                        api.appendChild(doc.createTextNode(req.getParameter("api")));
                        config.appendChild(api);

                        TransformerFactory tf = TransformerFactory.newInstance();
                        Transformer t = tf.newTransformer();
                        StringWriter sw = new StringWriter();
                        t.transform(new DOMSource(doc), new StreamResult(sw));

                        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                        writer.write(sw.toString());
                        writer.close();
                        
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("success", true);
                        jsonObject.put("message", "API KEY almacenada correctamente");
                        
                        res.getWriter().write(jsonObject.toString());
                        //-----------------------------------------------------------------

                    }catch (Exception e) {
                      JSONObject jsonCatch = new JSONObject();
                      jsonCatch.put("success", false);
                      jsonCatch.put("message", e.toString());
                      res.getWriter().write(jsonCatch.toString());
                    }finally {
                      if(conexion != null) conexion.disconnect();
                    }
                break;
                
                case "xmls":
                    try{
                        File tempFile = new File(file + "/config.xml");
                        
                        if(tempFile.exists()){ 
                               try{
                                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                                    Document doc = dBuilder.parse(tempFile);
                                    doc.getDocumentElement().normalize();
                                    
                                    NodeList nList = doc.getElementsByTagName("config");
                                    Node nNode = nList.item(0);
                                    Element eElement = (Element) nNode;
                                    
                                    JSONObject data = new JSONObject();
                                    data.put("ApiKey", eElement.getElementsByTagName("ApiKey").item(0).getTextContent());
                                    
                                    res.getWriter().write(data.toString());
                               }catch(Exception e){
                                    res.getWriter().write(e.toString());
                               }
                               
                        }else{
                            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                            
                            Document doc = docBuilder.newDocument();
                            Element rootElement = doc.createElement("xml");
                            doc.appendChild(rootElement);
                            
                            Element config = doc.createElement("config");
                            rootElement.appendChild(config); 
                            
                            Element api = doc.createElement("ApiKey");
                            api.appendChild(doc.createTextNode("_"));
                            config.appendChild(api);
                            
                            TransformerFactory tf = TransformerFactory.newInstance();
                            Transformer t = tf.newTransformer();
                            StringWriter sw = new StringWriter();
                            t.transform(new DOMSource(doc), new StreamResult(sw));

                            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                            writer.write(sw.toString());
                            writer.close();
                            
                            JSONObject data = new JSONObject();
                            data.put("ApiKey", "_");
                            
                            res.getWriter().write(data.toString());
                        }
                    }catch(Exception e){
                        res.getWriter().write(e.toString());
                    }
                    
                break;
            }
        }

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws java.io.IOException {
		doPost(req, res);
	}
}