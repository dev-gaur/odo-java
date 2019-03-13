package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OdoExecutorStubInjector.class})
public class OdoStorageUnmountMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehaviorWithStorageName(OdoExecutorStub odoExecutorStub) {
        // Given
        OdoStorageUnmoutMojo odoStorageUnmoutMojo = new OdoStorageUnmoutMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> unmountStorageConfig = new HashMap<>();
        unmountStorageConfig.put("app", "myapp");
        unmountStorageConfig.put("component", "mycomponent");
        unmountStorageConfig.put("project", "myproject");
        unmountStorageConfig.put("storageName", "foostorage");

        odoStorageUnmoutMojo.unmountStorage = unmountStorageConfig;
        odoStorageUnmoutMojo.project = project;
        odoStorageUnmoutMojo.odo = odo;

        // When:
        odoStorageUnmoutMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo storage unmount foostorage --app myapp --component mycomponent --project myproject");
    }
}
