package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class StorageCreateCommand implements Command {

    private static final String COMMAND_NAME = "create";

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PROJECT = "--project";
    private static final String SIZE = "--size";
    private static final String PATH = "--path";

    private String storageName;

    private String app;
    private String component;
    private String project;
    private String path;
    private String size;

    private GlobalParametersSupport globalParametersSupport;

    private StorageCreateCommand(String storageName){
        this.storageName = storageName;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        arguments.add(storageName);

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

        if (path != null) {
            arguments.add(PATH);
            arguments.add(path);
        }

        if (size != null) {
            arguments.add(SIZE);
            arguments.add(size);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<StorageCreateCommand.Builder> {
        private StorageCreateCommand storageCreateCommand;

        public Builder(String storageName) {
            this.storageCreateCommand = new StorageCreateCommand(storageName);
        }

        public StorageCreateCommand.Builder withApp(String app) {
            this.storageCreateCommand.app = app;
            return this;
        }

        public StorageCreateCommand.Builder withProject(String project) {
            this.storageCreateCommand.project = project;
            return this;
        }

        public StorageCreateCommand.Builder withComponent(String component) {
            this.storageCreateCommand.component = component;
            return this;
        }

        public StorageCreateCommand.Builder withPath(String path) {
            this.storageCreateCommand.path = path;
            return this;
        }

        public StorageCreateCommand.Builder withSize(String size) {
            this.storageCreateCommand.size = size;
            return this;
        }

        public StorageCreateCommand build() {
            storageCreateCommand.globalParametersSupport = buildGlobalParameters();
            return storageCreateCommand;
        }

    }
}
