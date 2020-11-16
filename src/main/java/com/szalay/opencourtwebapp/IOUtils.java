package com.szalay.opencourtwebapp;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class IOUtils {

    private static final JsonParser myParser = new BasicJsonParser();

    //GENERAL HELPER METHODS

    public static String readString(String filepath) {
        try {
            return Files.readString(Path.of(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readRtf(String filePath) {
        String result;
        File file = new File(filePath);
        try {
            DefaultStyledDocument styledDoc = new DefaultStyledDocument();
            InputStream is = new FileInputStream(file);
            new RTFEditorKit().read(is, styledDoc, 0);
            result = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes(StandardCharsets.UTF_8));
        } catch (IOException | BadLocationException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static Map<String, Object> parseJSONToMap(String filePath) {
        return IOUtils.myParser.parseMap(readString(filePath));
    }

    public static List<Object> parseJSONToList(String filePath) {
        return IOUtils.myParser.parseList(readString(filePath));
    }

    public static void download(String urlStr, String file) throws IOException, MuteURLException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        if (connection.getResponseCode() >= 500) {
            throw new MuteURLException("HTTP request returned with response code: " + connection.getResponseCode());
        }
        InputStream myInputStream = connection.getInputStream();
        File myNewDecision = new File(file);
        myNewDecision.getParentFile().mkdirs();
        FileOutputStream myOutputStream = new FileOutputStream(myNewDecision);
        myInputStream.transferTo(myOutputStream);
        myOutputStream.close();
        myInputStream.close();
    }

    public static void writeString(String tobewritten, String filepath, boolean append) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath, append))) {
            bufferedWriter.write(tobewritten);
        } catch (IOException iOEx) {
            iOEx.printStackTrace();
        }
    }
}
