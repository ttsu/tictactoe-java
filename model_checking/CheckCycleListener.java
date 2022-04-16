import gov.nasa.jpf.JPFListener;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.VM;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.MethodInfo;

import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.bytecode.FieldInstruction;
import gov.nasa.jpf.vm.FieldInfo;


/**
 * @author Digen Gill
 *
 * Listener which prints out the row and col values for each transition.
 * Used for understanding how to fix the Cycles in the code
 *
 */
public class CheckCycleListener extends ListenerAdapter implements JPFListener {

	// keep track of values
	long row, col;

	public CheckCycleListener()
	{
		this.row = 0;
		this.col = 0;
	}


	public void instructionExecuted(VM vm, ThreadInfo currentThread, Instruction nextInstruction, Instruction executedInstruction)
	{
		// update row and col if new value
		if (executedInstruction instanceof FieldInstruction)
		{
		FieldInstruction ins = (FieldInstruction) executedInstruction;	
		FieldInfo field = ins.getFieldInfo();
		String type = field.getType();
      		String name = field.getName();
		if (type.equals("int")){
			long val = ins.getLastValue();
			if (name.equals("row") && this.row != val)
			{
				this.row = val;
			}
			else if (name.equals("col") && this.col != val)
			{
				this.col = val;
			}
		}
	}
	}

	public void stateAdvanced(Search s){
		System.out.println("advanced to "+s.getStateId()+": "+this.row+ " "+ this.col);
	}
	public void stateBacktracked(Search s){
		System.out.println("backtracked to "+s.getStateId()+": "+this.row+ " "+ this.col);
	}
	public void propertyViolated(Search s){
		System.out.println("violated at "+s.getStateId()+": "+this.row+ " "+ this.col);
	}

}
