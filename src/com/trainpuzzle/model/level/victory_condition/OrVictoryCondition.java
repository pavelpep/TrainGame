package com.trainpuzzle.model.level.victory_condition;


public class OrVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public OrVictoryCondition() {
		//userObject = new TreeNodeUserObject(this,"Choose 1 objective");
	}
	
	protected boolean checkChildrenSatisfied() {
		boolean satisfied = false;
		if(this.getChildren().size() == 0) {
			satisfied = true;
		}
		else {
			for(VictoryCondition child : this.getChildren()) {
				if(child.isSatisfied()) {
					satisfied = true;
					break;
				}
			}
		}
		return satisfied;
	}

}