package week5.homework8;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Import;

import java.util.List;

@Data
@AllArgsConstructor
@Import(Student.class)
public class Klass {
    List<Student> students;

    public void dong(){
        System.out.println(this.getStudents());
    }
}
