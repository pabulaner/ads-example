package com.out_of_box_games.firewall.data.wave;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.gengine.data.Data;

import java.util.List;

public class WaveGroupData implements Data {

    private EnemyType type;

    private int count;

    private float start;

    private float spacing;

    private List<EnemyType> children;
}
