package com.example.demo.proj;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class DataProcessingUnit {
    private static final int PORT = 9090;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new SensorDataHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class SensorDataHandler implements Runnable {
    private Socket socket;

    public SensorDataHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String data = new String(buffer, 0, bytesRead);
            System.out.println("Received: " + data);
            // Process and store data (omitted for brevity)
//            RestTemplate restTemplate = new RestTemplate();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.add("Header", "header1");
//
//
//            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/sensor");
//            HttpEntity<?> entity = new HttpEntity<>(headers);
//
//            HttpEntity<String> response = restTemplate.exchange(
//                    builder.toUriString(),
//                    HttpMethod.POST,
//                    entity,
//                    String.class);


            URL url = new URL("http://localhost:8080/sensor");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String[] split = data.split(":");
            SensorData sensorData = new SensorData(split[0], split[1]);


            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(sensorData);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }









        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
