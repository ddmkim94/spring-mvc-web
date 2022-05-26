package hello.itemservice.http.repository;

import hello.itemservice.http.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberRepository {

    private static final Map<Long, Member> memberMap = new HashMap<>();
    private static long sequence = 0L; // 시퀀스

    public Member save(Member member) {
        member.setId(++sequence);
        memberMap.put(member.getId(), member);
        return member;
    }

    public void update(Long id, Member param) {
        Member member = memberMap.get(id);
        member.setName(param.getName());
        member.setAge(param.getAge());
        memberMap.put(id, member);
    }

    public void delete(Long id) {
        memberMap.remove(id);
    }

    public Member findById(Long id) {
        return memberMap.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(memberMap.values());
    }

}
