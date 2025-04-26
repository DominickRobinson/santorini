import React, { useState } from "react";
import MainMenu from "./components/MainMenu";
import GodCardSelectScreen from "./components/GodCardSelectScreen";
import GameScreen from "./components/GameScreen";

function App() {
  const [screen, setScreen] = useState("menu");
  const [useGodCards, setUseGodCards] = useState(false);
  const [selectedGodCards, setSelectedGodCards] = useState({ player1: null, player2: null });

  const handleStartGameWithoutGodCards = () => {
    setUseGodCards(false);
    setSelectedGodCards({ player1: "none", player2: "none" });
    setScreen("game");
  }

  const handleStartGameWithGodCards = () => {
    setUseGodCards(true);
    setScreen("select");
  }

  const handleGodCardSelectionDone = (godCards) => {
    setSelectedGodCards(godCards);
    setScreen("game");
  }

  if (screen === "menu") {
    return (
      <MainMenu
        onStartGame={handleStartGameWithoutGodCards}
        onStartGameWithGodCards={handleStartGameWithGodCards}
      />
    );
  }

  if (screen === "select") {
    return (
      <GodCardSelectScreen
        onStartGameWithGodCards={handleGodCardSelectionDone}
      />
    );
  }

  if (screen === "game") {
    return (
      <GameScreen
        useGodCards={useGodCards}
        selectedGodCards={selectedGodCards}
        onBackToMenu={() => {
          setScreen("menu");
          setSelectedGodCards({ player1: null, player2: null });
        }}
      />
    );
  }

  return null;
}

export default App;