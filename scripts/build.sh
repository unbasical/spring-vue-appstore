cd ./src/main/resources/hm-apps && npm install && npm run build && rm -r ./../www/static/*
cp -r ./dist/* ./../www/static

