package com.out_of_box_games.gengine.core.jfx.assets;

public class JfxAssetData {

    private final String path;

    private final Object[] params;

    public JfxAssetData(String path, Object[] params) {
        this.path = path;
        this.params = params;
    }

    public String getPath() {
        return path;
    }

    @SuppressWarnings("unchecked")
    public <TParam> TParam getParam(int index) {
        return (TParam) params[index];
    }
}
