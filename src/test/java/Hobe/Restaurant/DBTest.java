package Hobe.Restaurant;

import Hobe.Restaurant.Domain.Member;
import Hobe.Restaurant.Repository.MemberMemoryRepository;
import Hobe.Restaurant.Repository.MemberRepository;
import Hobe.Restaurant.Service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



class DBTest {
    @Autowired MemberMemoryRepository memberRepository;
    @Autowired MemberService memberservice;
    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("hello");
        Member findMember = memberRepository.save(member);
        assertEquals(member.getName(), findMember.getName());
    }
}
