package com.treazy.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Node {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false, name = "CODE")
	private String code;

	@Column(nullable = false, name = "DESCRIPTION")
	private String description;

	@Column(nullable = true, name = "DETAIL")
	private String detail;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name="parent_id")
	@JsonIgnoreProperties({"code", "description", "detail", "parent", "children"})
	private Node parent;

	@OneToMany(mappedBy="parent", fetch = FetchType.LAZY, orphanRemoval=true)
	private Set<Node> children = new HashSet<Node>();

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Set<Node> getChildren() {
		return children;
	}

	public void setChildren(Set<Node> children) {
		this.children = children;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}}
