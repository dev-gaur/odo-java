
package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;

@ExtendWith({MockitoExtension.class, OdoExecutorStubInjector.class})
public class OdoProjectCreateMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) throws MojoExecutionException, MojoFailureException {
        // Given
        OdoProjectCreateMojo odoProjectCreateMojo = new OdoProjectCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        String projectName = "fooproject";

        odoProjectCreateMojo.odo = odo;
        odoProjectCreateMojo.projectName = projectName;

        // When:
        odoProjectCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo project create " + projectName);
    }
}
