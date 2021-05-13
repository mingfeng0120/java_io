package com.liumf.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO 同步阻塞IO，主要是在等在连接和读取数据的时候，需要等待，
 * 也就是代码中  server.accept();   reader.readLine();
 * 在没有连接到来的时候，就会一直等着，或者连接建立之后，没有数据发送过来，就一直等待着数据的读取
 */
public class SocketBIO {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9000,2);

        System.out.println("step 1: new ServerSocket(9000) ");

        while (true) {
            final Socket client = server.accept();
            System.out.println("step 2:client\t" + client.getPort());

            new Thread(new Runnable(){

                public void run() {
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

                }



            }).start();

        }
    }
}
