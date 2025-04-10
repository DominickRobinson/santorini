import React from "react";
import Tile from './Tile';
import "./Board.css";

function Board({ board, phase, onTileClick }) {

    return (
        <div className="board">
            {board.flatMap((row, y) => 
                row.map((tile, x) => {

                    return (
                        <Tile
                            key={`${x},${y}`}
                            tile={tile}
                            phase={phase}
                            onClick={() => onTileClick(x, y)}
                        />
                    )
                })
            )}
        </div>
    );
}

export default Board;