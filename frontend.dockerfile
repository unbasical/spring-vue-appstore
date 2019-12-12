# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY ./src/main/resources/hm-apps/package*.json ./
RUN npm install
COPY ./src/main/resources/hm-apps .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY docker-frontend-entrypoint.sh .
EXPOSE 80
ENTRYPOINT ["sh", "docker-frontend-entrypoint.sh"]