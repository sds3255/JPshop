package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기 전용이 주가 되기 때문에 readOnly를 true로 해주고 쓰기전용은 따로 annotation을 달아준다.
@RequiredArgsConstructor // final로 선언된 repository의 객체의 생성자를 자동으로 만들어준다. (Lombok이 제공하는 어노테이션.)
public class MemberService {
    //@Autowired
    private final MemberRepository memberRepository; //변경 불가능

    //@Autowired //스프링 최신버전에서는 생성자가 한개만 있을 경우 annotation을 하지않아도 스프링이 자동으로 인식해서 생성해준다.
    /*public MemberService(MemberRepository memberRepository) { //constructor injection: 클래스가 생성되면서 만들어지기때문에 중간에 변경되지 않는다. 이방법이 좋음.
        this.memberRepository = memberRepository;
    }*/

   /* @Autowired
    public void setMemberRepository(MemberRepository memberRepository) { //setter injection: 중간에 변경될 가능성이 있어 치명적인 단점이다.
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원가입
     */
    @Transactional//쓰기전용(save,update,delete)일 경우에는 readOnly를 넣으면 안된다.데이터 변경이 안됨 & 함수에 따로 달아줄 경우 우선순위가 먼저다.
    public Long join(Member member) {
        validateDuplicateMember(member);  //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원전체조회
    //@Transactional(readOnly = true)  //읽기전용(search)일 경우에는 readOnly를 넣어주는게 좋다.
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    
    //회원 한 건 조회
    //@Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void updtate(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
