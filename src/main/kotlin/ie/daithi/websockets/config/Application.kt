package ie.daithi.websockets.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["ie.daithi.websockets"])
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}