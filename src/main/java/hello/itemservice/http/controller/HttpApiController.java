package hello.itemservice.http.controller;

import hello.itemservice.http.domain.Member;
import hello.itemservice.http.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@Controller
public class HttpApiController {

    private static final MemberRepository memberRepository = new MemberRepository();

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("동민", 29));
        memberRepository.save(new Member("연서", 36));
        memberRepository.save(new Member("은빈", 31));
        memberRepository.save(new Member("소민", 25));
    }

    @GetMapping("/members")
    public String members(Model model) {
        model.addAttribute("members", memberRepository.findAll());
        return "http/members";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/members")
    public Member addMember(@RequestBody Member member) {
        return memberRepository.save(new Member(member.getName(), member.getAge()));
    }

    @ResponseBody
    @GetMapping("/members/{id}")
    public Member member(@PathVariable("id") Long id) {
        return memberRepository.findById(id);
    }
}
