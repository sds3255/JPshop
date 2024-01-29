package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if (item.getId() == null){
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    public List<Item> findAll() {
        //JPQL 은 데이터 베이스를 조회 하는 SQL이 아니라 객체를 조회 하는 객체 지향 쿼리
        //조회 하고자 하는 FROM 절은 엔티티명이 되며 WHERE 절은 엔티티 필드
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
