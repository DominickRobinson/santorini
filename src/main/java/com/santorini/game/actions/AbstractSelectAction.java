package com.santorini.game.actions;

public abstract class AbstractSelectAction extends AbstractTurnAction {
    @Override
    public String getName() {
        return "Select a Worker";
    }

    @Override
    public boolean isSkippable() {
        return false;
    }
}
