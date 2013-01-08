package com.trainpuzzle.model.level.victory_condition;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicalVictoryCondition implements VictoryCondition {
	
	private List<VictoryCondition> childConditions = new ArrayList<VictoryCondition>();
	protected boolean conditionSatisfied = true;

	protected TreeNodeUserObject userObject;
	
	@Override
	public boolean isSatisfied() {
		boolean satisfied = checkChildrenSatisfied();
		if(conditionSatisfied != satisfied) {
			conditionSatisfied = satisfied;
			//treeModel.nodeChanged(getDisplayNode());
		}
		return conditionSatisfied;
	}
	

	
	@Override
	public void processEvent(Event event) {
		for(VictoryCondition child : childConditions) {
			child.processEvent(event);
		}
	}

	@Override
	public void resetEvents() {
		 conditionSatisfied = false;
		 resetChildrenEvents();
	}
	
	public void removeChildrens() {
		this.childConditions.clear();
	}
	
	private void resetChildrenEvents() {
		for(VictoryCondition child : childConditions) {
			child.resetEvents();
		}
	}
	
	public List<VictoryCondition> getChildren() {
		return childConditions;
	}
	
	public void addChild(VictoryCondition child) {
		childConditions.add(child);
		checkChildrenSatisfied();
	}
	

	public TreeNodeUserObject getUserObject() {
		return userObject;
	}
	
	protected abstract boolean checkChildrenSatisfied();
	
}
