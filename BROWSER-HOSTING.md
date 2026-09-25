# Santorini on GitHub Pages

The website serves the built game at `/santorini/`. Hugo copies `static/santorini/` unchanged, so the existing Pages workflow does not need Java or Node dependencies to publish it.

## Preservation

All original `src/main/java` game rules, god cards, HTTP server, and serializer are unchanged. TeaVM 0.15.0 compiles the existing rules and serializer into JavaScript. The browser-only entry point in `src/browser/java` replaces the HTTP server connection. The React interface retains its layout, styles, artwork, and controls; its requests now call the compiled engine locally, and sprite paths include `/santorini`.

The original class repository and original local school-project folder are untouched. No fork or backend hosting account is required.

## Rebuild

Check out the `browser-hosting` branch and use Java 21, Maven, Node.js (tested with 24), and npm:

```sh
./build.sh
```

Copy the contents of `santorini-ui/build/` into the website repository’s `static/santorini/` directory, then commit those generated files. Pushing to `main` triggers the existing Hugo/GitHub Pages deployment. Keep this branch for future edits; the website repository contains only the playable build, not the school-project Java sources.

The original Java unit tests and the React menu smoke test run during the build. The original template's unused “learn React” test was replaced with the menu check.

## Playing

Two players share one browser tab and take turns using the same device. Every tab has its own game. Reloading the page resets it; there is no saved-game or remote multiplayer feature.

## Validation of the initial browser build

The original Java unit tests passed. A seeded sequence of 1,013 states covering all nine card options (including no card) matched the unchanged Java HTTP server exactly, including board data, valid actions, instructions, skipping, and encountered wins. This verifies compilation behavior; it does not change or claim to fix existing gameplay limitations.
