package io.faucette.ui_component;


import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import io.faucette.math.Vec2;
import io.faucette.scene_graph.Scene;
import io.faucette.scene_graph.Entity;
import io.faucette.transform_components.Transform2D;

import static org.junit.Assert.*;
import org.junit.*;


public class UITest {
    @Test
    public void test() {
        Scene scene = new Scene();
        UI ui = new UI();

        scene.addEntity(new Entity()
            .addComponent(new Transform2D())
            .addComponent(ui));

        scene.init();

        assertTrue(ui.contains(new Vec2(0f, 0f)));
        assertTrue(ui.contains(new Vec2(0.5f, 0.5f)));
        assertFalse(ui.contains(new Vec2(0.51f, 0.51f)));
        assertFalse(ui.contains(new Vec2(-1f, -1f)));

        assertTrue(scene.hasComponentManager(UIManager.class));
    }

    @Test
    public void testLayers() {
        Scene scene = new Scene();

        for (int i = 0; i < 10; i++) {
            UI ui = new UI();
            ui.setLayer(10);
            scene.addEntity(new Entity().addComponent(ui));
        }

        UIManager uiManager = scene.getComponentManager(UIManager.class);

        final AtomicBoolean sortCalled = new AtomicBoolean(false);
        uiManager.setLayerComparators(10, new Comparator<UI>() {
            @Override
            public int compare(UI a, UI b) {
                sortCalled.set(true);
                return 0;
            }
        });

        scene.init();

        scene.update();

        assertTrue(sortCalled.get());

        sortCalled.set(false);
        scene.update();

        assertFalse(sortCalled.get());
    }

    @Test
    public void testIterator() {
        Scene scene = new Scene();

        scene.addEntity(new Entity().addComponent(new UI().setLayer(0)));
        scene.addEntity(new Entity().addComponent(new UI().setLayer(1)));
        scene.addEntity(new Entity().addComponent(new UI().setLayer(2)));

        scene.init();

        Iterator<UI> it = scene.getComponentManager(UIManager.class).iterator();
        while (it.hasNext()) {
            it.next();
        }
    }
}
