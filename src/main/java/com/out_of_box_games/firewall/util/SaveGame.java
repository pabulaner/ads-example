package com.out_of_box_games.firewall.util;

import com.gluonhq.attach.storage.StorageService;
import com.out_of_box_games.firewall.data.game.GameData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class SaveGame {

    private static final File DIRECTORY = StorageService.create()
            .map(StorageService::getPrivateStorage)
            .map(Optional::orElseThrow)
            .orElse(new File("."));

    public static void save(Type type, int id, GameData data) {
        try (
                FileOutputStream fos = new FileOutputStream(getFile(type, id));
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(data);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameData load(Type type, int id) {
        try (
                FileInputStream fis = new FileInputStream(getFile(type, id));
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (GameData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static void remove(Type type, int id) {
        getFile(type, id).delete();
    }

    private static File getFile(Type type, int id) {
        return new File(DIRECTORY, "game_" + type + "_" + id + ".data");
    }

    public enum Type {

        EDITION,
        USER
    }
}
