package com.narxoz.rpg.factory;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.combat.FrostBreath;
import com.narxoz.rpg.combat.IceShield;
import com.narxoz.rpg.loot.IceLootTable;
import com.narxoz.rpg.loot.LootTable;

import java.util.List;

public final class IceComponentFactory implements EnemyComponentFactory {
    @Override
    public String getElement() {
        return "ICE";
    }

    @Override
    public String createAIBehavior() {
        return "DEFENSIVE";
    }

    @Override
    public List<Ability> createAbilities() {
        return List.of(new FrostBreath(), new IceShield());
    }

    @Override
    public LootTable createLootTable() {
        return new IceLootTable();
    }
}
