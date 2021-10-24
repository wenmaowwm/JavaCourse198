package week5.homework8;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(StudentProperties.class)
@Import({Student.class, Klass.class})
@Data
public class StudentAtuoConfiguration {

    @Autowired
    StudentProperties studentProperties;

    @Autowired
    Student student102;

    @Autowired
    Student student100;
    // Resource
    @Autowired(required = true) //primary
    Klass class1;

    @Bean(name = "student102")
    public Student create102(){
        return new Student(studentProperties.getId1(),studentProperties.getName1(),null, null);
    }

    @Bean(name = "student100")
    public Student create100(){
        return new Student(studentProperties.getId2(),studentProperties.getName2(),null, null);
    }

    @Bean(name = "class1")
    public Klass createKlass(){
        List<Student> students = new ArrayList<>(2);
        students.add(student100);
        students.add(student102);
        return new Klass(students);
    }

    @Bean
    public School createSchool(){
        return new School(class1, student100);
    }
}
