FROM node:8
WORKDIR /home/patrick/Documents/Docker
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 8080
CMD [ "node", "server.js" ]