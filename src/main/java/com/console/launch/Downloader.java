package com.console.launch;

import com.console.utils.UrlUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Downloader {
    public static void downloadGithubRep(String repUrl, Path outputDir) throws IOException {
        URL toUrl = UrlUtils.createUrl(repUrl);

        try (ZipInputStream zipIS = new ZipInputStream(toUrl.openStream())) {
            String repDirName = Objects.requireNonNull(zipIS.getNextEntry()).getName();

            Files.createDirectories(outputDir);

            for (ZipEntry zipEntry = zipIS.getNextEntry(); zipEntry != null; zipEntry = zipIS.getNextEntry()) {
                Path entryPath = outputDir.resolve(zipEntry.getName().replace(repDirName, ""));

                if (!Files.exists(entryPath)) {
                    if (zipEntry.isDirectory()) {
                        Files.createDirectory(entryPath);
                    } else try (FileChannel fileChannel = FileChannel.open(entryPath, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
                        fileChannel.transferFrom(Channels.newChannel(zipIS), 0, Long.MAX_VALUE);
                    }
                }
            }
        }
    }
}
