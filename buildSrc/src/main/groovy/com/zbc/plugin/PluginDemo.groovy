import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginDemo implements Plugin<Project> {

    @Override
    void apply(Project project) {
        //定义一个 Task
        project.task("MyTask") {
            doLast {
                println "MyTask doLast invoke..."
            }
        }
    }
}