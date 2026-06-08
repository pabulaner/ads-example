package com.out_of_box_games.gengine.world.component;

public class PathFollowComponent extends TransformComponent {

    private float progress;

    public PathFollowComponent() {
        progress = 0.0f;
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);

        PathComponent path = getParent();
        setTranslation(path.sample(progress));
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public float getProgressRatio() {
        PathComponent path = getParent();
        return progress / path.length();
    }

    public void setProgressRatio(float progressRatio) {
        PathComponent path = getParent();
        progress = progressRatio * path.length();
    }
}
