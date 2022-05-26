package hello.itemservice.http.controller;

import hello.itemservice.http.domain.User;
import hello.itemservice.http.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class UserController {

    private static final UserRepository userRepository = new UserRepository();

    @PostConstruct
    public void init() {
        userRepository.save(new User("user1", "윤수빈", "user1@gmail.com"));
        userRepository.save(new User("user2", "오연서", "user2@gmail.com"));
        userRepository.save(new User("user3", "박은빈", "user3@gmail.com"));
        userRepository.save(new User("user4", "전소민", "user4@gmail.com"));
    }

    @GetMapping("/")
    public String home() {
        return "users/index";
    }

    @ResponseBody
    @PostMapping("/users/new")
    public User saveUser(@ModelAttribute User user) {
        userRepository.save(user);
        return user;
    }

    @ResponseBody
    @PostMapping("/users/edit")
    public User updateUser(@ModelAttribute User user) {
        return userRepository.update(user.getNo(), user);
    }

    @ResponseBody
    @PostMapping("/users/delete")
    public void deleteUser(@RequestParam Long no) {
        userRepository.delete(no);
    }

    @ResponseBody
    @GetMapping("/users")
    public List<User> userList() {
        return userRepository.findAll(); // json 배열 반환
    }

}
