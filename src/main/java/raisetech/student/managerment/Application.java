package raisetech.student.managerment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		//localhost:8080
		SpringApplication.run(Application.class, args);
	}
	@GetMapping("/hello")
	public String hello(){
		StringUtils.isAllBlank();
		return "Hello World";
	}
}
