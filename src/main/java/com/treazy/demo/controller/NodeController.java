package com.treazy.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.treazy.demo.commands.input.InsertNodeCommand;
import com.treazy.demo.commands.input.UpdateNodeCommand;
import com.treazy.demo.commands.result.ChildrenCommandResult;
import com.treazy.demo.commands.result.NodeCommandResult;
import com.treazy.demo.entity.Node;
import com.treazy.demo.service.NodeService;

import io.swagger.annotations.ApiOperation;

@RestController
public class NodeController {

	@Autowired
	private NodeService _nodeService;
	
	@ApiOperation(value = "Insert a node.")
    @RequestMapping(value = "/node", method =  RequestMethod.POST, consumes="application/json")
    public long Post(@Valid @RequestBody InsertNodeCommand command)
    {
        return _nodeService.Post(command).getId();
    }
    
    @ApiOperation(value = "Update a node.")
    @RequestMapping(value = "/node/{id}", method =  RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<Node> Put(@PathVariable(value = "id") long id, @Valid @RequestBody UpdateNodeCommand command)
    {
        return _nodeService.Put(id, command);
    }
    
	
	@ApiOperation(value = "Returns the entire structure.")
	@RequestMapping(value = "/node", method = RequestMethod.GET, produces="application/json") 
	public List<NodeCommandResult> Get() { 
		return _nodeService.Get();
	}
	
	@ApiOperation(value = "Returns all nodes below a specific node.")
	@RequestMapping(value = "/node/{parentId}", method = RequestMethod.GET, produces="application/json") 
	public List<ChildrenCommandResult> GetByParent(@PathVariable(value = "parentId") long parentId) { 
		return _nodeService.GetByParent(parentId);
	}
	 

}
