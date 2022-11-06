package com.ayush.multidatasource.config;

import com.sun.istack.NotNull;
import org.springframework.transaction.*;

public class ReplicaAwareTransactionManager implements PlatformTransactionManager {

    private final PlatformTransactionManager wrapped;

    public ReplicaAwareTransactionManager(PlatformTransactionManager platformTransactionManager) {
        wrapped = platformTransactionManager;
    }

    @Override
    public @NotNull
    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
            TransactionRoutingDataSource.setReadonlyDataSource(definition != null && definition.isReadOnly());
            return wrapped.getTransaction(definition);
    }

    @Override
    public void commit(@NotNull TransactionStatus status) throws TransactionException {
            wrapped.commit(status);
    }

    @Override
    public void rollback(@NotNull TransactionStatus status) throws TransactionException {
            wrapped.rollback(status);
    }
}