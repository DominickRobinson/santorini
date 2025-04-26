package com.santorini.game.actions;

public abstract class AbstractBuildAction extends AbstractTurnAction {
    @Override
    public String getName() {
        return "Build a Tower";
    }

    @Override
    public boolean isSkippable() {
        return false;
    }
}