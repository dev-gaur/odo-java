package com.lordofhejars.odo.core;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.api.Catalog;
import com.lordofthejars.odo.testbed.junit5.OpenShiftCatalogConditionExtension;
import com.lordofthejars.odo.testbed.junit5.OpenShiftConditionExtension;
import com.lordofthejars.odo.testbed.junit5.OpenShiftInjector;
import com.lordofthejars.odo.testbed.junit5.OpenShiftOperation;
import com.lordofthejars.odo.testbed.net.SimpleHttpClient;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith
    ({
        OpenShiftConditionExtension.class, // Checks if there is an OpenShift cluster running
        OpenShiftInjector.class, // Injects OpeShift client to do assertions
        OpenShiftCatalogConditionExtension.class // Checks for catalog requirements
    })
@Catalog(components = "openjdk18")
public class CreateAndDeployConsumerProviderTest {

    private final Path projectDir = Paths.get(".").toAbsolutePath()
        .getParent()
        .getParent()
        .resolve("e2e/link");
    private final Odo odo = new Odo(); // Creates Odo instance

    @AfterEach
    public void removeComponentsAndRoutes() { // Clean components created by odo

        odo.deleteUrl("route").build().execute();
        odo.deleteComponent("provider").build().execute();
        odo.deleteComponent("consumer").build().execute();

    }

    @Test
    public void should_create_deploy_and_link_components(OpenShiftOperation openShiftOperation)
        throws InterruptedException, IOException {

        // Given // When

        odo.createComponent("openjdk18").withComponentName("provider")
            .withLocal(projectDir.resolve("provider").toAbsolutePath().toString())
            .build()
            .execute();
        odo.pushComponent().withComponentName("provider").build().execute();

        odo.createComponent("openjdk18").withComponentName("consumer")
            .withLocal(projectDir.resolve("consumer").toAbsolutePath().toString())
            .build()
            .execute();
        odo.pushComponent().withComponentName("consumer").build().execute();

        odo.createUrl().withComponentName("route").withComponent("consumer").withPort(8080).build().execute();

        odo.linkComponent("provider")
            .withComponent("consumer")
            .withPort("8080")
            .withWait()
            .build()
            .execute();

        // Then

        final Optional<String> exposedRoute = openShiftOperation.getHostOfRouteStartingWith("route");
        assertThat(exposedRoute).isNotEmpty();

        // TODO wait flag has a bug and currently does not work, to not complicate anything now I just put an sleep
        // When bug fixed then we could remove this sleep.

        TimeUnit.SECONDS.sleep(25);

        final SimpleHttpClient simpleHttpClient = new SimpleHttpClient(exposedRoute.get());
        final String read = simpleHttpClient.read();

        assertThat(read).isEqualToIgnoringNewLines("HELLO WORLD");

    }

}
