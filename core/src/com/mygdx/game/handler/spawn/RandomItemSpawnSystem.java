package com.mygdx.game.handler.spawn;

import com.badlogic.gdx.physics.box2d.World;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomItemSpawnSystem<Item> {

    private class Entry {
        Class<Item> type;
        int accumulatedWeight;

        Item getItem(Class<Item> clazz, World world) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
            return clazz.getConstructor(world.getClass()).newInstance(world);
        }

    }

    private List<Entry> entries = new ArrayList<>();
    private int accumulatedWeight;
    private Random rand = new Random();

    public void addEntry(Class<Item> type, float weight) {
        accumulatedWeight += weight;
        Entry e = new Entry();
        e.type = type;
        e.accumulatedWeight = accumulatedWeight;
        entries.add(e);
    }

    public Item getRandomItem(World world) {
        float r = rand.nextFloat() * accumulatedWeight;

        for (Entry entry: entries) {
            if (entry.accumulatedWeight >= r) {
                try {
                    return entry.getItem(entry.type, world);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null; //should only happen when there are no entries
    }

}
