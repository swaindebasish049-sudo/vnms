package vcti.nms.vnms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VnmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VnmsApplication.class, args);
	}

}
