package com.treazy.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.treazy.demo.commands.input.InsertNodeCommand;
import com.treazy.demo.commands.input.UpdateNodeCommand;
import com.treazy.demo.commands.result.ChildrenCommandResult;
import com.treazy.demo.commands.result.NodeCommandResult;
import com.treazy.demo.entity.Node;
import com.treazy.demo.repository.NodeRepository;

@Service
public class NodeService {

	@Autowired
	private NodeRepository _nodeRepository;
	
	public Node Post(InsertNodeCommand command) {
		Node newNode = new Node();
		
		newNode.setCode(command.code);
		newNode.setDescription(command.description);
		newNode.setDetail(command.detail);
		
		if(command.parentId > 0) {
			newNode.setParent(GetById(command.parentId).get());			
		}

		
		return _nodeRepository.save(newNode);		
	}
	
	
	public ResponseEntity<Node> Put (long id, UpdateNodeCommand command){
        Optional<Node> oldNode = _nodeRepository.findById(id);
        if(oldNode.isPresent()){
            Node node = oldNode.get();
            
            node.setCode(command.code);
            node.setDescription(command.description);
            node.setDetail(command.detail);
            node.setParent(GetById(command.parentId).get());
            
            _nodeRepository.save(node);
            
            return new ResponseEntity<Node>(node, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	public Optional<Node> GetById (long id) {
		return _nodeRepository.findById(id);
	}
	
	public List<NodeCommandResult> Get () {	
		List<Node> listNode = _nodeRepository.findAll();
		
		List<NodeCommandResult> listaRetorno = new ArrayList<NodeCommandResult>();
		
		for(Node node: listNode) {
			NodeCommandResult nodeResult = new NodeCommandResult();
			
			nodeResult.code = node.getCode();
			nodeResult.description = node.getDescription();
			nodeResult.id = node.getId();
			nodeResult.detail = node.getDetail();
			
			if(node.getParent() != null) {
				nodeResult.parentId = node.getParent().getId();
			}
			
			listaRetorno.add(nodeResult);
		}
		
		return listaRetorno;
	}
	
	public List<ChildrenCommandResult> GetByParent (long parentId){
		
		List<Node> listaChildren = _nodeRepository.findByParentId(parentId);
		List<ChildrenCommandResult> listItem = new ArrayList<ChildrenCommandResult>();
		
		for(Node children: listaChildren) {
			ChildrenCommandResult item = new ChildrenCommandResult();
			
			item.code = children.getCode();
			item.description = children.getDescription();
			item.detail = children.getDetail();
			item.id = children.getId();
			
			if(children.getParent() != null) {
				item.parentId = children.getParent().getId();
			}
			
			if(!children.getChildren().isEmpty()) {
				item.hasChildren = true;
			}
		
			listItem.add(item);
		}
		
		return listItem;
	}
}
