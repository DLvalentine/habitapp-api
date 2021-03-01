# habitapp-api
API for Habitapp, a simple self-hosted webapp for goal/habit tracking.

# Technology used in this project

- Java (11)
- Maven
- Spring Boot (2.4.x)
- Lowdefy (used in the [front-end](https://github.com/DLvalentine/habitapp-webapp))
- [MySQL/Server (8) via Docker](https://hub.docker.com/r/mysql/mysql-server/)
    - This was developed on Windows, so I am also using WSL2 w/Debian as my distro of choice.
    - I also opted to use MySQL Workbench, following [this guide](https://stackoverflow.com/questions/33827342/how-to-connect-mysql-workbench-to-running-mysql-inside-docker) to setup the MySQL instance.
    - The SQL code to create the DB will be in the src/main/resources/static/db directory, so you can spin up MySQL however you want.
- Postman (for manual testing/development of API)

# Getting up and running

Once the prequesites are installed, per the "tech stack" blurb above, make sure the following is done in order:

1. MySql/Server instance setup and installed
2. DB created based on src/main/resources/static/db/habitapp.sql

Then you should be able to use the included `build.bat` and `serve.bat` scripts to build the project and run the project, respectively. This works for my purposes, but maybe one day soon I'll throw together something using Docker.

Once the API is running, go crazy. You could fire up habitapp's front-end or do something else entirely.

# Developing!

I made this as a little side project to learn Spring Boot basics, so... idk do whatever you want.
