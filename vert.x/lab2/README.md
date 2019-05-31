# Create Project, Build, and Deploy to OpenShift

OpenShift separates different projects using the concept of a project (also known as a Kubernetes namespace).

In this section, you create a project—using your username as part of the project name—to house your project and keep it separate from those of other users. You then build and deploy the service to OpenShift.

1. Create the OpenShift project, making sure to replace userXX with your username:

    `export HELLO_PROJECT_NAME=helloworld-http-userXX`

    `oc new-project $HELLO_PROJECT_NAME`

    It is possible to enable a multi-tenant cluster where users can create the same project names across the cluster, but this is not enabled for this lab. If interested, consult the documentation for more details.

2. Build and deploy to OpenShift:

    `mvn clean fabric8:deploy -Popenshift`
    
3. Check the status of the deployment in the OpenShift web console, or by using the CLI:

    `oc get pods`
    
    ```
    Sample Output
    NAME                             READY     STATUS      RESTARTS   AGE
    hello-microservice-1-m73d5       1/1       Running     0          30s
    ```

4. Check the log for the application pod to verify that the application starts up correctly, making sure to use the pod name from your results:

    `oc logs -f hello-microservice-1-m73d5`

    Sample Output
    ```
    Starting the Java application using /opt/run-java/run-java.sh ...
    ...
    Nov 03, 2018 8:20:51 PM io.vertx.core.impl.launcher.commands.VertxIsolatedDeployer
    INFO: Succeeded in deploying verticle
    ```
## Test Application

In this section, you test the hello-microservice application using curl.

1. In order to access the application outside OpenShift, determine the external host name.

2. Get the URL of the hello-microservice application:

    ```
    export HELLO_URL=http://$(oc get route hello-microservice \
    -n $HELLO_PROJECT_NAME -o template --template='{{.spec.host}}')
    ```

3. Exercise the endpoint using curl:

    `curl -X GET "${HELLO_URL}/John"`
    
    Sample Output
    
    `{"message":"Hello John","served-by": "hello-microservice-1-9r8uv"}`
    
## Scale Up and Scale Down

OpenShift gives you the ability to scale up and scale down your application. You can make use of manual scaling or auto-scaling. In this section, you manually scale your application.

1. Scale Up

    Set the number of replicas using the oc command line:

    ```
    # scale up to 3 replicas
    oc scale --replicas=3 dc hello-microservice
    ```

    You can also set the number of replicas using the OpenShift web console.

    Use curl to test the application:

    `curl -X GET "${HELLO_URL}/John"`
    
    Sample Output
    
    `{"message":"hello John","served-by": "hello-microservice-1-9r8uv"}`

    Refresh several times to see different values for "served-by".

    Your request is being handled by different instances. OpenShift balances the load between the different instances.

2. Scale Down

    In this section, you scale down the application to a single replica.

    Scale down to one replica:

    ```
    # scale down to 1 replicas
    oc scale --replicas=1 dc hello-microservice
    ```

    Test the application:

    `curl -X GET "${HELLO_URL}/John"`

    Sample Output
    
    `{"message":"hello John","served-by": "hello-microservice-1-9r8uv"}`

    Refresh several times and expect to see the same value for "served-by" for each request.

    This confirms that only one instance is available to handle the request.mvn clean fabric8:deploy -Popenshift
