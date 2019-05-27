cd ./src/main/resources/hm-apps && npm run build && rm -rf ./../www/static
mkdir ./../www/static
cp -r ./dist/* ./../www/static
cd ./../../../../ && git add . && git commit -m "Changes for deployment"
git push heroku master

