package app.netlify.dsubha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class MesseasyMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MesseasyMicroserviceApplication.class, args);
	}

}
