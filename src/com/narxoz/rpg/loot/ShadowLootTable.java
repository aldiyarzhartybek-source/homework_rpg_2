package com.narxoz.rpg.loot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ShadowLootTable implements LootTable {
    private final List<String> items;
    private final int goldDrop;
    private final int experienceDrop;

    public ShadowLootTable() {
        this.items = List.of("Shadow Gem", "Dark Essence", "Shadow Rune");
        this.goldDrop = 350;
        this.experienceDrop = 900;
    }

    private ShadowLootTable(List<String> items, int goldDrop, int experienceDrop) {
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
        return new ShadowLootTable(items, goldDrop, experienceDrop);
    }
}
