import React from "react";
import "./MainMenu.css";

function MainMenu({ onStartGame, onStartGameWithGodCards }) {
  return (
    <div className="main-menu">
      <h1 className="menu-title">Santorini</h1>
      <button className="menu-button" onClick={onStartGame}>
        Play Without God Cards
      </button>
      <button className="menu-button" onClick={onStartGameWithGodCards}>
        Play With God Cards
      </button>
    </div>
  );
}

export default MainMenu;
