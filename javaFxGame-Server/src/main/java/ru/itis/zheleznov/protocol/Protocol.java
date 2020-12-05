package ru.itis.zheleznov.protocol;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.itis.zheleznov.models.Question;
import ru.itis.zheleznov.models.QuestionsRow;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Protocol {

    public static final byte START = 1;
    public static final byte SEND_ANSWER = 2;
    public static final byte PLAYERS = 3;
    public static final byte NEW_PLAYER = 4;
    public static final byte QUESTIONS = 5;

    public static void write(byte command, Object object, OutputStream outputStreamRaw) {
        try {
            DataOutputStream outputStream = new DataOutputStream(outputStreamRaw);
            outputStream.writeByte(command);
            ObjectMapper mapper = new ObjectMapper();
            outputStream.writeUTF(mapper.writeValueAsString(object));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(byte command, OutputStream outputStreamRaw) {
        try {
            DataOutputStream outputStream = new DataOutputStream(outputStreamRaw);
            outputStream.writeByte(command);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> Object read(byte command, InputStream inputStreamRaw, Class<T> clas) {
        try {
            DataInputStream inputStream = new DataInputStream(inputStreamRaw);
            byte commandFromInput = inputStream.readByte();
            if (commandFromInput == command) {
                ObjectMapper mapper = new ObjectMapper();
                String msg = inputStream.readUTF();
                return (T) mapper.readValue(msg, clas);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean read(byte command, InputStream inputStreamRaw) {
        try {
            DataInputStream inputStream = new DataInputStream(inputStreamRaw);
            byte commandFromInput = inputStream.readByte();
            return commandFromInput == command;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<QuestionsRow> readQuestions(byte command, InputStream inputStreamRaw) {
        try {
            DataInputStream inputStream = new DataInputStream(inputStreamRaw);
            byte commandFromInput = inputStream.readByte();
            if (commandFromInput == command) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                String msg = inputStream.readUTF();
                return convertList(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<QuestionsRow> convertList(String msg) {
        List<QuestionsRow> result = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonArray jo = (JsonArray) parser.parse(msg);
        for (int i = 0; i < jo.size(); i++) {
            JsonObject obj = (JsonObject) jo.get(i);
            QuestionsRow questionsRow = new QuestionsRow();
            questionsRow.setCategoryName(obj.get("categoryName").getAsString());
            JsonArray questions = (JsonArray) obj.get("questions");
            ArrayList<Question> questionArrayList = new ArrayList<>();
            questionsRow.setQuestions(questionArrayList);
            for (int j = 0; j < questions.size(); j++) {
                JsonObject question = (JsonObject) questions.get(j);
                questionArrayList.add(new Question(question.get("questionName").getAsString(), question.get("points").getAsShort()));
            }
            result.add(questionsRow);
        }
        return result;
    }
}
