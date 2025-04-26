import React from "react";
import "./HelpScreen.css";
import "./Tile.css";

function HelpScreen({ onClose }) {
  const GOD_CARDS = {
    apollo: "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.",
    artemis: "Your Worker may move one additional time, but not back to its initial space.",
    // athena: "During opponent’s turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.",
    atlas: "Your Worker may build a dome at any level.",
    demeter: "Your Worker may build one additional time, but not on the same space.",
    hephaestus: "Your Worker may build one additional block (not dome) on top of your first block.",
    // hermes: "If your Workers do not move up or down, they may each move any number of times (even zero), and then either builds.",
    minotaur: "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.",
    pan: "You also win if your Worker moves down two or more levels.",
    prometheus: "If your Worker does not move up, it may build both before and after moving.",
    none: "No special abilities..."
  };

  return (
    <div>
      <div className="help-overlay" onClick={onClose} />
      <div className="help-panel">
        <h2>How to Play</h2>
        <p>
          <strong>Santorini</strong> is a two-player strategy game played on a 5x5 grid.
          Each player controls two workers who build and climb towers. The first
          worker to climb onto a level 3 tower wins the game.
        </p>

        <h3>Default Rules (without God Cards)</h3>
        <ul>
          <li><strong>Starting:</strong> Each player takes turns placing 2 workers on any unoccupied tiles.</li>
          <li><strong>Turns:</strong> On your turn:
            <ol>
              <li><strong>Select</strong> a worker you own.</li>
              <li><strong>Move</strong> that worker to an adjacent unoccupied tile. You can move up only one level at a time.</li>
              <li><strong>Build</strong> on an adjacent unoccupied tile. A dome is automatically built on top of level 3 towers.</li>
            </ol>
          </li>
          <li><strong>Winning:</strong> Move one of your workers onto a level 3 tower.</li>
          <li><strong>Blocked:</strong> If you cannot make any valid moves, you lose.</li>
        </ul>

        <h3>Tower Levels</h3>
        <ul className="icons">
          <li><span className="tile level-0 highlight-sample" /> - Level 0</li>
          <li><span className="tile level-1 highlight-sample" /> - Level 1</li>
          <li><span className="tile level-2 highlight-sample" /> - Level 2</li>
          <li><span className="tile level-3 highlight-sample" /> - Level 3</li>
        </ul>

        <h3>Tile Highlights</h3>
        <ul className="icons">
          <li><span className="highlight-sample highlight-spawn" /> - Valid spawn location</li>
          <li><span className="highlight-sample highlight-action" /> - Valid select, move, build</li>
          <li><span className="highlight-sample highlight-selected-worker" /> - Currently selected worker</li>
          <li><span className="highlight-sample highlight-win" /> - Winning move</li>
        </ul>

        <h3>Icons</h3>
        <ul className="icons">
            <li>
                <img src="/sprites/player1.svg" alt="Player 1" className="icon-sample" /> - Player 1
            </li>
            <li>
                <img src="/sprites/worker1.svg" alt="Player 1's Worker" className="icon-sample" /> - Player 1's Worker
            </li>
            <li>
                <img src="/sprites/player2.svg" alt="Player 2" className="icon-sample" /> - Player 2
            </li>
            <li>
                <img src="/sprites/worker2.svg" alt="Player 2's Worker" className="icon-sample" /> - Player 2's Worker
            </li>
            <li>
            <img src="/sprites/dome.svg" alt="Dome" className="icon-sample" /> - Dome
            </li>
        </ul>

        <h3>God Cards</h3>
        <ul className="icons">
          {Object.entries(GOD_CARDS).map(([key, desc]) => (
            <li>
              <img src={`/sprites/godcards/${key}.svg`} alt={key} className="icon-sample"/>
              <div>
                 - <strong>{key.charAt(0).toUpperCase() + key.slice(1)}:</strong> {desc}
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default HelpScreen;
