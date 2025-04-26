import React, { useEffect, useState } from "react";
import axios from "axios";
import Board from "./Board";
import HelpScreen from "./HelpScreen";
import GameOverScreen from "./GameOverScreen";
import "./LoadingScreen.css";
import "./GameScreen.css"
import GodCard from "./GodCardIcon";

function GameScreen({ useGodCards, selectedGodCards, onBackToMenu }) {
  const [gameState, setGameState] = useState(null);
  const [showHelp, setShowHelp] = useState(false);

  const fetchGameState = () => {
    axios.get("http://localhost:8080/game-state")
      .then(res => setGameState(res.data))
      .catch(err => console.error("Failed to fetch game state", err));
  };

  const startNewGame = () => {
    const config = {
      player1GodCard: selectedGodCards?.player1 || "none",
      player2GodCard: selectedGodCards?.player2 || "none",
    };
    axios.post("http://localhost:8080/new-game", config)
      .then(res => setGameState(res.data))
      .catch(err => console.error("Failed to start new game", err));
  };

  const handleTileClick = (x, y) => {
    axios.post("http://localhost:8080/tile-press", { x, y })
      .then(fetchGameState)
      .catch(err => console.error("Tile press failed", err));
  };

  const handleSkip = () => {
    axios.post("http://localhost:8080/skip")
      .then(fetchGameState)
      .catch(err => console.error("Skip failed", err));
  };

  useEffect(() => {
    startNewGame();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  if (!gameState) {
    return (
      <div className="loading-screen">
        <div className="spinner" />
        <h2>Loading game...</h2>
      </div>
    );
  }

  return (
    <div className="app-container">
      <header className="header-bar">
        <div className="header-side left" />
        <h1 className="title">Santorini: 17-214 (HW6a Edition)</h1>
        <div className="header-side right">
          {gameState.canSkip && (
            <button className="skip-button" onClick={handleSkip}>
              Skip
            </button>
          )}
          <button className="help-button" onClick={() => setShowHelp(!showHelp)}>
            How to Play
          </button>
          <button className="back-to-menu-button" onClick={onBackToMenu}>
            Back to Menu
          </button>
        </div>
      </header>

      <main className="main-content">
        <div className="side-panel left">
          <img
            src="/sprites/player1.svg"
            alt="Player 1"
            className={`portrait ${gameState.currentPlayer !== 1 ? "silhouetted" : ""}`}
          />
          <div className="player-label">Player 1</div>
          {useGodCards && (<GodCard godName={selectedGodCards.player1} />)}
        </div>

        <div className="center-panel">
          <Board
            board={gameState.board}
            onTileClick={handleTileClick}
          />
        </div>

        <div className="side-panel right">
          <img
            src="/sprites/player2.svg"
            alt="Player 2"
            className={`portrait ${gameState.currentPlayer !== 2 ? "silhouetted" : ""}`}
          />
          <div className="player-label">Player 2</div>
          {useGodCards && (<GodCard godName={selectedGodCards.player2} />)}
        </div>
      </main>

      <footer
        className={`instructions ${
          gameState.currentPlayer === 1
            ? "player1-turn"
            : gameState.currentPlayer === 2
            ? "player2-turn"
            : ""
        }`}
      >
        <span className="turn-text">
          {gameState.phase === "game_over"
            ? `🎉 Player ${gameState.winner} Wins! 🎉`
            : `Player ${gameState.currentPlayer}'s Turn to ${gameState.instruction.toUpperCase()}`}
        </span>
      </footer>

      {showHelp && <HelpScreen onClose={() => setShowHelp(false)} />}

      {gameState.phase === "game_over" && (
        <GameOverScreen winner={gameState.winner} onNewGame={startNewGame} />
      )}
    </div>
  );
}

export default GameScreen;
