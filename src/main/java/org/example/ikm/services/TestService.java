package org.example.ikm.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class TestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TransactionTemplate transactionTemplate;


    @Transactional
    public void executeSuccessfulTransaction() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.execute("INSERT INTO films.authors(name, birth_date, bio) VALUES ('Кристофер Нолан', '1970-07-30', 'М')");
                    jdbcTemplate.execute("INSERT INTO films.authors(name, birth_date, bio) VALUES ('Роберт Ли Земекис', '1952-05-14', 'М')");
                } catch (Exception e) {
                    status.setRollbackOnly();
                    throw new RuntimeException("Транзакция откачена", e);
                }
            }
        });
    }

    @Transactional
    public void executeWithRollback() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.execute("INSERT INTO films.authors(name, birth_date, bio) VALUES ('Квентин Тарантино', '1963-03-27', 'М')");
                    jdbcTemplate.execute("INSERT INTO films.authors(name, birth_date, bio) VALUES ('Дэвид Финчер', '1962-09-28', 'М')");
                    throw new RuntimeException("Транзакция откачена");
                } catch (Exception e) {
                    status.setRollbackOnly();
                    throw new RuntimeException("Транзакция откачена", e);
                }
            }
        });
    }


    @Transactional
    public void executeWithSavepoint() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.execute("SAVEPOINT savepoint1");
                    jdbcTemplate.execute("INSERT INTO films.authors(name, birth_date, bio) VALUES ('Майкл Бэй', '1965-02-17', 'М')");
                    jdbcTemplate.execute("ROLLBACK TO SAVEPOINT savepoint1");
                    jdbcTemplate.execute("INSERT INTO films.authors(name, birth_date, bio) VALUES ('Джордж Лукас', '1944-05-14', 'М')");
                } catch (Exception e) {
                    e.printStackTrace();
                    status.setRollbackOnly();
                    throw new RuntimeException("Произошла ошибка", e);
                }
            }
        });
    }
}