package Hobe.Restaurant.Service;

import Hobe.Restaurant.Repository.MemberMemoryRepository;
import Hobe.Restaurant.Repository.MemberRepository;
import Hobe.Restaurant.Domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberMemoryRepository testDB;
    MemberService memberService;
    @BeforeEach
    public void beforeEach(){
        testDB= new MemberMemoryRepository();
        memberService = new MemberService(testDB);
    }
    @Test
    void 존재하지_않는_회원_로그인(){

        Assertions.assertThat(memberService.CheckLogin("hello","123") == false);
    }
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");
        member.setPhone_num("123");
        //when
        memberService.Registraion(member);
        //then
        Member find = testDB.findByName(member.getName()).get();
        Assertions.assertThat(member.getName()).isEqualTo(find.getName());
    }

    @Test
    void 존재회원_로그인(){
        Assertions.assertThat(memberService.CheckLogin("hello","123") == true);
    }

    @Test
    void validateDuplicateMember_작동_테스트(){
        Member member1 = new Member();
        member1.setName("valid");
        memberService.Registraion(member1);
        assertThrows(IllegalStateException.class, () -> memberService.validateDuplicateMember(member1));
    }

    @Test
    void 중복_회원_error_check(){
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.Registraion(member1);
        assertThrows(IllegalStateException.class, () -> memberService.Registraion(member2));
    }

    @Test
    void getMember_작동_테스트() {
        Member member = new Member();
        member.setName("checking");
        member.setPhone_num("1234");
        memberService.Registraion(member);

        Assertions.assertThat(memberService.getMember("1234")).isEqualTo(member);
    }
    @Test
    void Check_Booing_Member_테스트(){
        Member member = new Member();
        member.setName("booing_check");
        member.setPhone_num("1234");
        member.setId(1);
        memberService.Registraion(member);

        Assertions.assertThat(memberService.Check_Booing_Member(member.getId()) == true);
    }
    @Test
    void Change_AvailableBooing_테스트(){
        Member member = new Member();
        member.setName("AvailableBooing");
        member.setPhone_num("1234");
        member.setId(1);
        memberService.Registraion(member);

        Assertions.assertThat(memberService.Check_Booing_Member(member.getId()) == true);
        memberService.Change_AvailableBooing(member.getId(),false);
        Assertions.assertThat(memberService.Check_Booing_Member(member.getId()) == false);
    }

}