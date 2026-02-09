package fr.kikoplugins.kikoapi.updatechecker;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.kikoplugins.kikoapi.KikoAPI;
import fr.kikoplugins.kikoapi.utils.StringUtils;
import fr.kikoplugins.kikoapi.utils.Task;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@NullMarked
public class UpdateChecker {
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    private final JavaPlugin plugin;
    private final String currentVersion;
    @Nullable
    private final URI uri;

    @Nullable
    private String latestVersion;
    private boolean noNewVersion;

    public UpdateChecker(JavaPlugin plugin, String modrinthID) {
        Preconditions.checkNotNull(plugin, "plugin cannot be null");
        Preconditions.checkNotNull(modrinthID, "modrinthID cannot be null");

        this.plugin = plugin;

        this.currentVersion = plugin.getPluginMeta().getVersion();

        URI tempURI = null;
        try {
            String gameVersionArray = "[\"%s\"]".formatted(Bukkit.getMinecraftVersion());
            tempURI = new URI("https://api.modrinth.com/v2/project/%s/version?include_changelog=false&game_version=%s".formatted(
                    modrinthID,
                    URLEncoder.encode(gameVersionArray, StandardCharsets.UTF_8)
            ));
        } catch (URISyntaxException e) {
            // Should not happen
            KikoAPI.getInstance().getSLF4JLogger().error("Failed to create URI for update checker", e);
        } finally {
            this.uri = tempURI;
        }

        if (this.uri == null)
            return;

        this.startTask();
    }

    private void startTask() {
        Task.asyncRepeat(task -> {
            this.latestVersion = this.latestVersion();
            this.noNewVersion = this.latestVersion == null || StringUtils.compareSemVer(this.currentVersion, this.latestVersion) >= 0;

            if (this.noNewVersion)
                return;

            Bukkit.getConsoleSender().sendMessage(Component.text("PLACEHOLDER"));

            Bukkit.getPluginManager().registerEvents(new UpdateCheckerListener(
                    this.plugin,
                    this.currentVersion,
                    this.latestVersion
            ), this.plugin);

            task.cancel();
        }, this.plugin, 0L, 24L, TimeUnit.HOURS);
    }

    @Nullable
    private String latestVersion() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(this.uri)
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> resp = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200)
                return null;

            JsonElement root = gson.fromJson(resp.body(), JsonElement.class);
            JsonArray array = root.getAsJsonArray();

            // Can happen when the newest versions don't support the server version anymore
            if (array.isEmpty())
                return null;

            JsonObject latestObject = array.get(0).getAsJsonObject();

            return latestObject.get("version_number").getAsString();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
