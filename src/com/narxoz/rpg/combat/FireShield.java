package com.narxoz.rpg.combat;

public final class FireShield implements Ability {

    @Override public String getName() {
        return "Fire Shield";
    }

    @Override public int getDamage() {
        return 0;
    }

    @Override public String getDescription() {
        return "Defensive buff, reduces incoming damage with fire aura.";
    }

    @Override public Ability clone() {
        return new FireShield();
    }
}
