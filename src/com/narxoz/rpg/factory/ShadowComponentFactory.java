package com.narxoz.rpg.factory;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.combat.ShadowStrike;
import com.narxoz.rpg.combat.Vanish;
import com.narxoz.rpg.loot.LootTable;
import com.narxoz.rpg.loot.ShadowLootTable;

import java.util.List;

public final class ShadowComponentFactory implements EnemyComponentFactory {
    @Override
    public String getElement() {
        return "SHADOW";
    }

    @Override
    public String createAIBehavior() {
        return "TACTICAL";
    }

    @Override
    public List<Ability> createAbilities() {
        return List.of(new ShadowStrike(), new Vanish());
    }

    @Override
    public LootTable createLootTable() {
        return new ShadowLootTable();
    }
}
