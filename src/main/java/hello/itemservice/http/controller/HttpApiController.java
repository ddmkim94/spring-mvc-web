package hello.itemservice.http.controller;

import hello.itemservice.http.domain.Member;
import hello.itemservice.http.domain.UploadInfo;
import hello.itemservice.http.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
public class HttpApiController {

    private static final MemberRepository memberRepository = new MemberRepository();

    // @PostConstruct
    public void init() {
        memberRepository.save(new Member("동민", 29));
        memberRepository.save(new Member("연서", 36));
        memberRepository.save(new Member("은빈", 31));
        memberRepository.save(new Member("소민", 25));
    }

    // @GetMapping("/members")
    public String members(Model model) {
        model.addAttribute("members", memberRepository.findAll());
        return "http/members";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    // @PostMapping("/members")
    public Member addMember(@RequestBody Member member) {
        return memberRepository.save(new Member(member.getName(), member.getAge()));
    }

    @ResponseBody
    // @GetMapping("/members/{id}")
    public Member member(@PathVariable("id") Long id) {
        return memberRepository.findById(id);
    }

    /**
     * PUT: 리소스가 있으면 "완전히" 대체, 없으면 생성!
     *
     * @Pathvariable - URI 주소에서 {}안의 변수 이름과 파라미터 이름이 같은 경우 value 속성 생략 가능!!
     * - 파라미터 이름을 직접 지정하고 싶다면 value 속성에 {}안의 변수이름과 똑같은 이름을 지정!
     */
    @ResponseBody
    // @PutMapping("/members/{id}")
    public Member putMember(@PathVariable Long id, @RequestBody Member member) {
        Member findMember = memberRepository.findById(id);
        if (findMember == null) {
            return memberRepository.save(member);
        } else {
            memberRepository.update(id, member);
            return memberRepository.findById(id);
        }
    }

    /**
     * PATCH: 리소스 부분 변경!
     */
    @ResponseBody
    @PatchMapping("/members/{id}")
    public Member patchMember(@PathVariable Long id, @RequestBody Member member) {
        Member findMember = memberRepository.findById(id);
        if (member.getName() != null) {
            findMember.setName(member.getName());
        }
        if (member.getAge() != 0) {
            findMember.setAge(member.getAge());
        }
        return findMember;
    }

    @GetMapping("/save")
    public String fileForm() {
        return "http/files";
    }

    @ResponseBody
    @PostMapping("/save")
    public UploadInfo file(@RequestParam String username,
                           @RequestParam int age,
                           @RequestParam MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            long fileSize = file.getSize(); // 파일 사이즈
            String fileName = file.getOriginalFilename(); // 파일 이름
            String extension = fileName.substring(fileName.lastIndexOf(".")); // 파일 확장자
            String fileContentType = file.getContentType(); // contentType
            String fullPath = "/Users/dongmin/Desktop/test/" + fileName; // 파일 업로드 최종 경로

            file.transferTo(new File(fullPath)); // 지정한 경로에 파일 저장
            return new UploadInfo(username, age, fileName, extension, fileSize, fileContentType);
        } else {
            return new UploadInfo(username, age);
        }
    }
}