package members.gymmembers.controllers;

import members.gymmembers.models.Member;
import members.gymmembers.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        member.setJoinDate(LocalDate.now());
        return ResponseEntity.ok(memberService.saveMember(member));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        return memberService.getMemberById(id)
                .map(existingMember -> {
                    existingMember.setFirstName(memberDetails.getFirstName());
                    existingMember.setLastName(memberDetails.getLastName());
                    existingMember.setEmail(memberDetails.getEmail());
                    existingMember.setPhoneNumber(memberDetails.getPhoneNumber());
                    Member updatedMember = memberService.saveMember(existingMember);
                    return ResponseEntity.ok(updatedMember);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}