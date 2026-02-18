package com.narxoz.rpg.combat;

public interface Ability {

    String getName();

    AbilityType getType();

    String getDescription();


    int getPower();

    int getCooldownSeconds();

    Ability clone();


    default String getInfo() {
        return String.format(
                "%s [%s] power=%d cd=%ds — %s",
                getName(), getType(), getPower(), getCooldownSeconds(), getDescription()
        );
    }
}
