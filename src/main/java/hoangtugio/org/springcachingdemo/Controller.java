package hoangtugio.org.springcachingdemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private StudentRepository studentRepository;
   List<Student> students;

    @PostMapping
    public Student save(@RequestBody Student student) {
        studentRepository.save(student);
        //Write through
        students = studentRepository.findAll();
        //Cache-aside Invalidation
        //students.clear();
        return student;
    }

    @GetMapping
    public List<Student> findAll() {
        if (students == null) {
            students = studentRepository.findAll();
            System.out.println("Updated List");
        }
        return students;
    }

    //Spring Cacheable - Tự tạo 1 entry cache tên là students, sau đó lấy return value của method để gán cho nó
    // Nếu có rồi thì return thẳng về entry cache mà ko thực thi hàm
    @Cacheable("students")
    @GetMapping("cache")
    public List<Student> findAll2() {
        System.out.println("Updated List2");
        return studentRepository.findAll();
    }

    // Evict: xóa toàn bộ cache "students" khi save
    @CacheEvict(value = "students", allEntries = true)
    // Put: lấy giá trị return của hàm set cho entry cache
    //@CachePut(value = "students")
    @PostMapping("cache")
    public Student save2(@RequestBody Student student) {
       return studentRepository.save(student);
    }
}
