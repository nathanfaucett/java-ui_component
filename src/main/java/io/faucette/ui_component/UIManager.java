package io.faucette.ui_component;


import io.faucette.scene_graph.Component;
import io.faucette.scene_graph.ComponentManager;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;


public class UIManager extends ComponentManager {
    private float width;
    private float height;

    private int count;
    private List<List<UI>> layers;
    private List<Boolean> dirtyLayers;

    private List<Comparator<UI>> comparators;
    private Comparator<UI> defaultComparator = new DefaultComparator();


    public class DefaultComparator implements Comparator<UI> {
        @Override
        public int compare(UI a, UI b) {
            return new Integer(a.getZ()).compareTo(new Integer(b.getZ()));
        }
    }


    public UIManager() {
        super();

        width = 960f;
        height = 640f;

        layers = new ArrayList<>();
        dirtyLayers = new ArrayList<>();
        comparators = new ArrayList<>();
        count = 0;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    public UIManager setWidthHeight(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }
    public float getWidth() {
        return this.width;
    }
    public float getHeight() {
        return this.height;
    }

    private UIManager setLayer(int layer, List<UI> layerArray) {
        if (layer >= layers.size()) {
            for (int i = layers.size(), il = layer; i < il; i++) {
                layers.add(new ArrayList<UI>());
            }
            layers.add(layerArray);
        } else {
            layers.set(layer, layerArray);
        }
        return this;
    }
    public UIManager setDirtyLayer(int layer) {
        if (layer >= dirtyLayers.size()) {
            for (int i = dirtyLayers.size(), il = layer; i < il; i++) {
                dirtyLayers.add(false);
            }
            dirtyLayers.add(true);
        } else {
            dirtyLayers.set(layer, true);
        }
        return this;
    }
    public UIManager setLayerComparators(int layer, Comparator<UI> comparator) {
        if (layer >= comparators.size()) {
            for (int i = comparators.size(), il = layer; i < il; i++) {
                comparators.add(defaultComparator);
            }
            comparators.add(comparator);
        } else {
            comparators.set(layer, comparator);
        }
        return this;
    }

    @Override
    public UIManager init() {
        sort();
        return this;
    }

    @Override
    public UIManager update() {
        sort();
        return this;
    }

    @Override
    public UIManager sort() {
        for (int i = 0, il = dirtyLayers.size(); i < il; i++) {
            if (dirtyLayers.get(i).booleanValue()) {
                dirtyLayers.set(i, false);
                Collections.sort(layers.get(i), comparators.get(i));
            }
        }
        return this;
    }

    private class UIIterator implements Iterator<UI> {
        private List<List<UI>> layers;
        private int layerIndex;
        private int index;
        private int count;


        public UIIterator(UIManager s) {
            layers = s.layers;
            layerIndex = 0;
            index = 0;
            count = s.count;
        }

        public boolean hasNext() {
            return count != 0;
        }
        public UI next() {
            if (count != 0) {
                List<UI> layer = layers.get(layerIndex);
                UI ui;

                count -= 1;

                if (index >= layer.size()) {
                    index = 0;
                    layerIndex++;
                    layer = layers.get(layerIndex);
                }

                return layer.get(index++);
            } else {
                return null;
            }
        }
        public void remove() {}
    }

    @Override
    public Iterator<UI> iterator() {
        return new UIIterator(this);
    }

    @Override
    public boolean hasComponent(Component component) {
        UI ui = (UI) component;

        try {
            List<UI> layer = layers.get(ui.getLayer());
            return layer.contains(ui);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    @Override
    public <T extends Component> UIManager addComponent(T component) {
        UI ui = (UI) component;
        int componentLayer = ui.getLayer();
        List<UI> layer;

        try {
            layer = layers.get(componentLayer);
        } catch (IndexOutOfBoundsException e) {
            layer = new ArrayList<>();
            setLayer(componentLayer, layer);

            boolean needsLayerComparator = true;
            try {
                comparators.get(componentLayer);
                needsLayerComparator = false;
            } catch (IndexOutOfBoundsException e0) {}

            if (needsLayerComparator) {
                setLayerComparators(componentLayer, defaultComparator);
            }
        }

        setDirtyLayer(componentLayer);
        layer.add(ui);
        count += 1;

        return this;
    }
    @Override
    public <T extends Component> UIManager removeComponent(T component) {
        UI ui = (UI) component;
        int componentLayer = ui.getLayer();

        try {
            List<UI> layer = layers.get(componentLayer);
            layer.remove(ui);
            count -= 1;
        } catch (IndexOutOfBoundsException e) {}

        return this;
    }
}
