cd ./src/main/resources/hm-apps && npm install && npm run build && rm -r ./../www/static/*
mkdir ./../www/static
cp -r ./dist/* ./../www/static

