package Hobe.Restaurant.Service;

import Hobe.Restaurant.Domain.Member;
import Hobe.Restaurant.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


public class MemberService {
    private final MemberRepository memberDB;

    public MemberService(MemberRepository memberDB) {

        this.memberDB = memberDB;
    }

    public boolean CheckLogin(String name, String phoneNum){
        Optional<Member> member = memberDB.findByName_Phone(name,phoneNum);
        return member.isPresent();
        //저장된 값이 존재하면 true를 반환하고, 값이 존재하지 않으면 false를 반환함.
    }
    public boolean Registraion(Member member){
        boolean a =validateDuplicateMember(member);
        if(a==false) {
            memberDB.save(member);
            return true;
        }
        else
            return false;
    }
    public boolean validateDuplicateMember(Member member){
        boolean b = memberDB.findByName(member.getName()).isPresent();
        return b;
    }
    public void printMember(){
        for(Member member : memberDB.findAll()){
            System.out.println(member.getName()+"등록");
        }
    }
    public Member getMember(String PhoneNumber){ //사용자의 전화번호로 멤버 서비스가 로그인한
        //회원의 객체를 넘겨줌.
        return memberDB.findByPhone(PhoneNumber).get();
    }

    public Member getMemberWithName(String Name){ //사용자의 이름으로 멤버 서비스가 로그인한
        //회원의 객체를 넘겨줌.
        return memberDB.findByName(Name).get();
    }
    public boolean Check_Booing_Member(long id){
        //만약 회원이 예약을 했으면 또 하면 안된다.
        return memberDB.findById(id).get().isAvailable_Booking();
    }
    public void Change_AvailableBooing(long id,boolean bool){
        memberDB.findById(id).get().setAvailable_Booking(bool);
    }

}
