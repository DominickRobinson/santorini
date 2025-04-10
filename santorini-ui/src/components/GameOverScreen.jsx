import React from "react";
import "./GameOverScreen.css";

function GameOverScreen({ winner, onNewGame }) {
  return (
    <div className="overlay">
      <div className="content">
        <h2>🎉 Player {winner} Wins! 🎉</h2>
        <button className="new-game-button" onClick={onNewGame}>
          Play Again
        </button>
      </div>
    </div>
  );
}

export default GameOverScreen;