## Deploy Catalog Service to OpenShift

In this lab, you create the cart service. This service depends on a supporting service: the catalog service.

The solution code for the catalog service is provided. All you have to do is deploy the catalog service.

The catalog service is responsible for retrieving products from and adding products to the catalog. This functionality is exposed over a REST API. Product entities are stored in MongoDB.

1. Build the catalog service with Maven from the command line:

    `cd ~/appmod_springboot_experienced/lab-03-catalog-service`

    `mvn clean package`

2. Log in to OpenShift, using your login credentials when prompted:

    `oc login --insecure-skip-tls-verify \`
    `--server=https://master.na311.openshift.opentlc.com:443`
    
3. Create a new project:

    `export CATALOG_PRJ=<yourname>-coolstore-catalog`

    `oc new-project $CATALOG_PRJ`
    
4. Deploy an instance of MongoDB on OpenShift using the coolstore-catalog-mongodb-persistent.yaml template in the etc directory:

    `oc process -f etc/coolstore-catalog-mongodb-persistent.yaml -p CATALOG_DB_USERNAME=mongo -p CATALOG_DB_PASSWORD=mongo -n $CATALOG_PRJ | oc create -f - -n $CATALOG_PRJ`
    
    * The coolstore-catalog-mongodb-persistent.yaml file specifies the deployment directives for MongoDB, including the name of the base image, port numbers, and database name.

5. Add the view role to the default service account:

    `oc adm policy add-role-to-group view system:serviceaccounts:$CATALOG_PRJ -n $CATALOG_PRJ`

    * The Coolstore catalog microservice calls the Kubernetes API to retrieve the ConfigMap, which requires view access.

6. Create the ConfigMap with the configuration for the Coolstore catalog microservice:

    `oc create configmap app-config --from-file=etc/app-config.yaml -n $CATALOG_PRJ`

The etc folder of the application source project contains an example of a configuration file.

The configuration file defines the catalog.http.port parameter (port for the HTTP server) and the connection_string, db_name, username, and password MongoDB connection parameters, which are used by the Vert.x MongoDB client.

Verify that the ConfigMap is deployed:

oc get configmap app-config -o yaml -n $CATALOG_PRJ
Sample Output
apiVersion: v1
data:
  app-config.yaml: |-
    catalog.http.port: 8080
    connection_string: mongodb://catalog-mongodb:27017
    db_name: catalogdb
    username: mongo
    password: mongo
kind: ConfigMap
metadata:
  creationTimestamp: 2019-01-03T08:50:19Z
  name: app-config
  namespace: coolstore-catalog
  resourceVersion: "19499"
  selfLink: /api/v1/namespaces/coolstore-catalog/configmaps/app-config
  uid: 2b3d7672-4a95-11e7-8788-507b9d27afbf
Deploy the Coolstore catalog microservice on OpenShift:

mvn clean fabric8:deploy -Popenshift -Dfabric8.namespace=$CATALOG_PRJ
While the app is deploying, log in to the OpenShift web console at https://master.na311.openshift.opentlc.com, using your OPENTLC credentials when prompted.

Select your project and then monitor the status of the deployment:

coolstore catalog deployed
The deployment may take 5 to 10 minutes depending on your network connection.
Alternatively, use the CLI:

oc get pods -n $CATALOG_PRJ
Sample Output
NAME                          READY     STATUS      RESTARTS   AGE
catalog-mongodb-1-w132w       1/1       Running     0          1h
catalog-service-1-p1wx1       1/1       Running     0          5m
catalog-service-s2i-1-build   0/1       Completed   0          5m
If you need to redeploy your application, use the following fabric8:build command:

mvn clean package fabric8:build -Popenshift -Dfabric8.namespace=$CATALOG_PRJ
Wait for the deployment to complete, then proceed to the next section.
