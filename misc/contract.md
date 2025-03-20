# Behavioral Contract

(Note: this is based on the standard rules of Santorini, which could change with the introduction of God cards.)

## Operation

move(Worker worker, Field to): void

---

## Cross References

Use Cases: A Player moves a Worker to a new Field

---

## Preconditions

 - The game is not over.
 - The game is in the move phase.
 - *worker* is already on a **Field**.
 - *worker* belongs to the **Player** calling this method.
 - The **Player** calling this method is the current player.
 - *to* is not occupied by a **Player**.
 - *to* must be adjacent to *worker*'s currently occupied field.
 - *to*'s **Tower**'s is at most one level higher than *worker*'s **Field**'s **Tower**.
 - *to*'s **Tower** does not have a dome.

---

## Postconditions

 - *worker*’s **Field** is updated to *to*.
 - *worker* is removed from its previous **Field**.
 - *worker* is added to *to*.
 - If *worker* successfully moves to *to*, and *to* has a **Tower** with height 3, then the game ends and the **Player** calling this method wins.
 - If *worker* successfully moves to *to*, and *to* does not have a **Tower** with height 3, then the game moves to the build phase where the **Player** that called this method will have *worker* build up a **Tower**.

