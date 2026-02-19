package com.narxoz.rpg.builder;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.enemy.DragonBoss;
import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BossEnemyBuilder implements EnemyBuilder {
    private String name;
    private Integer health;

    private int damage = 500;
    private int defense = 200;
    private int speed = 50;

    private String element = "NONE";
    private String aiBehavior = "BOSS";

    private final List<Ability> abilities = new ArrayList<>();
    private LootTable lootTable;

    private final Map<Integer, Integer> phases = new HashMap<>();


    private boolean canFly = true;
    private boolean hasBreathAttack = true;
    private int wingspan = 20;

    public BossEnemyBuilder setCanFly(boolean canFly) {
        this.canFly = canFly; return this;
    }

    public BossEnemyBuilder setHasBreathAttack(boolean hasBreathAttack) {
        this.hasBreathAttack = hasBreathAttack; return this;
    }

    public BossEnemyBuilder setWingspan(int wingspan) {
        this.wingspan = wingspan; return this;
    }

    @Override
    public EnemyBuilder reset() {
        name = null;
        health = null;
        damage = 500;
        defense = 200;
        speed = 50;
        element = "NONE";
        aiBehavior = "BOSS";
        abilities.clear();
        lootTable = null;
        phases.clear();
        canFly = true;
        hasBreathAttack = true;
        wingspan = 20;
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
        phases.put(phaseNumber, healthThreshold);
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

        int p1 = phases.getOrDefault(1, health);
        int p2 = phases.getOrDefault(2, Math.max(1, health / 2));
        int p3 = phases.getOrDefault(3, Math.max(1, health / 5));

        return new DragonBoss(
                name,
                health,
                damage,
                defense,
                speed,
                element,
                new ArrayList<>(abilities),
                p1, p2, p3,
                lootTable,
                aiBehavior,
                canFly,
                hasBreathAttack,
                wingspan
        );
    }
}
