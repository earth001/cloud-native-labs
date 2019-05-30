# Create Project, Build, and Deploy to OpenShift

OpenShift separates different projects using the concept of a project (also known as a Kubernetes Namespace).

In this section, you create a project—using your username as part of the project name—to house your project and keep it separate from those of other users. You then build and deploy the service along with its database.

1. Create the OpenShift project, making sure to replace userXX with your username:

>> `export INVENTORY_PROJECT_NAME=thorntail-inventory-lab2-userXX`

>> `oc new-project $INVENTORY_PROJECT_NAME`

2. Build and deploy:

`cd ~/thorntail/lab2`

`mvn clean package fabric8:build fabric8:deploy`

This causes the following to happen:

* **clean** resets the project

* **package** builds the Thorntail uber JAR

* **fabric8:build** builds a Docker image containing the uber JAR and its runtime (Java) and pushes it to OpenShift’s internal Docker registry

* **fabric8:deploy** creates OpenShift objects within the OpenShift project to deploy the service, PostgreSQL, and the associated services and routes

When completed, expect your project to be up and running. OpenShift runs the different components of the project in one or more pods, which are the units of runtime deployment and consist of the running containers for the project. The PostgreSQL database is running in one pod and the inventory service in another.
