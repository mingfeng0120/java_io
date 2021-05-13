package com.liumf.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketBIOLambda {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9000,2);

        System.out.println("step 1: new ServerSocket(9000) ");

        while (true) {
            Socket client = server.accept();
            System.out.println("step 2:client " + client.getPort());

            new Thread(() -> {
                InputStream in = null;
                try {
                    in = client.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    while(true){
                        String dataline = reader.readLine();

                        if(null != dataline){
                            System.out.println(dataline);
                        }else{
                            client.close();
                            break;
                        }
                    }
                    System.out.println("客户端断开");

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }).start();

        }
    }
}
