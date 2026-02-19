package com.narxoz.rpg.builder;

import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.factory.EnemyComponentFactory;

public final class EnemyDirector {
    private EnemyBuilder builder;

    public EnemyDirector(EnemyBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(EnemyBuilder builder) {
        this.builder = builder;
    }

    public Enemy createMinion(EnemyComponentFactory factory) {
        return builder.reset()
                .setName(factory.getElement() + " Goblin")
                .setHealth(120)
                .setDamage(15)
                .setDefense(8)
                .setSpeed(25)
                .setElement(factory.getElement())
                .setAbilities(factory.createAbilities())
                .setLootTable(factory.createLootTable())
                .setAI(factory.createAIBehavior())
                .build();
    }

    public Enemy createElite(EnemyComponentFactory factory) {
        return builder.reset()
                .setName(factory.getElement() + " Elite Goblin")
                .setHealth(400)
                .setDamage(45)
                .setDefense(25)
                .setSpeed(30)
                .setElement(factory.getElement())
                .setAbilities(factory.createAbilities())
                .setLootTable(factory.createLootTable())
                .setAI(factory.createAIBehavior())
                .build();
    }

    public Enemy createMiniBoss(EnemyComponentFactory factory) {
        return builder.reset()
                .setName(factory.getElement() + " MiniBoss")
                .setHealth(2500)
                .setDamage(120)
                .setDefense(70)
                .setSpeed(28)
                .setElement(factory.getElement())
                .setAbilities(factory.createAbilities())
                .setLootTable(factory.createLootTable())
                .setAI(factory.createAIBehavior())
                .addPhase(1, 2500)
                .addPhase(2, 1200)
                .addPhase(3, 500)
                .build();
    }

    public Enemy createRaidBoss(EnemyComponentFactory factory) {
        return builder.reset()
                .setName(factory.getElement() + " Ancient Dragon")
                .setHealth(50000)
                .setDamage(500)
                .setDefense(200)
                .setSpeed(50)
                .setElement(factory.getElement())
                .setAbilities(factory.createAbilities())
                .setLootTable(factory.createLootTable())
                .setAI(factory.createAIBehavior())
                .addPhase(1, 50000)
                .addPhase(2, 30000)
                .addPhase(3, 15000)
                .build();
    }
}
