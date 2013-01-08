package com.trainpuzzle.model.level.victory_condition;

public class AndVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public AndVictoryCondition() {

	}
	
	protected boolean checkChildrenSatisfied() {
		boolean satisfied =true;
		for(VictoryCondition child : this.getChildren()) {
			if(!child.isSatisfied()) {
				satisfied = false;
				break;
			}
		}
		return satisfied;
	}
	
}