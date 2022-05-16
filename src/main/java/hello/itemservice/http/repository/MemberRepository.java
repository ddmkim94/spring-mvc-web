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
        member.setId(sequence += 100);
        memberMap.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return memberMap.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(memberMap.values());
    }

}
