package pro.master.com.Diagnotic;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("pro.master.com.Diagnotic")
@SpringBootApplication
@EnableFeignClients(basePackages = "pro.master.com.Diagnotic.Client")
public class DiagnoticApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiagnoticApplication.class, args);
	}

}
