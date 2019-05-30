package com.redhat.coolstore.rest;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.InetAddress;

@Path("/service")
public class HealthEndpoint {

  @GET
  @Health
  @Path("/health")
  public HealthCheckResponse health() {
    ModelNode op = new ModelNode();
    op.get("address").setEmptyList();
    op.get("operation").set("read-attribute");
    op.get("name").set("suspend-state");

    try (ModelControllerClient client = ModelControllerClient.Factory.create(
        InetAddress.getByName("localhost"), 9990)) {
      ModelNode response = client.execute(op);

      if (response.has("failure-description")) {
        throw new Exception(response.get("failure-description").asString());
      }

      boolean isRunning = response.get("result").asString().equals("RUNNING");
      if (isRunning) {
        return HealthCheckResponse.named("server-state").up().build();
      } else {
        return HealthCheckResponse.named("server-state").down().build();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @GET
  @Path("/killme")
  public Response killme() {
    ModelNode op = new ModelNode();
    op.get("address").setEmptyList();
    op.get("operation").set("suspend");

    try (ModelControllerClient client = ModelControllerClient.Factory.create(
        InetAddress.getByName("localhost"), 9990)) {
      ModelNode response = client.execute(op);

      if (response.has("failure-description")) {
        throw new Exception(response.get("failure-description").asString());
      }

      return Response.ok(response.get("result").asString()).build();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
