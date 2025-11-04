package game.core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class LevelLoader {

    // Lớp con biểu diễn 1 loại gạch

    // Lớp con biểu diễn 1 level trong game
    public static class Level {
        int level;
        String name;
        int[][] map;
    }

    /**
     * Đọc file JSON và trả về danh sách các Level
     */
    public static List<Level> loadLevels(String jsonFilePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(jsonFilePath)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            Type levelListType = new TypeToken<List<Level>>() {
            }.getType();
            return gson.fromJson(jsonObject.getAsJsonArray("levels"), levelListType);
        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file JSON: " + e.getMessage(), e);
        }
    }
}