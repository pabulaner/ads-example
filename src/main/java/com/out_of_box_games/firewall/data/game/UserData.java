package com.out_of_box_games.firewall.data.game;

import com.out_of_box_games.gengine.data.Data;

public class UserData implements Data {

    private String url;

    private int wave;

    public String getUrl() {
        return url;
    }

    public UserData setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getWave() {
        return wave;
    }

    public UserData setWave(int wave) {
        this.wave = wave;
        return this;
    }
}
