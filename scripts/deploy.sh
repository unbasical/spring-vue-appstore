cd ./src/main/resources/hm-apps && npm run build && rm -r ./../static/* && cp -r ./dist/* ./../static
cd ./../../../../ && git push heroku master

