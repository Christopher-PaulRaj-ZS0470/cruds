package com.audius.music.core.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SqlExecutorService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int executeUpdate(String sql) {
        return entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public Object executeSelectSingle(String sql) {
        return entityManager.createNativeQuery(sql).getSingleResult();
    }
}
