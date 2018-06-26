
# IGT Frontend

**Caution: This guide was made for UNIX machines. The installation process may be different on Windows machines.**

## Prerequisites

 - Node.js version 8 or newer
 - Npm version 5 or newer
 - Docker (CLI) version 18 or newer

## Building from source

- Hints

    You need to work with the shell to build the project. 

	Your location has to be this folder (path/to/repository/Frontend).

 - **Change the API URL in the file src/store/index.js**

	  In line 7 you will find the following: const HOST = '';

	  Insert the API URL in the following format: const HOST = 'http://localhost:9080';

- Install node/npm dependencies (they are described in package.json)
	```bash
	npm install
	```
  The folder node_modules will be created and the dependencies will be installed automatically.
  
-  Build the web application that shall be served
    ```bash
    npm run build
    ```

- Build the docker image
	```bash
	docker build -t igt/frontend .
	```
	The image will now be listed by docker.
	```bash
	docker images
	
	> REPOSITORY           TAG                 IMAGE ID            CREATED             SIZE
	> igt/frontend         latest              df390c7ded70        1 minute ago       833MB

	```
- Run the docker image
	```bash
	docker run -p 44044:8080 igt/frontend
	```

## Accessing the app

Simply open http://localhost:44044 in the browser of your choice.
