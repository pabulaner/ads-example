package com.out_of_box_games.firewall.data;

public enum SpeedMode {

    _1X,
    _2X,
    _4X,
    _8X,
    _16X,
    _32X,
    _64;

    public static SpeedMode[] releaseValues() {
        return new SpeedMode[] {
                _1X,
                _2X,
                _4X,
                _8X,
                _16X
        };
    }

    public static SpeedMode[] debugValues() {
        return new SpeedMode[] {
                _1X,
                _2X,
                _4X,
                _8X,
                _16X,
                _32X,
                _64
        };
    }
}
