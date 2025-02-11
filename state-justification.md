# Justification for handling state
Below, describe where you stored each of the following states and justify your answers with design principles/goals/heuristics/patterns. Discuss the alternatives and trade-offs you considered during your design process.

## Players
The players are stored inside of a list in the Game class. I did this because Game's responsibility is to store information about the game state and handle rules. Players only really exist inside of the context of the game, which is why I put them there.

I did not consider any alternatives to this. I don't really know where else you would store the Players.


## Current player
The current player is stored in the Game class. Current player only exists within the context of the game, and because Game tracks the state of the game with respect to the turns and rules, it made sense to track current player in Game.

I did not consider any alternatives to this. I don't really know where else you would track the current player.


## Worker locations
I gave each worker a Field attribute, where Field represents one tile on the board. I did this because I knew that I eventually needed to check for valid and/or unoccupied adjacent Fields, and so I figured that this information should be accessible from the Field. I also felt that it was the responsibility of Field to track what occupies itself, including any Workers or Towers.

I also have Worker track which Field it is on, so that a Player can locate its workers. I am worried that this makes my code highly coupled at this point. I considered having one way relationships (either Field is aware of its occupant Worker while the Worker is unaware of where it is at, or vice versa), but not doing this led to some other coupling elsewhere in my code, so I decided to stick with this.


## Towers
Each tile on the board can have a tower built on it. As a result, I decided to give each tile (referred to as Field in my implementation) a Tower object. Towers only correspond to a tile on the board. Also, I figured that because each worker is aware of the Field it occupies, then a Worker would be able to build upon a Tower by getting the Tower corresponding to its Field.

I considered having Tower simply be an int property on Field, but I felt that Tower was a distinct enough concept from Field to make it its own object. Later, once I realized that I needed some other information about Tower, such as whether it has a dome on it, I decided to create the Tower object to DELEGATE that responsibility.


## Winner
The winner of the game (if it exists yet) is stored in Game. Game contains information about the rules and turns of the game, and a winner only exists in the context of a game, so it makes sense to put winner there.

I did not consider any alternatives to this. I don't know where else you would track the Winner.


## Design goals/principles/heuristics considered
I tried my best to keep coupling low and cohesion high. However, I am worried that I have some instances in my code where there are long chains of getter methods because I was very concerned about high cohesion. The most notable example of this in my code might relate to my Position class, because I felt that I needed a way to identify Fields in the grid without publically revealing that the Fields could be stored in a List of Lists (this was my initial approach, but I changed it later). As a result, there are examples in my code where I do something like "worker.getField().getPosition().getX()" which seems a bit coupled.

I tried my best to consider other design patterns we learned, such as Strategy or Template, but in this barebones version of the game without AI opponents or variations on the game, I did not come across a need for them yet.


## Alternatives considered and analysis of trade-offs
My initial design had Dome and Block extend Piece, which compose a Tower. However, once I started implementing this, I realized that the Pieces did not have any properties or methods. I also realized that for Towers with initial heights of 0, 1, or 2, building always entails placing a block, and for a Tower with initial height of 3, building always entails adding a Dome. Based on this, I felt that it was overkill to create all of these objects when I could simply track the number of levels with an int in Tower. I felt that I could easily refactor my code if I eventually need to modify my game and create Piece objects to compose the Towers.
