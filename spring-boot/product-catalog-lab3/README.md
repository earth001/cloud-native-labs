# Deploy openshift instructions

1. Log in to the remote OpenShift environment.

2. Create a new project and add your initials to the name to make it unique:

Project names in OpenShift must be all lowercase and unique.

`export CATALOG_PROJECT_NAME=lab3-product-catalog-<your-initials>`

`oc new-project $CATALOG_PROJECT_NAME`

3. Create a PostgreSQL database:

`oc new-app -e POSTGRESQL_USER=luke -ePOSTGRESQL_PASSWORD=secret -ePOSTGRESQL_DATABASE=my_data openshift/postgresql-92-centos7 --name=my-database`

4. Build and deploy your project to the OpenShift servers:

`mvn clean fabric8:deploy -Popenshift`

This deployment uses the OpenShift configuration files in src/main/fabric8. In particular, it reads deployment.yml, credentials-secret.yml, and route.yml.

This command can take between 5 and 10 minutes to complete, depending on your Internet speed.

5. Check the status of the deployment in the OpenShift web console or by using the CLI:

`oc get pods -w -n $CATALOG_PROJECT_NAME`
```
Sample Output
NAME                                  READY     STATUS      RESTARTS   AGE
my-database-1-b5p5m                   1/1       Running     0          18m
product-catalog-lab3-1-w725f          1/1       Running     0          17m
product-catalog-lab3-s2i-1-build      0/1       Completed   0          18m
```

6. Wait until you see the two pods my-database-xxx and product-catalog-lab3-xxx listed as READY 1/1 and STATUS Running.

You can ignore the pods xxx-build and xxx-deploy. Those pods are only for building and deploying your project.
Once the two pods my-database-xxx and product-catalog-lab3-xxx are ready, press Ctrl+C.

## Test Product Catalog REST API

1. Get the URL of the product catalog application:

```
export PRODUCT_CATALOG_URL=http://$(oc get route product-catalog-lab3 \
-n $CATALOG_PROJECT_NAME -o template --template='{{.spec.host}}')
```

2. Get the products from the catalog REST API:

`curl -X GET "$PRODUCT_CATALOG_URL/products"`

Sample Output

```
[{"itemId":329299,"name":"Red Fedora","description":"Official Red Hat Fedora",
"price":34.99},{"itemId":329199,"name":"Forge Laptop Sticker",
"description":"JBoss Community Forge Project Sticker","price":8.5}, ...
```

This data is returned from the product catalog REST API deployed on the OpenShift servers.
