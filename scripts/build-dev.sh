cd ./src/main/resources/hm-apps && npm install && npm run build-dev && rm -r ./../www/static/*
cp -r ./dist/* ./../www/static
