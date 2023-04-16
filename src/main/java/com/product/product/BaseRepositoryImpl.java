package com.product.product;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public class BaseRepositoryImpl<E, ID> extends SimpleJpaRepository<E,ID> implements BaseRepository<E, ID>{

    private EntityManager em;
    public BaseRepositoryImpl(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.em = entityManager;
    }


    @Override
    public List<E> findBayQuery(String jpql, Map<String, Object> params) {

        TypedQuery<E> query = em.createQuery(jpql, getDomainClass());
        if(null != params){
            for (String key: params.keySet()){
                query.setParameter(key,params.get(key));
            }
        }

        return query.getResultList();
    }
}
