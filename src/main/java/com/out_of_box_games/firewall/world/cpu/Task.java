package com.out_of_box_games.firewall.world.cpu;

public class Task {

    private int id;
    
    private float usage;
    
    private float time;
    
    private Runnable onBegin;
    
    private Runnable onEnd;

    public int getId() {
        return id;
    }

    public Task setId(int id) {
        this.id = id;
        return this;
    }

    public float getUsage() {
        return usage;
    }

    public Task setUsage(float usage) {
        this.usage = usage;
        return this;
    }

    public float getTime() {
        return time;
    }

    public Task setTime(float time) {
        this.time = time;
        return this;
    }

    public Runnable getOnBegin() {
        return onBegin;
    }

    public Task setOnBegin(Runnable onBegin) {
        this.onBegin = onBegin;
        return this;
    }

    public Runnable getOnEnd() {
        return onEnd;
    }

    public Task setOnEnd(Runnable onEnd) {
        this.onEnd = onEnd;
        return this;
    }
}
