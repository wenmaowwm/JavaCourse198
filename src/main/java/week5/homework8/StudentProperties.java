package week5.homework8;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "student")
@Data
public class StudentProperties {
    private int id1 = 0;
    private String name1 = "0";
    private int id2 = 0;
    private String name2 = "0";
}
