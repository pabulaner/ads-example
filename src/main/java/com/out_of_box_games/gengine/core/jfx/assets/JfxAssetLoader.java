package com.out_of_box_games.gengine.core.jfx.assets;

import com.out_of_box_games.gengine.core.api.assets.Asset;
import com.out_of_box_games.gengine.core.api.assets.AssetLoader;
import com.out_of_box_games.gengine.core.api.assets.Font;
import com.out_of_box_games.gengine.core.api.assets.Texture;
import com.out_of_box_games.gengine.util.ClassFactory;

public class JfxAssetLoader implements AssetLoader {

    private final ClassFactory<JfxAssetData, Asset> loaders;

    public JfxAssetLoader() {
        loaders = new ClassFactory<>();

        loaders.addFactory(Texture.class, JfxTexture::new);
        loaders.addFactory(Font.class, JfxFont::new);
    }

    @Override
    public <TAsset extends Asset> TAsset load(Class<TAsset> assetClass, String path, Object... params) {
        JfxAssetData data = new JfxAssetData(path, params);
        return loaders.create(assetClass, data);
    }
}
