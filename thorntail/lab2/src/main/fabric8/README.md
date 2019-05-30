# Explore Fabric8 Deployment Resources for OpenShift

Fabric8 and the Fabric8 Maven plug-in enable easy deployment of projects to OpenShift by automating the creation of these objects within OpenShift. They provide "zero configuration" and have sensible defaults. For non-trivial projects, however, additional directives and configuration are needed. For this project, you now have a service and a database.

1- Examine the following files included in this lab in the src/main/fabric8 directory to understand how Fabric8 uses these files to create the necessary resources within OpenShift:

`inventory-deployment.yml`

This file defines the container for the inventory service. It also defines how the container life cycle is managed, and many other configuration values. Note in particular that this file also defines the Thorntail project stage that should be active via the Java system property thorntail.project.stage. You revisit this mechanism in future labs to externalize the settings from the stage file.

`inventory-svc.yml`

This file defines a software-load-balanced service through which other applications can access the inventory service. Through Kubernetes, external consumers running in the same OpenShift cluster or project can access this service using the service name as the host name—for example, http://inventory-service:8080. This makes consumer code less dependent on changing networking conditions such as changing host names and changing ports. The automatic load balancing is key to many microservice architectures where stateless services must be able to scale independently to multiple replicas. This is handled through Kubernetes.

`inventory-route.yml`

This file allows consumers outside OpenShift to access the load-balanced service using an external DNS name, protocol, and well-known and typically unrestricted TCP ports such as 80, 8080, and 8443. For example, if you want to access the service from your colleague’s desktop, you cannot use the service name, you must use this route’s host name.

`inventory-db-deployment.yml`

This file specifies the deployment directives for PostgreSQL, including the name of the base image, port numbers, username, passwords, and database name.

`inventory-db-svc.yml`

This file includes the load-balanced service definition for the PostgreSQL database service.

2- Note that there is no route object for the database.

This means that the database is inaccessible from outside the OpenShift cluster. The only externally facing service is the inventory service.

When the Fabric8 Maven plug-in runs, these files are processed, along with the building of the application, to cause the application and its database to be deployed to OpenShift.
