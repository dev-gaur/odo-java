package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentPushCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "push-component")
public class OdoComponentPushMojo extends AbstractMojo {

    private static final String PREFIX = "s";

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> pushComponent;

    @Parameter
    protected String componentName;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }
        ComponentPushCommand componentPushCommand = odo.pushComponent()
                .withComponentName(componentName != null ? componentName : null)
                .build();

        injectFields(componentPushCommand, pushComponent, logger);
        componentPushCommand.execute(project.getBasedir().toPath());
    }
}
