package hello.itemservice.http.repository;

import hello.itemservice.http.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private static final Map<Long, User> usersMap = new HashMap<>();
    private static long sequence = 1L;

    public User save(User user) {
        user.setNo(sequence++);
        usersMap.put(user.getNo(), user);
        return user;
    }

    public User update(Long no, User updateParam) {
        User user = usersMap.get(no);
        user.setId(updateParam.getId());
        user.setName(updateParam.getName());
        user.setEmail(updateParam.getEmail());
        return user;
    }

    public void delete(Long no) {
        usersMap.remove(no);
    }

    public User findByNo(Long no) {
        return usersMap.get(no);
    }

    public List<User> findAll() {
        return new ArrayList<>(usersMap.values());
    }

}
