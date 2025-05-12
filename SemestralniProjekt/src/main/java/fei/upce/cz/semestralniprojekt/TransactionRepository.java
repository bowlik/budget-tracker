package fei.upce.cz.semestralniprojekt;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByOrderByDateDesc();
    
    @Query("SELECT t FROM Transaction t WHERE " +
           "(:fromDate IS NULL OR t.date >= :fromDate) AND " +
           "(:toDate IS NULL OR t.date <= :toDate) AND " +
           "(:type IS NULL OR t.type = :type) AND " +
           "(:currency IS NULL OR t.currency = :currency) " +
           "ORDER BY t.date DESC")
    List<Transaction> findWithFilters(
        @Param("fromDate") LocalDate fromDate,
        @Param("toDate") LocalDate toDate,
        @Param("type") Transaction.TransactionType type,
        @Param("currency") Transaction.Currency currency
    );
}