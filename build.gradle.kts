import io.papermc.paperweight.util.constants.SPIGOT_NAMESPACE
import me.kcra.takenaka.generator.accessor.AccessorType
import me.kcra.takenaka.generator.accessor.CodeLanguage
import org.sayandev.plugin.StickyNoteModules
import me.kcra.takenaka.generator.accessor.plugin.accessorRuntime
import java.util.*

plugins {
    id("java")
    id("java-library")
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("org.sayandev.stickynote.project")
    id("me.kcra.takenaka.accessor") version "1.2.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "me.syphix"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.sayandev.org/snapshots")
    maven("https://repo.sayandev.org/releases")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.screamingsandals.org/public")
    maven("https://jitpack.io/")
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("me.clip:placeholderapi:2.11.6")
    implementation("ir.syphix:PalladiumAPI:1.3.20")
    implementation("dev.triumphteam:triumph-gui:3.1.10")
    implementation(accessorRuntime())
    mappingBundle("me.kcra.takenaka:mappings:1.8.8+1.21.3")
}

stickynote {
    modules(StickyNoteModules.BUKKIT, StickyNoteModules.BUKKIT_NMS)
}

tasks {

    processResources {
        filesMatching(listOf("**plugin.yml", "**plugin.json")) {
            expand(
                "version" to project.version as String,
                "name" to rootProject.name,
                "description" to project.description
            )
        }
    }

    build {
        dependsOn(clean)
        dependsOn(shadowJar)
    }

    shadowJar {
        relocate("dev.triumphteam.gui", "me.syphix.thepit.libs.gui")
        relocate("ir.syphix.palladiumapi", "me.syphix.thepit.libs.palladiumapi")
        archiveFileName.set("${rootProject.name}-${version}.jar")
        archiveClassifier.set("")
        destinationDirectory.set(file(rootProject.projectDir.path + "/bin"))
        exclude("META-INF/**")
        from("LICENSE")
        minimize()
    }

    java {
        toolchain{
            languageVersion.set(JavaLanguageVersion.of(17))
        }
        withSourcesJar()
    }

    jar {
        enabled = true
    }

    runServer {
        minecraftVersion("1.20.4")
    }
}

java {
    disableAutoTargetJvm()
}

accessors {
    // you can select a version subset with versions or versionRange, i.e.:

    // versions("1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.6")
    // versionRange("1.20.1", "1.20.6")

    // if you don't, you will get accessors mapped for everything that the bundle offers, i.e. 1.8.8 to 1.20.6

    basePackage("me.syphix.thepit.nms") // this is the base package of the generated output, probably somewhere in your plugin/library's namespace
    namespaces(SPIGOT_NAMESPACE, "mojang") // these are the "namespaces" that can be queried on runtime, i.e. "spigot" (for Spigot/CraftBukkit/Paper), "searge" (for Forge), "mojang" (for Mojang-mapped Paper - >1.20.4), "yarn" (not useful on runtime), "intermediary" (for Fabric), "quilt" or "hashed" (for Quilt)
    accessorType(AccessorType.REFLECTION) // this is the generated accessor type, can be "none" (no accessor breakout classes are generated, only a mapping class that can be queried), "reflection" or "method_handles" (self-explanatory, java.lang.reflect or java.lang.invoke accessors)
    codeLanguage(CodeLanguage.JAVA)
    versionRange("1.8.8", "1.21.3")
    mappingWebsite("https://mappings.dev/")


    val ClientboundAddEntityPacket = "net.minecraft.network.protocol.game.ClientboundAddEntityPacket"
    val EntityType = "net.minecraft.world.entity.EntityType"
    val Entity = "net.minecraft.world.entity.Entity"
    val LivingEntity = "net.minecraft.world.entity.LivingEntity"
    val BlockEntity = "net.minecraft.world.level.block.entity.BlockEntity"
    val Vec3 = "net.minecraft.world.phys.Vec3"
    val Vec3i = "net.minecraft.core.Vec3i"

    // there are many more options, like mapping for clients, IntelliJ's source JAR view and auto-complete are your friends (Ctrl+Click)

    // now, let's define what we want to access
    mapClass(ClientboundAddEntityPacket) {
        constructor(Int::class, UUID::class, Double::class, Double::class, Double::class, Float::class, Float::class, EntityType, Int::class, Vec3)
        constructor(Entity, Int::class)
        constructor(Entity)
        methodInferred("getId", "1.20.4")
    }

}

