package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentLinkCommand;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.Map;
import java.util.logging.Logger;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "link-component")
public class OdoComponentLinkMojo extends AbstractMojo {
    private static final String PREFIX = "s";

    protected Odo odo = null;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> linkComponent;

    // The target component/service name to link to.
    @Parameter(required = true)
    protected String target;

    @Override
    public void execute() {
        if(odo == null) {
            odo = new Odo();
        }

        ComponentLinkCommand componentLinkCommand = odo.linkComponent(target).build();
        injectFields(componentLinkCommand, linkComponent, logger);
        componentLinkCommand.execute(project.getBasedir().toPath());
    }

}
