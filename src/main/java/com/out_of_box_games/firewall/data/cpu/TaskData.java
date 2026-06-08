package com.out_of_box_games.firewall.data.cpu;

public class TaskData {

    private int tower;

    private float usage;

    private float time;

    public int getTower() {
        return tower;
    }

    public TaskData setTower(int tower) {
        this.tower = tower;
        return this;
    }

    public float getUsage() {
        return usage;
    }

    public TaskData setUsage(float usage) {
        this.usage = usage;
        return this;
    }

    public float getTime() {
        return time;
    }

    public TaskData setTime(float time) {
        this.time = time;
        return this;
    }
}
