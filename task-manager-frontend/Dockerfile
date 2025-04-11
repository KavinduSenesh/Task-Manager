# Step 1: Build the Angular app
FROM node:16 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the Angular project for production
RUN npm run build --prod

# Step 2: Serve the app using a lightweight server (e.g., Nginx)
FROM nginx:alpine

# Copy the build files from the previous stage to the Nginx server
COPY --from=build /app/dist/your-angular-app /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start the Nginx server
CMD ["nginx", "-g", "daemon off;"]
