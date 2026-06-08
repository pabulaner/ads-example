package com.out_of_box_games.firewall.data.wave;

import com.out_of_box_games.gengine.data.Data;

import java.util.List;

public class WaveData implements Data {

    private int index;

    private List<WaveEntryData> entries;

    public int getIndex() {
        return index;
    }

    public WaveData setIndex(int index) {
        this.index = index;
        return this;
    }

    public List<WaveEntryData> getEntries() {
        return entries;
    }

    public WaveData setEntries(List<WaveEntryData> entries) {
        this.entries = entries;
        return this;
    }
}
