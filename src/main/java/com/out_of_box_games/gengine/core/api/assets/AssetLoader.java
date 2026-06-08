package com.out_of_box_games.gengine.core.api.assets;

public interface AssetLoader {

    <TAsset extends Asset> TAsset load(Class<TAsset> assetClass, String path, Object... params);
}
