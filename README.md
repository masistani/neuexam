Interview Project
==================

# Setup Project

Please use following command to add dependencies.

mvn clean install



Following command to setup project for eclipse IDE.

mvn eclipse:clean eclipse:eclipse


Note: Please keep in mind that project is include test which would prevent build if test case fails. Please apply change in POM if you wish to run application without running the test cases.

# Build

To build project please run following command (unique for all environment).

    mvn clean package
  
    

# Run

To run this project, do the following:

    java -jar ./target/restapiexam-0.0.1-SNAPSHOT.jar


# NOTE
Please consider following items in runtime:

	Default port is set to 10443 in test and API. To make change in port, please change ./resources/application.properties -> server.port=<port-number>. In addition, please change port number at RestApiExamApplicationTests.java well.
