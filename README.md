# habitapp-api
API for Habitapp, a simple self-hosted webapp for goal/habit tracking.

# Technology used in this project
(TODO: expand this with installation instructions, run instructions, etc., in a DEVELOPING section)

- Java (11)
- Maven
- Spring Boot (2.4.x)
- Angular (9) (used in the [front-end](https://github.com/DLvalentine/habitapp-webapp))
- [MySQL/Server (8) via Docker](https://hub.docker.com/r/mysql/mysql-server/)
    - This was developed on Windows, so I am also using WSL2 w/Debian as my distro of choice.
    - I also opted to use MySQL Workbench, following [this guide](https://stackoverflow.com/questions/33827342/how-to-connect-mysql-workbench-to-running-mysql-inside-docker) to setup the MySQL instance.
    - The SQL code to create the DB will be in the src/main/resources/static/db directory, so you can spin up MySQL however you want.
- Postman (for manual testing)
