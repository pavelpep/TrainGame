package com.trainpuzzle.model.level.victory_condition;


public class IfThenVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public IfThenVictoryCondition() {

	}
	
	protected boolean checkChildrenSatisfied() {
		boolean satisfied = true;
		for(VictoryCondition child : this.getChildren()) {
			if(!child.isSatisfied()) {
				satisfied = false;
				break;
			}
		}
		return satisfied;
	}
	
	@Override
	public void processEvent(Event event) {
		for(VictoryCondition child : this.getChildren()) {
			if(!child.isSatisfied()) {
				child.processEvent(event);
				break;
			}
		}
	}
	
	
}