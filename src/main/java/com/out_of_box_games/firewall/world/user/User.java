package com.out_of_box_games.firewall.world.user;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.gengine.util.collection.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class User {

    private static final Logger LOGGER = Logger.getLogger(User.class.getName());

    private static final char ESCAPE = '\\';

    private final String name;

    private String value;

    private int index;

    public User(String name) {
        this.name = name;
        this.value = "";
        this.index = 0;
    }

    public UserRoot load() {
        try (InputStream input = User.class.getResourceAsStream("/users/" + name + "/root")) {
            if (input == null) {
                return null;
            }

            value = new String(input.readAllBytes()).strip();
            index = 0;

            String url = "";
            float cash = 0.0f;

            while (index < value.length()) {
                Pair<String, String> pair = parse();
                String second = pair.getSecond();

                switch (pair.getFirst()) {
                    case "url" -> url = second;
                    case "cash" -> cash = Float.parseFloat(second);
                }
            }

            return new UserRoot(url, cash, load(url));
        } catch (IOException e) {
            LOGGER.severe(e.toString());
            return null;
        }
    }

    public UserInfo load(String url) {
        try (InputStream input = User.class.getResourceAsStream("/users/" + name + "/" + url)) {
            if (input == null) {
                return null;
            }

            value = new String(input.readAllBytes());
            index = 0;
        } catch (IOException e) {
            LOGGER.severe(e.toString());
            return null;
        }

        int waves = 0;
        List<EnemyType> enemies = List.of();
        List<String> choices = List.of();
        String content = "";

        while (index < value.length()) {
            Pair<String, String> pair = parse();
            String second = pair.getSecond();

            switch (pair.getFirst()) {
                case "waves" -> waves = Integer.parseInt(second);
                case "enemies" -> enemies = Arrays.stream(second.split(","))
                        .map(String::strip)
                        .map(EnemyType::valueOf)
                        .toList();
                case "choices" -> choices = Arrays.stream(second.split(","))
                        .map(String::strip)
                        .filter(value -> !value.isEmpty())
                        .toList();
                case "content" -> content = second;
            }
        }

        return new UserInfo(waves, enemies, choices, content);
    }

    private Pair<String, String> parse() {
        expect('@');
        String key = until(':');

        expect(':');
        String value = until('@');

        return new Pair<>(key.strip(), value.strip());
    }

    private char next() {
        return value.charAt(index++);
    }

    private char peek() {
        return value.charAt(index);
    }

    private void expect(char expected) {
        char actual = next();

        if (actual != expected) {
            throw new RuntimeException("Expected: " + expected + ", actual: " + actual);
        }
    }

    private String until(Character... end) {
        List<Character> endList = Arrays.asList(end);
        int begin = index;

        while (index < value.length() && !endList.contains(peek())) {
            if (peek() == ESCAPE) {
                index++;
            }

            index++;
        }

        String result = value.substring(begin, index);

        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == ESCAPE && i + 1 < result.length()) {
                result = result.substring(begin, i) + result.substring(i + 1);
            }
        }

        return result;
    }
}
