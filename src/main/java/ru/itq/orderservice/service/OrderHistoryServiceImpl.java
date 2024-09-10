package ru.itq.orderservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itq.orderservice.entity.Order;

import java.util.List;

@Slf4j
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService{
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Object[]> getOrderRevisions(Long orderId) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<Object[]> revisions = auditReader.createQuery()
                .forRevisionsOfEntity(Order.class, false, true)
                .add(AuditEntity.id().eq(orderId))
                .getResultList();

        log.info("Revisions for order with ID {}: {}", orderId, revisions);

        return revisions;
    }
}
