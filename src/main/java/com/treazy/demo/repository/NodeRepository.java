package com.treazy.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treazy.demo.entity.Node;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

	List<Node> findByParentId(long parentId);
}
