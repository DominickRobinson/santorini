# Justification for Handling State

## Players
**Where it's stored**: Players are stored inside a `Queue` in the `Game` class.

**Justification**:  
- **Encapsulation**: Players only exist within the context of a game, so storing them in `Game` ensures that all player logic is encapsulated within a single source. 
- **Low Coupling, High Cohesion**: `Game` already manages turn logic, so keeping players inside `Game` prevents unnecessary coupling between `Player` and other classes like `Grid`.  
- **Information Expert**: The `Game` class is responsible for managing the overall game state, so it the most appropriate place to track all players in my opinion.

**Alternative considered**: I considered letting `Grid` store players, but this violates **Information Expert** and the role of `Grid`, which should not have enough information to track the players (it's `Game`'s responsibility).

## Current player
**Where it's stored**: The current player is stored in a `Player` attribute inside `Game`.

**Justification**:  
- **Encapsulation**: Because `Game` determines turn order, storing `currentPlayer` here prevents `Player` or any other class from needing to access turn logic.  
- **Low Coupling**: If `Player` were responsible for storing the current turn, we’d have unnecessary dependencies where each `Player` would need to check the turn order instead of one overseeing `Game` class.  

**Alternative considered**: Instead of explicitly storing `currentPlayer`, I could have computed it dynamically using `players.get(turn % players.size())`. However, I decided to do it with a `Queue` to increase flexibility for possible future mechanics with God cards (such as the ability to skip turns or take multiple consecutive turns).

## Worker locations
**Where it's stored**: Each `Worker` stores a reference to a `Field`, and each `Field` stores a reference to the `Worker` occupying it.

**Justification**:  
- **Information Expert**: The `Worker` is responsible for movement, while `Field` is responsible for tracking occupancy. This division ensures that neither class has excessive responsibility.  

**Alternative considered**: I considered making this a one-way relationship (either only `Worker` tracks `Field` or vice versa), but this seemed impossible because:
- If only `Field` tracked the `Worker`, `Worker` would have to search through the board to find itself.  
- If only `Worker` tracked its `Field`, `Board` would have to loop over all tiles to determine which `Fields` are unoccupied and thus possible valid moves.  

## Towers
**Where it's stored**: Each `Field` has a `Tower` object.

**Justification**:  
- **Encapsulation**: Since `Towers` are always (and only ever) built on `Fields`, it makes sense for `Field` to contain the `Tower` rather than keeping a separate structure in `Board` or anywhere else.
- **High Cohesion**: `Field` is responsible for checking whether it's occupied and what structures exist on it.  

**Alternative considered**: My first design stored **tower height as an `int` inside `Field`**, but this violated **Encapsulation** because `Field` would have to track dome status.

## Winner
**Where it's stored**: A `Player` reference for `winner` is stored inside `Game`.

**Justification**:  
- **Encapsulation**: The concept of a "winner" is part of the game state, not an attribute for any class not involved in making/enforcing the rules. 
- **Low Coupling**: If `Player` were responsible for tracking whether they won, there could be unnecessary dependencies.

**Alternative considered**: I could have stored a **boolean `hasWon` in `Player`**, but, as mentioned earlier, increases coupling. 


## Design goals/principles/heuristics considered
1. **Separation of Concerns**:  
   - `Game` handles turn logic and victory conditions.  
   - `Grid` just stores `Field`'s.  
   - `Player` only manages its own workers.  

2. **Encapsulation**:  
   - `Worker` knows its own location.  
   - `Field` knows its own occupancy (`Tower` and `Worker`).  
   - `Game` manages turn-based mechanics without exposing how turns work.  

3. **Low Coupling, High Cohesion**:  
   - **High cohesion**: `Game` only tracks game state, `Grid` only tracks tiles.  
   - **Low coupling**: `Player` does not need to understand board structure, and `Grid` does not need to track player turns.  


## Alternatives considered and analysis of trade-offs

**Tracking `Field` positions in `Grid` vs. `Position` class**  
- **Alternative**: Instead of storing positions using `Position`, I initially structured `Grid` as a `List`.  
- **Trade-off**: I did this to increase flexibility for possible future changes in anticipation of God cards. Namely, in case a `Player` can move a `Worker` to a non-adjacent `Field`.

**Representing `Tower` as an `int` instead of a separate class**  
- **Alternative**: I initially stored tower height as a simple `int` within `Field`.  
- **Trade-off**: I felt that a dedicated `Tower` class was needed to delegate the responsibilities of tracking both height of tower and whether the tower has a dome.  
