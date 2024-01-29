package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext  //스프링이 entitymanager를 알아서 주입해줌
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){ //쿼리문을 jpql을 사용함(sql과 비슷하나 from의 대상이 table이 아닌 entity가 된다.)
        return em.createQuery("select m from Member m",Member.class).getResultList(); //member 데이터를 list로 만들어줌
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name=:name",Member.class)
                .setParameter("name",name) //파라미터 바인딩
                .getResultList();
    }
}
