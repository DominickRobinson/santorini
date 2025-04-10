import React from "react";
import "./HelpScreen.css";
import "./Tile.css";

function HelpScreen({ onClose }) {
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
        <ul>
          <li><span className="tile level-0 highlight-sample" /> Level 0</li>
          <li><span className="tile level-1 highlight-sample" /> Level 1</li>
          <li><span className="tile level-2 highlight-sample" /> Level 2</li>
          <li><span className="tile level-3 highlight-sample" /> Level 3</li>
        </ul>

        <h3>Tile Highlights</h3>
        <ul>
          <li><span className="highlight-sample highlight-spawn" /> Valid spawn location</li>
          <li><span className="highlight-sample highlight-select" /> Selectable worker</li>
          <li><span className="highlight-sample highlight-move" /> Valid move</li>
          <li><span className="highlight-sample highlight-build" /> Valid build</li>
          <li><span className="highlight-sample highlight-win" /> Winning move</li>
        </ul>

        <h3>Icons</h3>
        <ul>
            <li>
                <img src="/sprites/player1.svg" alt="Player 1" className="highlight-sample icon-sample" /> - Player 1
            </li>
            <li>
                <img src="/sprites/worker1.svg" alt="Player 1's Worker" className="highlight-sample icon-sample" /> - Player 1's Worker
            </li>
            <li>
                <img src="/sprites/player2.svg" alt="Player 2" className="highlight-sample icon-sample" /> - Player 2
            </li>
            <li>
                <img src="/sprites/worker2.svg" alt="Player 2's Worker" className="highlight-sample icon-sample" /> - Player 2's Worker
            </li>
            <li>
                <img src="/sprites/dome.svg" alt="Dome" className="highlight-sample icon-sample" style={{ backgroundColor: "#333", borderRadius: "50%" }} />  - Dome
            </li>
        </ul>
      </div>
    </div>
  );
}

export default HelpScreen;
