package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ComponentCreateCommand;
import com.lordofthejars.odo.maven.util.MavenArtifactsUtil;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static com.lordofthejars.odo.maven.ConfigurationInjector.injectFields;

@Mojo(name = "create-component")
public class OdoComponentCreateMojo extends AbstractMojo {

    private static final String PREFIX = "s";

    protected Odo odo = null;

    // Current maven project
    @Parameter(defaultValue= "${project}", readonly = true)
    protected MavenProject project;

    @Parameter
    protected Map<String, String> createComponent;

    @Override
    public void execute() {

        if (odo == null) {
            odo = new Odo();
        }

        if (!createComponent.containsKey("componentType")) {
            throw new IllegalArgumentException("componentType property is required for create component.");
        }

        ComponentCreateCommand componentCreateCommand = odo.createComponent(createComponent.get("componentType"))
                .withComponentName(createComponent.get("componentName") != null ? createComponent.get("componentName") : MavenArtifactsUtil.getSanitizedArtifactId(project, PREFIX))
                .build();

        injectFields(componentCreateCommand, createComponent);
        componentCreateCommand.execute(project.getBasedir().toPath());
    }
}