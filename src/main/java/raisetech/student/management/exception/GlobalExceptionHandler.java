//package raisetech.student.management.exception;

//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
//public class GlobalExceptionHandler {

//@ExceptionHandler(StudentNotFoundException.class)
//public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException e) {
//return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);}

//@ExceptionHandler(TestException.class)
//public ResponseEntity<String> handleTestException(TestException e) {
//return new ResponseEntity<>("テスト例外が発生しました: " + e.getMessage(),
//HttpStatus.INTERNAL_SERVER_ERROR);
//}

//@ExceptionHandler(MethodArgumentNotValidException.class)
//public ResponseEntity<String> handleMethodArgumentNotValidException(
//MethodArgumentNotValidException e) {
//String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
//return new ResponseEntity<>("バリデーションエラー: " + errorMessage, HttpStatus.BAD_REQUEST);
//}

//@ExceptionHandler(Exception.class)
//public ResponseEntity<String> handleOtherExceptions(Exception e) {
//return new ResponseEntity<>("予期しないエラー: " + e.getMessage(),
//HttpStatus.INTERNAL_SERVER_ERROR);
//}
//}