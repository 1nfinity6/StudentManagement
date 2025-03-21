package raisetech.student.managerment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class Application {

	private final Map<String, String> student = new HashMap<>();

	public static void main(String[] args) {
		//localhost:8080
		SpringApplication.run(Application.class, args);
	}

	@PostMapping("/studentInfo")
	public String addStudent(@RequestParam String name, @RequestParam String age) {
		student.put(name, age);
		return "学生情報を追加しました:" + name + "(" + age + "歳)";
	}

	@GetMapping("/studentInfo")
	public Map<String, String> getStudent() {
		return student;
	}
}

		//GET:取得する,リクエストの結果を受け取る
		//POST:情報を与える・渡す

