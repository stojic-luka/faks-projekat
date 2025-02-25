FROM node:20 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . ./
RUN npm run build

FROM nginx:alpine AS production

# COPY nginx.conf /etc/nginx/nginx.conf
COPY react-nginx.conf /etc/nginx/conf.d/react-nginx.conf
RUN rm /etc/nginx/conf.d/default.conf

COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 5173
CMD ["nginx", "-g", "daemon off;"]
