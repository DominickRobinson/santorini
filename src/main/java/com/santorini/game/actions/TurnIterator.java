package com.santorini.game.actions;

import java.util.List;
import java.util.Iterator;

public class TurnIterator {
    private final List<TurnAction> actions;
    private Iterator<TurnAction> iterator;
    private TurnAction current;

    public TurnIterator(List<TurnAction> actions) {
        this.actions = actions;
        reset();
    }

    public void reset() {
        this.iterator = actions.iterator();
        advance();
    }

    public void advance() {
        current = iterator.hasNext() ? iterator.next() : null;
    }

    public void insertNext(TurnAction action) {
        if (current == null) {
            actions.addLast(action);
        } else {
            int index = actions.indexOf(current);
            actions.add(index + 1, action);
        }
        iterator = actions.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == current) {
                break;
            }
        }
    }

    public TurnAction getCurrent() {
        return current;
    }

    public boolean isDone() {
        return current == null;
    }

    public List<TurnAction> getAllActions() {
        return actions;
    }
}
