package hello.itemservice.http.controller;

import hello.itemservice.http.domain.Member;
import hello.itemservice.http.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
public class MemberController {

    private final MemberRepository memberRepository = new MemberRepository();

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("동민", 29));
        memberRepository.save(new Member("연서", 36));
        memberRepository.save(new Member("은빈", 31));
        memberRepository.save(new Member("소민", 25));
    }

    // 회원 목록 조회
    @GetMapping("/members")
    public String memberList(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    // 회원 등록 폼
    @GetMapping("/members/new")
    public String createdMemberForm() {
        return "members/new-form";
    }

    // 회원 등록
    @PostMapping("/members/new")
    public String createdMember(@ModelAttribute Member member) {
        memberRepository.save(member);
        return "redirect:/members";
    }

    // 회원 조회
    @GetMapping("/members/{id}")
    public String memberSearch(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id);
        log.info("회원 정보={}", member);
        model.addAttribute("member", member);
        return "members/memberSearch";
    }

    // 회원 수정 폼
    @GetMapping("/members/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id);
        model.addAttribute("member", member);
        return "members/edit-form";
    }

    // 회원 수정
    @PostMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Member updateParam) {
        log.info("updateParam={}", updateParam);
        memberRepository.update(id, updateParam);
        return "redirect:/members";
    }

    // 회원 삭제
    @PostMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id) {
        memberRepository.delete(id);
        return "redirect:/members";
    }
}
