package hoangtugio.org.springcachingdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringCachingDemoApplication implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;
    public static void main(String[] args) {
        SpringApplication.run(SpringCachingDemoApplication.class, args);
        System.out.println("Hello World!");
    }

    @Override
    public void run(String... args) throws Exception {
        studentRepository.save(new Student(1,"A",5));
        studentRepository.save(new Student(2,"B",6));
        studentRepository.save(new Student(3,"C",7));
    }
}
