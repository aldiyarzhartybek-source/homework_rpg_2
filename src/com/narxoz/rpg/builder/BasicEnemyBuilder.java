package com.narxoz.rpg.builder;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.enemy.Goblin;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.List;

public final class BasicEnemyBuilder implements EnemyBuilder {
    private String name;
    private Integer health;

    private int damage = 15;
    private int defense = 5;
    private int speed = 35;

    private String element = "NONE";
    private String aiBehavior = "BASIC";

    private final List<Ability> abilities = new ArrayList<>();
    private LootTable lootTable;

    @Override
    public EnemyBuilder reset() {
        name = null;
        health = null;
        damage = 15;
        defense = 5;
        speed = 35;
        element = "NONE";
        aiBehavior = "BASIC";
        abilities.clear();
        lootTable = null;
        return this;
    }

    @Override public EnemyBuilder setName(String name) {
        this.name = name; return this;
    }

    @Override public EnemyBuilder setHealth(int health) {
        this.health = health; return this;
    }

    @Override public EnemyBuilder setDamage(int damage) {
        this.damage = damage; return this;
    }

    @Override public EnemyBuilder setDefense(int defense) {
        this.defense = defense; return this;
    }

    @Override public EnemyBuilder setSpeed(int speed) {
        this.speed = speed; return this;
    }

    @Override public EnemyBuilder setElement(String element) {
        this.element = element; return this;
    }

    @Override
    public EnemyBuilder addAbility(Ability ability) {
        if (ability != null) abilities.add(ability);
        return this;
    }

    @Override
    public EnemyBuilder setAbilities(List<Ability> abilities) {
        this.abilities.clear();
        if (abilities != null) this.abilities.addAll(abilities);
        return this;
    }

    @Override
    public EnemyBuilder addPhase(int phaseNumber, int healthThreshold) {

        return this;
    }

    @Override
    public EnemyBuilder setLootTable(LootTable loot) {
        this.lootTable = loot;
        return this;
    }

    @Override
    public EnemyBuilder setAI(String aiBehavior) {
        this.aiBehavior = aiBehavior;
        return this;
    }

    @Override
    public Enemy build() {

        if (name == null || name.isBlank())
            throw new IllegalStateException("Name is required!");
        if (health == null || health <= 0)
            throw new IllegalStateException("Health must be positive!");

        Goblin goblin = new Goblin(name, health, damage, defense, speed);
        goblin.setElement(element);
        goblin.setAIBehavior(aiBehavior);
        goblin.setLootTable(lootTable);

        for (Ability a : abilities) goblin.addAbility(a);
        return goblin;
    }
}
