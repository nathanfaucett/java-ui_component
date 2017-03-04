package io.faucette.ui_component;


import io.faucette.math.AABB2;
import io.faucette.math.Vec2;
import io.faucette.math.Mat32;
import io.faucette.scene_graph.Entity;
import io.faucette.scene_graph.Component;
import io.faucette.scene_graph.ComponentManager;
import io.faucette.transform_components.Transform2D;


public class UI extends Component {
    private boolean visible;

    private int layer;
    private int z;

    private float alpha;

    private Integer image;

    private AABB2 aabb;
    private Vec2 size;

    private float width;
    private float height;

    private float x;
    private float y;
    private float w;
    private float h;

    private String text;
    private int fontSize;
    private String font;
    private int fontColor;


    public UI() {
        super();

        visible = true;

        layer = 0;
        z = 0;

        alpha = 1f;

        image = new Integer(-1);

        aabb = new AABB2();
        size = new Vec2(1f, 1f);

        width = 1f;
        height = 1f;

        x = 0f;
        y = 0f;
        w = 1f;
        h = 1f;

        text = "";
        fontSize = 16;
        font = "Arial";
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
        size.set(1f, 1f);

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

    public AABB2 getAABB2() {
        Vec2 position = entity.getComponent(Transform2D.class).getPosition();
        aabb.fromCenterSize(position, size);
        return aabb;
    }

    public boolean contains(Vec2 point) {
        return getAABB2().contains(point);
    }
    public boolean intersects(AABB2 other) {
        return getAABB2().intersects(other);
    }

    public int getZ() { return z; }
    public UI setZ(int z) { this.z = z; return this; }

    public float getAlpha() { return alpha; }
    public UI setAlpha(float alpha) { this.alpha = alpha; return this; }

    public Integer getImage() { return image; }
    public UI setImage(Integer image) { this.image = image; return this; }

    public float getWidth() { return width; }
    public UI setWidth(float width) {
        this.width = width;
        size.x = width * 0.5f;
        return this;
    }

    public float getHeight() { return height; }
    public UI setHeight(float height) {
        this.height = height;
        size.y = height * 0.5f;
        return this;
    }

    public float getX() { return x; }
    public UI setX(float x) { this.x = x; return this; }

    public float getY() { return y; }
    public UI setY(float y) { this.y = y; return this; }

    public float getW() { return w; }
    public UI setW(float w) { this.w = w; return this; }

    public float getH() { return h; }
    public UI setH(float h) { this.h = h; return this; }

    public String getText() { return text; }
    public UI setText(String text) { this.text = text; return this; }

    public int getFontSize() { return fontSize; }
    public UI setFontSize(int fontSize) { this.fontSize = fontSize; return this; }

    public String getFont() { return font; }
    public UI setFont(String font) { this.font = font; return this; }

    public int getFontColor() { return fontColor; }
    public UI setFontColor(int fontColor) { this.fontColor = fontColor; return this; }
}
