# Santorini: 17-214 HW6a

This is a web-based implementation of the game **Santorini** for CMU 17-214.

## 📦 Getting Started

First, clone this repository:

```bash
git clone https://github.com/CMU-17-214-Students/s25-hw2-santorini-DominickRobinson.git
cd s25-hw2-santorini-DominickRobinson
```

> ✅ You need Java (e.g. Temurin 17) and Maven installed. On Windows, make sure `mvn` works in your terminal.

## 🟢 How to Start

### 1. Backend (Java)

Run this from the root folder:

```bash
mvn compile exec:java
```

Server starts at `http://localhost:8080`.

### 2. Frontend (React)

From the `santorini-ui` folder:

```bash
npm install
npm start
```

UI opens at `http://localhost:3000`.

## ❓ How to Play

Click the **"How to Play"** button in the top right corner of the game to view the rules and controls.

## ✅ Features

- 5x5 grid with tower levels, domes, and workers  
- Turn cycle: SPAWN → [SELECT → MOVE → BUILD] ↺ → GAME OVER  
- Highlights valid tiles  
- Shows current player and winner  
- "New Game" button to reset the board