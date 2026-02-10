package fr.kikoplugins.kikoapi.mock;

import org.mockbukkit.mockbukkit.MockBukkit;

public final class MockBukkitHelper {

    public static KikoServerMock safeMock() {
        KikoServerMock server;

        if (MockBukkit.isMocked() && MockBukkit.getMock() instanceof KikoServerMock serverMock) {
            server = serverMock;
        } else {
            server = MockBukkit.mock(new KikoServerMock());
        }

        server.addSimpleWorld("world");

        return server;
    }

    public static void safeUnmock() {
        if (MockBukkit.isMocked())
            MockBukkit.unmock();
    }

}
