# Behavioral Contract (Minotaur God Card)

## Operation

`move(Worker worker, Tile to): void`

## Assumptions

- The current `player` has a `MinotaurCard`.
- The opposing `player` has no `GodCard`.

## Preconditions

- The `game` is not over.
- The `game` is in the TURN_ACTION phase.
- The `worker` is selected and belongs to the **current player**.
- The `worker` is currently placed on a valid `Tile`.
- The `to` `Tile` is adjacent to the worker's current `Tile.
- One of the following must be true:
  - `to` is unoccupied, has no dome, and is at most one level higher than the current tile.
  - `to` is occupied by an enemy `Worker`, and:
    - The `Tile` directly beyond `to` (in the same direction) is a valid `Tile`.
    - That `Tile` is unoccupied.
    - That `Tile` has no dome.
    - `to`'s `Tower` is at most one level higher than the current `Tile` that the enemy `Worker` is on.


## Postconditions

- If moving into an empty `Tile`:
  - The `worker` moves to the `to` `Tile`.
- If moving into an occupied `Tile` (pushing an opponent's worker):
  - The opponent's worker is pushed into the `Tile` directly beyond `to`.
  - The `worker` moves into the `to` `Tile`.
- The original `Tile` becomes unoccupied.
- If the `worker` moves onto a `Tile` with a `Tower` of height 3 (but not by pushing), the player immediately wins and the game transitions to the `GAME_OVER` phase.
