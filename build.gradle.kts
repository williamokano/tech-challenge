plugins {
    id("java")
    id("application")
}

group = "dev.okano.camunda.techchallenge"
version = "1.0-SNAPSHOT"

application {
    mainClass = "dev.okano.camunda.techchallenge.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.camunda.bpm:camunda-engine:7.9.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
