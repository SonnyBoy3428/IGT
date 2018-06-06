# IGT
This repository is for the course IGT - Integration Technologies at the Mannheim University of Applied Sciences

# Tools needed

* [Docker for Windows](https://store.docker.com/editions/community/docker-ce-desktop-windows)
	* Install it
	* Create a Docker-ID
* [MySQL Workbench and MySQL Connector J](https://dev.mysql.com/downloads/windows/installer/8.0.html)
	* Use the windows installer if you have Windows and merely install these two tools (**no other tools or servers are necessary**)
	* If you are using a different platform you'll have to look into that yourself
	* Yet here are some starting links: [Workbench](https://dev.mysql.com/downloads/workbench/) and [Connector J](https://dev.mysql.com/downloads/connector/j/)
* [MySQL via Docker](https://hub.docker.com/r/mysql/mysql-server/)
	* The link is only provided for your convenience. You don't need to install the image just continue reading and you'll get there
* [Eclipse Oxygen](https://www.eclipse.org/downloads/)
	* IntelliJ won't work with the Community Edition. It doesn't support Hibernate.
	* Install the workload 'Java for Developers'
* [JBoss Tools](http://tools.jboss.org/downloads/jbosstools/oxygen/4.5.3.Final.html)
	* It's recommended to install this via the Eclipse Marketplace
	* In Eclipse:
		* Got to menu 'Help' and click on 'Eclipse Marketplace...'
		* Search for JBoss Tools 4.5.3 Final
		* Install the package

		
# Setting up the tools
Before we get started make sure you cloned this repository onto your computer. I don't know if you have to set up hibernate for Eclipse again but if you do have to follow these tutorials to the needed extent:

* [MySQL via Docker](https://hub.docker.com/r/mysql/mysql-server/)
	* Just navigate to the folder [./Docker/](Docker/) and execute this command via a terminal: "docker-compose up"
	* Wait a minute until the server is ready to be worked with
* Integrate MySQL Connector J into project (if needed)
	* In Eclipse right click the project in the project explorer and click on **Properties**
	* Got to **Java Build Path** and click on **Add External JAR**
	* Navigate to the folder [./Resources/](Resources/) and add the JAR for the connector
* MySQL Workbench
	* Open the MySQL Workbench
	* Add a new connection 
	* Name your new connection appropriately 
	* Type in 'localhost' as the host 
	* Type in '3308' as the port 
	* Type in 'root' as the user 
	* Store the password 'root' in the vault
	
	
# Setting up an own project
Follow the directions of this link [here](http://www.codejava.net/frameworks/hibernate/hibernate-hello-world-tutorial-for-beginners-with-eclipse-and-mysql) and this link [here](http://www.codejava.net/frameworks/hibernate/java-hibernate-reverse-engineering-tutorial-with-eclipse-and-mysql)

# Other useful links for a rough understanding
* [Hibernate and IntelliJ](https://www.youtube.com/watch?v=nl3-XaV8X4A&t=370s) - Just forget the IntelliJ part. It won't work with the community edition.
* [Generate Classes in Java from existing database tables](http://www.codejava.net/frameworks/hibernate/java-hibernate-reverse-engineering-tutorial-with-eclipse-and-mysql)
* [Hibernate Bidirectional One-To-Many Relationship](http://www.baeldung.com/hibernate-one-to-many)
* [Best Practices: One-To-Many Relationship](https://www.thoughts-on-java.org/best-practices-many-one-one-many-associations-mappings/)
* [Many-To-Many Relationships with extra table](https://stackoverflow.com/questions/5127129/mapping-many-to-many-association-table-with-extra-columns)