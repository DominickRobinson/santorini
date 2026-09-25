// The original Java engine is compiled by TeaVM and loaded before React.
// Keep the response shape of the former server without making network requests.
function invoke(method, ...args) {
  return Promise.resolve().then(() => {
    if (typeof window[method] !== "function") {
      throw new Error("The game could not load. Please refresh the page.");
    }
    return { data: JSON.parse(window[method](...args)) };
  });
}

const gameApi = {
  get(path) {
    if (path === "/game-state") return invoke("state");
    return Promise.reject(new Error(`Unknown game action: ${path}`));
  },
  post(path, data = {}) {
    switch (path) {
      case "/new-game":
        return invoke("newGame", data.player1GodCard || "none", data.player2GodCard || "none");
      case "/tile-press": return invoke("tilePress", data.x, data.y);
      case "/skip": return invoke("skip");
      default: return Promise.reject(new Error(`Unknown game action: ${path}`));
    }
  },
};

export default gameApi;
