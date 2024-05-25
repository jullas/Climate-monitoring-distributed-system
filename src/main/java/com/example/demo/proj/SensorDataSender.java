package com.example.demo.proj;

import java.io.OutputStream;
import java.net.Socket;

public class SensorDataSender {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 9090;

        try (Socket socket = new Socket(serverAddress, serverPort);
             OutputStream outputStream = socket.getOutputStream()) {
            String data = "Temperature: 22.5";
            outputStream.write(data.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
