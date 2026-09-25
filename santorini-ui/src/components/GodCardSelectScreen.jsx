import React, { useState } from "react";
import "./GodCardSelectScreen.css";

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
    none: "Play without a God card."
};

function GodCardSelectScreen({ onStartGameWithGodCards }) {
  const [selected, setSelected] = useState({ player1: null, player2: null });
  const [currentPlayer, setCurrentPlayer] = useState("player1");
  const [showHelp, setShowHelp] = useState(false);

  const handleSelect = (god) => {
    setSelected((prev) => ({ ...prev, [currentPlayer]: god }));
  
    setCurrentPlayer((prev) => (prev === "player1" ? "player2" : "player1"));
  };
  

  const allSelected = selected.player1 && selected.player2;

  return (
    <div className="godcard-select-screen">
      <h2>Select God Cards</h2>
      <h3 className={`turn-indicator ${currentPlayer}`}>
        {currentPlayer === "player1" ? "Player 1" : "Player 2"}: Choose your God Card
      </h3>

      <div className="god-card-grid">
        {Object.keys(GOD_CARDS).map((god) => {
          const selectedByP1 = selected.player1 === god;
          const selectedByP2 = selected.player2 === god;

          let cardClass = "god-card";
          if (selectedByP1 && selectedByP2) cardClass += " selected-both";
          else if (selectedByP1) cardClass += " selected-p1";
          else if (selectedByP2) cardClass += " selected-p2";

          return (
            <button
              key={god}
              className={cardClass}
              onClick={() => handleSelect(god)}
            >
              <img src={`${process.env.PUBLIC_URL}/sprites/godcards/${god}.svg`} alt={god} className="god-icon" />
              <span className="god-name">{god.charAt(0).toUpperCase() + god.slice(1)}</span>
            </button>
          );
        })}
      </div>

      <div className="godcard-controls">
        <button className="hint-toggle" onClick={() => setShowHelp(!showHelp)}>
          {showHelp ? "Hide Hints" : "Show Hints"}
        </button>

        <button
          className="start-button"
          disabled={!allSelected}
          onClick={() => onStartGameWithGodCards(selected)}
        >
          Start Game
        </button>
      </div>

      {showHelp && (
        <div className="godcard-help-panel">
          <h4>God Card Descriptions</h4>
          <ul>
            {Object.keys(GOD_CARDS).map((god) => (
              <li key={god}>
                <strong>{god.charAt(0).toUpperCase() + god.slice(1)}:</strong> {GOD_CARDS[god]}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}

export default GodCardSelectScreen;