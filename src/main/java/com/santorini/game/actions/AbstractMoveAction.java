package com.santorini.game.actions;

public abstract class AbstractMoveAction extends AbstractTurnAction {
    @Override
    public String getName() {
        return "Move their Worker";
    }

    @Override
    public boolean isSkippable() {
        return false;
    }
}
