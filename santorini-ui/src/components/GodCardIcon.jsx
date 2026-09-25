import React from "react";
import "./GodCard.css";

function GodCard({ godName }) {
  const iconPath = `${process.env.PUBLIC_URL}/sprites/godcards/${godName.toLowerCase()}.svg`;
  return (
    <div className="god-card">
      <img src={iconPath} alt={godName} className="god-icon" />
      <div className="god-name">{godName}</div>
    </div>
  );
}

export default GodCard;
