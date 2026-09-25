#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mvn -q -Pbrowser package
(cd santorini-ui && npm ci --no-audit --no-fund && CI=true npm test -- --watchAll=false --watchman=false && PUBLIC_URL=/santorini GENERATE_SOURCEMAP=false npm run build)
echo "Website files are ready in santorini-ui/build/. Copy their contents into your website's static/santorini/ folder."
