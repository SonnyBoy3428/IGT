# IGT
This repository is for the course IGT - Integration Technologies at the Mannheim University of Applied Sciences

# Tools needed

* [Docker for Windows](https://store.docker.com/editions/community/docker-ce-desktop-windows)
	* Install it
	* Create a Docker-ID
* [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
	* Open the MySQL Workbench
	* Add a new connection 
	* Name your new connection appropriately 
	* Type in 'localhost' as the host 
	* Type in '3306' as the port 
	* Type in 'root' as the user 
	* Store the password 'root' in the vault
* [MySQL via Docker](https://hub.docker.com/r/mysql/mysql-server/)
	* In Command Prompt execute the following: docker pull mysql/mysql-server:5.7 (Version 5.7 is important since it did not work with the lates version)
	* Wait for the completion of the installing procedure
	* Execute the following command: docker run --name=[YOUR_NAME] -e MYSQL_ROOT_HOST=[YOUR_HOST] -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql/mysql-server:5.7
	* [YOUR_NAME] can be any name you like
	* [YOUR_HOST] has to be a very specific IP-Address. Do the following: Open MySQL Workbench and right-click on the connection you've created for this task. Click on 'Edit Connection'. Click on 'Test Connection' and if an error pops up use the displayed IP-Address in the error box for [YOUR_HOST].
* [Eclipse Oxygen](https://www.eclipse.org/downloads/)
	* IntelliJ won't work with the Community Edition. It doesn't support Hibernate.
	* Install the workload 'Java for Developers'

# Setting up an own project
Follow the directions of this link [here](http://www.codejava.net/frameworks/hibernate/hibernate-hello-world-tutorial-for-beginners-with-eclipse-and-mysql)

# Other useful links for a rough understanding
* [Hibernate and IntelliJ](https://www.youtube.com/watch?v=nl3-XaV8X4A&t=370s) - Just forget the IntelliJ part. It won't work with the community edition.