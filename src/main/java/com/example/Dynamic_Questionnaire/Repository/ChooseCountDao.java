package com.example.Dynamic_Questionnaire.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dynamic_Questionnaire.Entity.ChooseCount;
import com.example.Dynamic_Questionnaire.Entity.Options;

@Repository
public interface ChooseCountDao extends JpaRepository<ChooseCount, UUID> {
	List<ChooseCount> findByOptionsUuid(UUID opUuid);
}
