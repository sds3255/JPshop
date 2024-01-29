package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)  //juit을 실행할때 스프링을 같이 실행하고자할때 넣어주면 됨.
@SpringBootTest //springBoot를 띄운 상태로 테스트를 하기위해서 꼭 필요하다.
@Transactional  //DB에 INSERT 쿼리가 실행되고 나서 rollback이 시행되는 로직으로 기본적으로 rollback을 시행한다.
                //(단 repository나 service단에서는 rollback하지 않는다.)
                //console에 select이외 쿼리는 보여주지 않는다.
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;   //transactional 어노테이션의 rollback으로 인해 쿼리가 보이지 않을때 사용하는 방법

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        //em.flush(); //console에 insert구문을 보여준다.
        assertEquals(member,memberRepository.findOne(savedId));
    }
    
    @Test(expected = IllegalStateException.class)  // try~catch문으로 예외처리하는 것을 간단하게 할수 있음
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        //try {
            memberService.join(member2);
        //} catch (IllegalStateException e){
        //    return;
        //}

        //then
         fail("예외가 발생해야 한다.");
    }
    
    
    
}