package com.society.repositories;

import com.society.entities.system.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementRepository extends JpaRepository<Statement, Integer> {
    Statement findStatementByMonthAndYear(Integer month, Integer year);
}
