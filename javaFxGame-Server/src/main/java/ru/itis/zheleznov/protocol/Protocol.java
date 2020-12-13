package ru.itis.zheleznov.protocol;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Button;
import ru.itis.zheleznov.dto.QuestionDto;
import ru.itis.zheleznov.models.Question;
import ru.itis.zheleznov.models.QuestionsRow;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Protocol {

    public static final byte START = 1;
    public static final byte SEND_ANSWER = 2;
    public static final byte NEW_PLAYER = 3;
    public static final byte PUT_QUESTIONS = 4;
    public static final byte MOVE = 5;
    public static final byte CHOOSE = 6;
    public static final byte NEXT_ROUND = 7;
    public static final byte CHECK_ANSWER = 8;
    public static final byte NEXT_MOVE = 9;

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

    public static <T> Object read(byte command, InputStream inputStreamRaw, OutputStream outputStreamRaw, Class<T> clas) {
        try {
            DataInputStream inputStream = new DataInputStream(inputStreamRaw);

            byte commandFromInput = inputStream.readByte();
            System.out.println(commandFromInput);
            if (commandFromInput == command) {
                ObjectMapper mapper = new ObjectMapper();
                String msg = inputStream.readUTF();
                return (T) mapper.readValue(msg, clas);
            } else {
                //write(commandFromInput, outputStreamRaw);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static QuestionDto readQuestion(byte command, InputStream inputStreamRaw, OutputStream outputStreamRaw) {
        try {
            DataInputStream inputStream = new DataInputStream(inputStreamRaw);
            byte commandFromInput = inputStream.readByte();
            if (commandFromInput == command) {
                String msg = inputStream.readUTF();
                System.out.println(msg);
                return convertQuestion(msg);
            } else {
                //write(commandFromInput, outputStreamRaw);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean read(byte command, InputStream inputStreamRaw, OutputStream outputStreamRaw) {
        try {
            DataInputStream inputStream = new DataInputStream(inputStreamRaw);
            byte commandFromInput = inputStream.readByte();
            if (commandFromInput != command) {
                //write(commandFromInput, outputStreamRaw);
            }
            return commandFromInput == command;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<QuestionsRow> readQuestions(byte command, InputStream inputStreamRaw, OutputStream outputStream) {
        try {
            DataInputStream inputStream = new DataInputStream(inputStreamRaw);
            byte commandFromInput = inputStream.readByte();
            if (commandFromInput == command) {
                String msg = inputStream.readUTF();
                return convertList(msg);
            } else {
                //write(commandFromInput, outputStream);
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
            questionsRow.setButton(new Button());
            JsonArray questions = (JsonArray) obj.get("questions");
            ArrayList<Question> questionArrayList = new ArrayList<>();
            questionsRow.setQuestions(questionArrayList);
            for (int j = 0; j < questions.size(); j++) {
                JsonObject question = (JsonObject) questions.get(j);
                questionArrayList.add(
                        new Question(question.get("questionName").getAsString(), new Button(), question.get("points").getAsShort()));
            }
            result.add(questionsRow);
        }
        return result;
    }

    private static QuestionDto convertQuestion(String msg) {
        QuestionDto result = new QuestionDto();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(msg);
        result.setQuestionName(jsonObject.get("questionName").getAsString());
        result.setPoints(jsonObject.get("points").getAsShort());
        return result;
    }
}
