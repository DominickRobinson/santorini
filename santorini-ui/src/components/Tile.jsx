import React from "react";
import './Tile.css';

function Tile({ tile, phase, onClick }) {
    const {
        x, y,
        height,
        hasDome,
        worker,
        isValidSpawn,
        isValidSelect,
        isValidMove,
        isValidBuild,
        isWinningTile
    } = tile;

    const towerClass = `level-${height}`

    const highlightClass =
        isWinningTile ? 'highlight-win' :
        isValidSpawn ? 'highlight-spawn' :
        isValidSelect ? 'highlight-select' :
        isValidMove ? 'highlight-move' :
        isValidBuild ? 'highlight-build' :
        '';

    const workerImage = worker ? (
        <img
            src={`/sprites/worker${worker}.svg`}
            alt={`Player ${worker}\'s worker`}
            className="worker-sprite"
        />
    ) : null;

    const domeImage = hasDome ? (
        <img
            src="/sprites/dome.svg"
            alt="Dome"
            className="dome-sprite"
        />
    ) : null;

    return (
        <button
            className={`tile ${towerClass} ${highlightClass}`}
            onClick={() => onClick(x, y)}>
            {workerImage}
            {domeImage}
        </button>
    );
}

export default Tile;