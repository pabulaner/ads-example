package com.out_of_box_games.firewall.data.map;

import com.out_of_box_games.firewall.data.RuleType;

public class RuleData {

    private RuleType type;

    private Object value;

    public RuleType getType() {
        return type;
    }

    public RuleData setType(RuleType type) {
        this.type = type;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public RuleData setValue(Object value) {
        this.value = value;
        return this;
    }
}
