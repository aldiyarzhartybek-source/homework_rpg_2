package com.narxoz.rpg.loot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class IceLootTable implements LootTable {
    private final List<String> items;
    private final int goldDrop;
    private final int experienceDrop;

    public IceLootTable() {
        this.items = List.of("Ice Gem", "Frost Scale", "Ice Rune");
        this.goldDrop = 260;
        this.experienceDrop = 780;
    }

    private IceLootTable(List<String> items, int goldDrop, int experienceDrop) {
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
        return new IceLootTable(items, goldDrop, experienceDrop);
    }
}
