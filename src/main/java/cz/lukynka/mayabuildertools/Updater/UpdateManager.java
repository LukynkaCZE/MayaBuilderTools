package cz.lukynka.mayabuildertools.Updater;

import com.google.gson.Gson;
import cz.lukynka.mayabuildertools.MayaBuilderTools;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

 // This is blatantly stolen from Island Utils
 // https://github.com/AsoDesu/IslandUtils/tree/main/src/main/java/net/asodev/islandutils/util/updater/
 // thanks aso :P

public class UpdateManager {

    HttpClient client;
    Gson gson;

    public UpdateManager() {
        client = HttpClient.newBuilder().build();
        gson = new Gson();
    }


    // Creates request to github API to get the latest release
    public CompletableFuture<GithubRelease> checkForUpdates() throws Exception {
        CompletableFuture<GithubRelease> f = new CompletableFuture<>();

        URI updatorURI = new URI("https://api.github.com/repos/LukynkaCZE/MayaBuilderTools/releases/latest");
        HttpRequest req = HttpRequest.newBuilder(updatorURI).GET().build();

        client.sendAsync(req, HttpResponse.BodyHandlers.ofString()).thenAccept(res -> {
            f.complete(gson.fromJson(res.body(), GithubRelease.class));
        });

        return f;
    }

    public void runUpdateCheck() {
        try {
            checkForUpdates().thenAccept(res -> {
                if (res == null) return;

                // If the release tag from github doesn't match the current mod version, set updateAvailable to true
                if (!MayaBuilderTools.version.equals(res.getTagName())) {
                    MayaBuilderTools.updateTag = res.getTagName();
                    MayaBuilderTools.updateAvailable = true;

                    // Compare tag from github and current version as numbers, if current version
                    // is bigger than one from github, we are running dev version
                    var versionNum = Integer.parseInt(MayaBuilderTools.version.replace(".", ""));
                    var newVersionNum = Integer.parseInt(res.getTagName().replace(".", ""));
                    if(versionNum > newVersionNum) {
                        MayaBuilderTools.isPreRelease = true;
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("Failed to get Update!");
        }
    }
}