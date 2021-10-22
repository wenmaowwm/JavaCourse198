package week5.homework2;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import week5.homework1.Student;

import java.util.List;
@Data
@Component//自动装配
public class StudentService {
    @Autowired
    private List<Student> students;

    public StudentService(List<Student> students) {
        this.students = students;
    }
    public int studentCnt(){
        return students.size();
    }

}
