import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

plugins {
    id("base")
    id("idea")
}

val version: String = project.property("version") as String
val group_id: String = project.property("group_id") as String

subprojects {
    version = version
    group = group_id

    repositories {
        mavenCentral()
        mavenLocal()
    }

    pluginManager.withPlugin("java") {
        extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(21))
            }
            withSourcesJar()
        }
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

abstract class PushChangesTask : DefaultTask() {
    @TaskAction
    fun push() {
        val projectDir = project.rootProject.projectDir
        val status = runGit(projectDir, "git", "status", "--porcelain")
        if (status.trim().isEmpty()) {
            println("工作区没有需要提交的更改,直接推送")
        } else {
            println("当前更改的文件:")
            println(status)
            println("请输入提交信息:")
            val reader = BufferedReader(InputStreamReader(System.`in`))
            val message = reader.readLine()
            if (message == null || message.trim().isEmpty()) {
                throw GradleException("提交信息不能为空")
            }
            runGit(projectDir, "git", "add", ".")
            runGit(projectDir, "git", "commit", "-m", message)
        }
        runGit(projectDir, "git", "push", "origin", "HEAD")
        println(">>>推送完成")
    }

    private fun runGit(dir: File, vararg args: String): String {
        val proc = ProcessBuilder(*args)
            .directory(dir)
            .redirectErrorStream(true)
            .start()
        val output = proc.inputStream.bufferedReader().use { it.readText() }
        val exit = proc.waitFor()
        if (exit != 0) {
            throw RuntimeException("Git command failed: ${args.joinToString(" ")}\nError: $output")
        }
        return output
    }
}

tasks.named("build") {
    dependsOn(":app:build")
    dependsOn(":neoforge:build")
}

tasks.register<PushChangesTask>("pushChanges") {
    notCompatibleWithConfigurationCache("任务需要交互式输入并访问项目目录")
    description = "自动add,commit并推送当前分支"
}

abstract class ReleaseVersionTask : DefaultTask() {
    @TaskAction
    fun release() {
        val projectDir = project.rootProject.projectDir
        val tagName = project.version.toString()
        if (tagName.isEmpty() || tagName.contains("unspecified")) {
            throw GradleException("版本号无效:'$tagName', 请设置gradle.properties中的mod_version")
        }
        val status = runGit(projectDir, "git", "status", "--porcelain")
        if (status.trim().isEmpty()) {
            println("工作区没有需要提交的更改,直接推送")
        } else {
            println("当前更改的文件:")
            println(status)
            println("请输入提交信息:")
            val reader = BufferedReader(InputStreamReader(System.`in`))
            val message = reader.readLine()
            if (message == null || message.trim().isEmpty()) {
                throw GradleException("提交信息不能为空")
            }
            runGit(projectDir, "git", "add", ".")
            runGit(projectDir, "git", "commit", "-m", message)
        }
        runGit(projectDir, "git", "push", "origin", "HEAD")
        val remoteTags = runGit(projectDir, "git", "ls-remote", "--tags", "origin")
        if (remoteTags.contains("refs/tags/$tagName")) {
            throw GradleException("远程仓库已存在标签'$tagName', 请更新mod_version后再试")
        }
        runGit(projectDir, "git", "tag", "-a", tagName, "-m", "Release $tagName")
        runGit(projectDir, "git", "push", "origin", tagName)
        println(">>> 发布完成,标签${tagName}已推送,JitPack将开始构建")
    }

    private fun runGit(dir: File, vararg args: String): String {
        val proc = ProcessBuilder(*args)
            .directory(dir)
            .redirectErrorStream(true)
            .start()
        val output = proc.inputStream.bufferedReader().use { it.readText() }
        val exit = proc.waitFor()
        if (exit != 0) {
            throw RuntimeException("Git command failed: ${args.joinToString(" ")}\nError: $output")
        }
        return output
    }
}

tasks.register<ReleaseVersionTask>("releaseVersion") {
    notCompatibleWithConfigurationCache("任务需要交互式输入并访问项目目录")
    description = "自动add,commit,push并创建发布标签"
}