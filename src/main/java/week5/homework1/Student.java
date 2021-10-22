package week5.homework1;

import lombok.Data;
import week5.homework1.myProxy.AopProxy;

@Data
public class Student implements Hello {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public void hello() {
        System.out.println("hello! my name is " + name);
    }

    public static void main(String[] args) {
        Student s = new Student("wwm");
        AopProxy AopProxy = new AopProxy(s);
        Hello proxy = (Hello)AopProxy.CreateProxy();
        proxy.hello();
    }
}
