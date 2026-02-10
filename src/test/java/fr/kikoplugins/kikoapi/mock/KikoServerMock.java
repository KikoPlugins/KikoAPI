package fr.kikoplugins.kikoapi.mock;

import org.jspecify.annotations.NonNull;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.command.CommandMapMock;

public class KikoServerMock extends ServerMock {

    public final CommandMapMock commandMap;

    public KikoServerMock() {
        super();

        this.commandMap = new CommandMapMock(this);
    }

    @Override
    public @NonNull CommandMapMock getCommandMap() {
        return this.commandMap;
    }

    @Override
    public boolean isStopping() {
        return MockBukkit.isMocked();
    }
}
