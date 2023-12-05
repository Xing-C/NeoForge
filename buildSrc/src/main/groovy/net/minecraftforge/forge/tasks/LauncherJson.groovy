package net.minecraftforge.forge.tasks

import groovy.json.JsonBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.*

import java.nio.file.Files
import java.util.regex.Matcher
import java.util.regex.Pattern

import static net.minecraftforge.forge.tasks.Util.getArtifacts
import static net.minecraftforge.forge.tasks.Util.iso8601Now

abstract class LauncherJson extends DefaultTask {
    @OutputFile abstract RegularFileProperty getOutput()
    @InputFiles abstract ConfigurableFileCollection getInput()
    @Input Map<String, Object> json = new LinkedHashMap<>()

    @Internal final vanilla = project.project(':mcp').file('build/mcp/downloadJson/version.json')
    @Internal final timestamp = iso8601Now()
    //@Internal final id = "${project.rootProject.ext.MC_VERSION}-${project.name}${project.version.substring(project.rootProject.ext.MC_VERSION.length())}"
    @Internal final id = "${project.rootProject.ext.VERSION}"

    LauncherJson() {
        getOutput().convention(project.layout.buildDirectory.file('version.json'))

        dependsOn('universalJar')
        getInput().from(project.tasks.universalJar.archiveFile,
                vanilla)
    }

    @TaskAction
    protected void exec() {
        if (!json.libraries)
            json.libraries = []
        def libs = [:]
        getArtifacts(project, project.configurations.installer, false).each { key, lib -> libs[key] = lib }
        getArtifacts(project, project.configurations.moduleonly, false).each { key, lib -> libs[key] = lib }
        getArtifacts(project, project.configurations.gameLayerLibrary, false).each { key, lib -> libs[key] = lib }
        getArtifacts(project, project.configurations.pluginLayerLibrary, false).each { key, lib -> libs[key] = lib }

        libs.each { key, lib -> json.libraries.add(lib) }

        Files.writeString(output.get().asFile.toPath(), new JsonBuilder(json).toPrettyString())

        String json = Files.readString(output.get().asFile.toPath());
        // 定义要查找和替换的正则表达式
        String regex = "https://maven.neoforged.net/releases/net/neoforged/fancymodloader";
        String replacement = "https://p.mcxkly.cn/d/1/fancymodloader";

        // 创建Pattern对象
        Pattern pattern = Pattern.compile(regex);

        // 创建Matcher对象，并将其应用于JSON文本
        Matcher matcher = pattern.matcher(json);

        // 使用Matcher的replaceAll方法替换匹配到的内容
        json = matcher.replaceAll(replacement);

        // 将修改后的JSON文本保存到文件
        Files.writeString(output.get().asFile.toPath(), json);
    }
}
