package com.console.utils;

import java.nio.file.Path;
import java.util.ArrayList;

public class ConsoleConstants {
    public static final Path LAUNCHER_DIRECTORY = Path.of(System.getenv("APPDATA"), ".console");

    public static final Path GAMES_DIRECTORY = LAUNCHER_DIRECTORY.resolve("games");

    public static final Path ASSETS_DIRECTORY = LAUNCHER_DIRECTORY.resolve("assets");

    public static final Path JAVA_EXE = ASSETS_DIRECTORY.resolve(Path.of("jre", "bin", "java.exe"));

    public static final Path PREFERENCES_JSON = LAUNCHER_DIRECTORY.resolve("preferences.json");

    public static final ArrayList<String> DEFAULT_JAVA_ARGUMENTS = new ArrayList<>() {{
        add("-XX:-UseAdaptiveSizePolicy");
        add("-XX:-OmitStackTraceInFastThrow");
        add("-Dfml.ignorePatchDiscrepancies=true");
        add("-Dfml.ignoreInvalidMinecraftCertificates=true");
        add("-Djava.library.path=" + ASSETS_DIRECTORY.resolve("natives"));
        add("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
        add("-Dminecraft.api.auth.host=http://127.0.0.1");
        add("-Dminecraft.api.account.host=http://127.0.0.1");
        add("-Dminecraft.api.session.host=http://127.0.0.1");
        add("-Dminecraft.api.services.host=http://127.0.0.1");
        add("-Dlog4j.configurationFile=" + ASSETS_DIRECTORY.resolve("log4j2_112-116.xml"));
    }};

    public static final ArrayList<String> DEFAULT_MINECRAFT_ARGUMENTS = new ArrayList<>() {{
        add("--version"); add("1.12.2");
        add("--assetsDir"); add(ASSETS_DIRECTORY.resolve("assets").toString());
        add("--assetIndex"); add("1.12.2-forge");
        add("--userType"); add("mojang");
        add("--tweakClass"); add("net.minecraftforge.fml.common.launcher.FMLTweaker");
        add("--versionType"); add("Forge");
    }};
    
    public static final ArrayList<String> DEFAULT_LIBRARIES = new ArrayList<>() {{
        add("libraries/com/google/code/gson/gson/2.8.0/gson-2.8.0.jar");
        add("libraries/com/google/guava/guava/21.0/guava-21.0.jar");
        add("libraries/com/ibm/icu/icu4j-core-mojang/51.2/icu4j-core-mojang-51.2.jar");
        add("libraries/com/mojang/authlib/1.5.25/authlib-1.5.25.jar");
        add("libraries/com/mojang/patchy/1.3.9/patchy-1.3.9.jar");
        add("libraries/com/mojang/realms/1.10.22/realms-1.10.22.jar");
        add("libraries/com/mojang/text2speech/1.10.3/text2speech-1.10.3.jar");
        add("libraries/com/paulscode/codecjorbis/20101023/codecjorbis-20101023.jar");
        add("libraries/com/paulscode/codecwav/20101023/codecwav-20101023.jar");
        add("libraries/com/paulscode/libraryjavasound/20101123/libraryjavasound-20101123.jar");
        add("libraries/com/paulscode/librarylwjglopenal/20100824/librarylwjglopenal-20100824.jar");
        add("libraries/com/paulscode/soundsystem/20120107/soundsystem-20120107.jar");
        add("libraries/com/typesafe/akka/akka-actor_2.11/2.3.3/akka-actor_2.11-2.3.3.jar");
        add("libraries/com/typesafe/config/1.2.1/config-1.2.1.jar");
        add("libraries/commons-codec/commons-codec/1.10/commons-codec-1.10.jar");
        add("libraries/commons-io/commons-io/2.5/commons-io-2.5.jar");
        add("libraries/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar");
        add("libraries/io/netty/netty-all/4.1.9.Final/netty-all-4.1.9.Final.jar");
        add("libraries/it/unimi/dsi/fastutil/7.1.0/fastutil-7.1.0.jar");
        add("libraries/java3d/vecmath/1.5.2/vecmath-1.5.2.jar");
        add("libraries/lzma/lzma/0.0.1/lzma-0.0.1.jar");
        add("libraries/net/java/dev/jna/jna/4.4.0/jna-4.4.0.jar");
        add("libraries/net/java/dev/jna/platform/3.4.0/platform-3.4.0.jar");
        add("libraries/net/java/jinput/jinput/2.0.5/jinput-2.0.5.jar");
        add("libraries/net/java/jutils/jutils/1.0.0/jutils-1.0.0.jar");
        add("libraries/net/minecraft/launchwrapper/1.12/launchwrapper-1.12.jar");
        add("libraries/net/minecraftforge/forge/1.12.2-14.23.5.2860/forge-1.12.2-14.23.5.2860.jar");
        add("libraries/net/sf/jopt-simple/jopt-simple/5.0.3/jopt-simple-5.0.3.jar");
        add("libraries/net/sf/trove4j/trove4j/3.0.3/trove4j-3.0.3.jar");
        add("libraries/org/apache/commons/commons-compress/1.8.1/commons-compress-1.8.1.jar");
        add("libraries/org/apache/commons/commons-lang3/3.5/commons-lang3-3.5.jar");
        add("libraries/org/apache/httpcomponents/httpclient/4.3.3/httpclient-4.3.3.jar");
        add("libraries/org/apache/httpcomponents/httpcore/4.3.2/httpcore-4.3.2.jar");
        add("libraries/org/apache/logging/log4j/log4j-api/2.15.0/log4j-api-2.15.0.jar");
        add("libraries/org/apache/logging/log4j/log4j-api/2.8.1/log4j-api-2.8.1.jar");
        add("libraries/org/apache/logging/log4j/log4j-core/2.15.0/log4j-core-2.15.0.jar");
        add("libraries/org/apache/logging/log4j/log4j-core/2.8.1/log4j-core-2.8.1.jar");
        add("libraries/org/apache/maven/maven-artifact/3.5.3/maven-artifact-3.5.3.jar");
        add("libraries/org/jline/jline/3.5.1/jline-3.5.1.jar");
        add("libraries/org/lwjgl/lwjgl/lwjgl-platform/2.9.4-nightly-20150209/lwjgl-platform-2.9.4-nightly-20150209.jar");
        add("libraries/org/lwjgl/lwjgl/lwjgl/2.9.4-nightly-20150209/lwjgl-2.9.4-nightly-20150209.jar");
        add("libraries/org/lwjgl/lwjgl/lwjgl_util/2.9.4-nightly-20150209/lwjgl_util-2.9.4-nightly-20150209.jar");
        add("libraries/org/ow2/asm/asm-debug-all/5.2/asm-debug-all-5.2.jar");
        add("libraries/org/scala-lang/plugins/scala-continuations-library_2.11/1.0.2_mc/scala-continuations-library_2.11-1.0.2_mc.jar");
        add("libraries/org/scala-lang/plugins/scala-continuations-plugin_2.11.1/1.0.2_mc/scala-continuations-plugin_2.11.1-1.0.2_mc.jar");
        add("libraries/org/scala-lang/scala-actors-migration_2.11/1.1.0/scala-actors-migration_2.11-1.1.0.jar");
        add("libraries/org/scala-lang/scala-compiler/2.11.1/scala-compiler-2.11.1.jar");
        add("libraries/org/scala-lang/scala-library/2.11.1/scala-library-2.11.1.jar");
        add("libraries/org/scala-lang/scala-parser-combinators_2.11/1.0.1/scala-parser-combinators_2.11-1.0.1.jar");
        add("libraries/org/scala-lang/scala-reflect/2.11.1/scala-reflect-2.11.1.jar");
        add("libraries/org/scala-lang/scala-swing_2.11/1.0.1/scala-swing_2.11-1.0.1.jar");
        add("libraries/org/scala-lang/scala-xml_2.11/1.0.2/scala-xml_2.11-1.0.2.jar");
        add("libraries/oshi-project/oshi-core/1.1/oshi-core-1.1.jar;");
        add("1.12.2-forge.jar");
    }};
}
