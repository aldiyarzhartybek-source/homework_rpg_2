package com.narxoz.rpg.loot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FireLootTable implements LootTable {
    private final List<String> items;
    private final int goldDrop;
    private final int experienceDrop;

    public FireLootTable() {
        this.items = List.of("Fire Gem", "Dragon Scale", "Flame Rune");
        this.goldDrop = 300;
        this.experienceDrop = 800;
    }

    private FireLootTable(List<String> items, int goldDrop, int experienceDrop) {
        this.items = new ArrayList<>(items);
        this.goldDrop = goldDrop;
        this.experienceDrop = experienceDrop;
    }

    @Override
    public List<String> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public int getGoldDrop() {
        return goldDrop;
    }

    @Override
    public int getExperienceDrop() {
        return experienceDrop;
    }

    @Override
    public LootTable clone() {
        return new FireLootTable(items, goldDrop, experienceDrop);
    }
}
