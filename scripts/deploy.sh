cd ./src/main/resources/hm-apps && npm run build && rm -r ./../static/* && cp -r ./dist/* ./../static
cd ./../../../../ && git add . && git commit -m "Changes for deployment" && git push heroku master

