package org.rmysj.api.commons.util;

import java.io.*;

public class PlayUtils {

    public static void vod (String shpath,String rootPath,String filePath,String playUrl){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Process pro = null;
                String returnString = "";
                String command = "/bin/sh " + shpath + " " + rootPath + " " + filePath + " " + playUrl;
                try {
                    Thread.sleep(1000);
                    pro = Runtime.getRuntime().exec(command);
                    pro.waitFor();
                    BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
                    PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
                    String line;
                    while ((line = input.readLine()) != null) {
                        returnString = returnString + line + "\n";
                    }
                    input.close();
                    output.close();
                    pro.destroy();
                }catch (IOException e) {

                }catch (InterruptedException e) {
                }
            }}).start();
    }


    public static void listen (String shpath,String rootPath,String orgUrl,String repeatUrl){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Process pro = null;
                String returnString = "";
                String command = "/bin/sh " + shpath + " " + rootPath + " " + orgUrl + " " + repeatUrl;
                try {
                    Thread.sleep(1000);
                    pro = Runtime.getRuntime().exec(command);
                    pro.waitFor();
                    BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
                    PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
                    String line;
                    while ((line = input.readLine()) != null) {
                        returnString = returnString + line + "\n";
                    }
                    input.close();
                    output.close();
                    pro.destroy();
                }catch (IOException e) {

                }catch (InterruptedException e) {
                }
            }}).start();
    }
}
