package com.wellsfargo.training.prism.repository;

import java.util.List;

//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wellsfargo.training.prism.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	//public List<Transaction> findAllByOrderByIdDesc();
	//public Optional<List<Transaction>>
	
	@Query("SELECT t from Transaction t "
			+ "where t.senderAccount =:accountNo or t.receiverAccount=:accountNo")
	List<Transaction> getTransactionsOfAccount(@Param("accountNo") Long accountNo);
	
	
}
