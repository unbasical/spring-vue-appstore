cd ./src/main/resources/hm-apps && npm run build && rm -r ./../static/* && cp -r ./dist/* ./../static
cd ./../../../../ && mvn spring-boot:run -Pdev

