package com.out_of_box_games.firewall.util;

import com.out_of_box_games.firewall.data.game.GameData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveGame {

    public static void save(int id, GameData data) {
        try (
                FileOutputStream fos = new FileOutputStream(getFileName(id));
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(data);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameData load(int id) {
        try (
                FileInputStream fis = new FileInputStream(getFileName(id));
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (GameData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static void remove(int id) {
        try {
            Files.delete(Path.of(getFileName(id)));
        } catch (IOException ignore) {
            // empty
        }
    }

    private static String getFileName(int id) {
        return "game" + id + ".data";
    }
}
