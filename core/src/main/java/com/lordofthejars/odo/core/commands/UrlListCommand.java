package com.lordofthejars.odo.core.commands;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.lordofthejars.odo.core.CliExecutor;
import com.lordofthejars.odo.core.commands.output.UrlList;
import java.util.ArrayList;
import java.util.List;

public class UrlListCommand extends AbstractRunnableCommand<UrlList> {

    private static final String COMMAND_NAME = "list";

    private UrlCommand urlCommand;
    private GlobalParametersSupport globalParametersSupport;

    private UrlListCommand(UrlCommand urlCommand, CliExecutor odoExecutor) {
        super(odoExecutor, UrlListCommand::parse);
        this.urlCommand = urlCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(urlCommand.getCliCommand());

        arguments.add(COMMAND_NAME);
        if (this.globalParametersSupport != null) {
            arguments.addAll(this.globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    protected static UrlList parse(List<String> consoleOutput) {
        final String output = String.join(" ", consoleOutput.toArray(new String[consoleOutput.size()]));

        if (output.length() == 0) return null;
        final JsonValue outputJson = Json.parse(output);

        final JsonObject urlJson = outputJson.asObject();
        return UrlList.from(urlJson);
    }

    public static class Builder extends GlobalParametersSupport.Builder<UrlListCommand.Builder> {
        private UrlListCommand urlListCommand;

        public Builder(UrlCommand urlCommand, CliExecutor odoExecutor) {
            this.urlListCommand = new UrlListCommand(urlCommand, odoExecutor);
        }

        public UrlListCommand build() {
            urlListCommand.globalParametersSupport = buildGlobalParameters();
            return urlListCommand;
        }

    }
}
