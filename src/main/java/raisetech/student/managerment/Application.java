package raisetech.student.managerment;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class Application {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		//localhost:8080
		SpringApplication.run(Application.class, args);
	}
//READ読み出し　学生情報を取得
	@GetMapping("/student")
	public List<Student> getStudentList() {
		return repository.search();
	}

		//READ処理　受講生コース情報
		@GetMapping("/studentscourses")
				public List<StudentsCourses> getStudentsCoursesList() {
			return  repository.searchStudentsCourses();
		}
	}


//GET:取得する,リクエストの結果を受け取る
//POST:情報を与える・渡す

