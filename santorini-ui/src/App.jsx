import React, { useEffect, useState } from "react";
import axios from 'axios';
import "./App.css";
import "./components/UI.css"
import "./components/LoadingScreen.css"
import HelpScreen from "./components/HelpScreen";
import GameOverScreen from "./components/GameOverScreen";
import Board from "./components/Board";

function App() {

  const [gameState, setGameState] = useState(null);
  const [showHelp, setShowHelp] = useState(false);

  const fetchGameState = () => {
    axios.get("http://localhost:8080/game-state")
      .then(res => {
        console.log("Game state received: ", res.data)
        setGameState(res.data)
      })
      .catch(err => console.error('Failed to fetch game state', err));
  }

  useEffect(() => {
    fetchGameState();
  }, []);

  const handleTileClick = (x, y) => {
    axios.post("http://localhost:8080/tile-press", { x, y })
      .then(fetchGameState)
      .catch((err) => console.error("Tile press failed", err));
  }

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
          <button className="new-game-button" onClick={() => {
            axios.post("http://localhost:8080/new-game")
              .then(res => setGameState(res.data))
              .catch(err => console.error("New game failed", err));
          }}>
            New Game
          </button>
          <button className="help-button" onClick={() => setShowHelp(!showHelp)}>
            How to Play
          </button>
        </div>
      </header>

      <main className="main-content">
        <div className="side-panel left">
          <img
            src="/sprites/player1.svg"
            alt="Player 1"
            className={`portrait ${gameState.currentPlayer !== 1 ? 'silhouetted' : ''}`}  
          />
          <div className="player-label">Player 1</div>
        </div>

        <div className="center-panel">
          <Board
            board={gameState.board}
            phase={gameState.phase}
            onTileClick={handleTileClick}
          />
        </div>
        
        <div className="side-panel right">
          <img
            src="/sprites/player2.svg"
            alt="Player 2"
            className={`portrait ${gameState.currentPlayer !== 2 ? 'silhouetted' : ''}`}  
          />
          <div className="player-label">Player 2</div>
        </div>
      </main>
      
      <footer className={`instructions ${gameState.currentPlayer === 1 ? 'player1-turn' : gameState.currentPlayer === 2 ? 'player2-turn' : ''}`}>
        <span className="turn-text">
        {gameState.phase === "game_over"
          ? `🎉 Player ${gameState.winner} Wins! 🎉`
          : `Player ${gameState.currentPlayer}'s Turn to ${gameState.phase.toUpperCase()}`}
        </span>
      </footer>

      {showHelp && (
        <HelpScreen onClose={() => setShowHelp(false)} />
      )}

      {gameState.phase === "game_over" && (
        <GameOverScreen
          winner={gameState.winner}
          onNewGame={() =>
            axios.post("http://localhost:8080/new-game")
              .then(res => setGameState(res.data))
              .catch(err => console.error("New game failed", err))
          }
        />
      )}
    </div>
  );
}

export default App;