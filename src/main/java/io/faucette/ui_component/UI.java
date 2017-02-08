package io.faucette.ui_component;


import io.faucette.math.Vec2;
import io.faucette.math.Mat32;
import io.faucette.scene_graph.Entity;
import io.faucette.scene_graph.Component;
import io.faucette.scene_graph.ComponentManager;


public class UI extends Component {
    private boolean visible;

    private int layer;
    private int z;

    private float alpha;

    private Integer image;

    private float width;
    private float height;

    private float x;
    private float y;
    private float w;
    private float h;


    public UI() {
        super();

        visible = true;

        layer = 0;
        z = 0;

        alpha = 1f;

        image = new Integer(-1);

        width = 1f;
        height = 1f;

        x = 0f;
        y = 0f;
        w = 1f;
        h = 1f;
    }

    @Override
    public Class<? extends ComponentManager> getComponentManagerClass() {
        return UIManager.class;
    }
    @Override
    public ComponentManager createComponentManager() {
        return new UIManager();
    }

    @Override
    public Component clear() {

        visible = true;

        layer = 0;
        z = 0;

        alpha = 1f;

        image = new Integer(-1);

        width = 1f;
        height = 1f;

        x = 0f;
        y = 0f;
        w = 1f;
        h = 1f;

        return this;
    }

    public boolean getVisible() { return visible; }
    public UI setVisible(boolean visible) { this.visible = visible; return this; }

    public int getLayer() { return layer; }
    public UI setLayer(int layer) {
        UIManager uiManager = (UIManager) getComponentManager();

        if (uiManager != null) {
            layer = layer < 0 ? 0 : layer;

            if (layer != this.layer) {
                uiManager.removeComponent(this);
                this.layer = layer;
                uiManager.addComponent(this);
                uiManager.setDirtyLayer(layer);
            }
        } else {
            this.layer = layer < 0 ? 0 : layer;
        }

        return this;
    }

    public int getZ() { return z; }
    public UI setZ(int z) { this.z = z; return this; }

    public float getAlpha() { return alpha; }
    public UI setAlpha(float alpha) { this.alpha = alpha; return this; }

    public Integer getImage() { return image; }
    public UI setImage(Integer image) { this.image = image; return this; }

    public float getWidth() { return width; }
    public UI setWidth(float width) { this.width = width; return this; }

    public float getHeight() { return height; }
    public UI setHeight(float height) { this.height = height; return this; }

    public float getX() { return x; }
    public UI setX(float x) { this.x = x; return this; }

    public float getY() { return y; }
    public UI setY(float y) { this.y = y; return this; }

    public float getW() { return w; }
    public UI setW(float w) { this.w = w; return this; }

    public float getH() { return h; }
    public UI setH(float h) { this.h = h; return this; }
}
