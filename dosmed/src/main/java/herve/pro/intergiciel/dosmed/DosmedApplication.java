package herve.pro.intergiciel.dosmed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DosmedApplication {

	public static void main(String[] args) {
		SpringApplication.run(DosmedApplication.class, args);
	}

}
