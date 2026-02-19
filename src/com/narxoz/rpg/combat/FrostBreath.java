package com.narxoz.rpg.combat;

public final class FrostBreath implements Ability {

    @Override public String getName() {
        return "Frost Breath";
    }

    @Override public int getDamage() {
        return 95;
    }

    @Override public String getDescription() {
        return "Damage + slow effect.";
    }

    @Override public Ability clone() {
        return new FrostBreath();
    }
}
