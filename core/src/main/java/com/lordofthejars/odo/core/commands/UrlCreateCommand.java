package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class UrlCreateCommand implements Command {

    private static final String COMMAND_NAME = "create";

    private String componentName;

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PROJECT = "--project";
    private static final String OPEN = "--open";
    private static final String PORT = "--port";

    private String app;
    private String component;
    private String project;
    private Boolean open;
    private Integer port;

    private GlobalParametersSupport globalParametersSupport;

    private List<String> extraCommands;

    private UrlCreateCommand(){

    }

    @Override
    public List<String> getCliCommand() {

        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        if (componentName != null) {
            arguments.add(componentName);
        }

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (component != null) {
            arguments.add(COMPONENT);
            arguments.add(component);
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (open != null && open.booleanValue()) {
            arguments.add(OPEN);
        }

        if (port != null) {
            arguments.add(PORT);
            arguments.add(Integer.toString(port));
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<UrlCreateCommand.Builder> {
        private UrlCreateCommand urlCreateCommand;

        public Builder() {
            this.urlCreateCommand = new UrlCreateCommand();
        }

        public UrlCreateCommand.Builder withComponentName(String componentName) {
            this.urlCreateCommand.componentName = componentName;
            return this;
        }

        public UrlCreateCommand.Builder withApp(String app) {
            this.urlCreateCommand.app = app;
            return this;
        }

        public UrlCreateCommand.Builder withProject(String project) {
            this.urlCreateCommand.project = project;
            return this;
        }

        public UrlCreateCommand.Builder withComponent(String component) {
            this.urlCreateCommand.component = component;
            return this;
        }

        public UrlCreateCommand.Builder withPort(Integer port) {
            this.urlCreateCommand.port = port;
            return this;
        }

        public UrlCreateCommand.Builder withOpen() {
            this.urlCreateCommand.open = Boolean.TRUE;
            return this;
        }

        public UrlCreateCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.urlCreateCommand.extraCommands = extraArguments;
            return this;
        }

        public UrlCreateCommand build() {
            urlCreateCommand.globalParametersSupport = buildGlobalParameters();
            return urlCreateCommand;
        }

    }
}
