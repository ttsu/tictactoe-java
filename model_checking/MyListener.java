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
 *
 */
public class MyListener extends ListenerAdapter implements JPFListener {

	long row, col;

	public MyListener()
	{
		this.row = 0;
		this.col = 0;
	}


	public void instructionExecuted(VM vm, ThreadInfo currentThread, Instruction nextInstruction, Instruction executedInstruction)
	{
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
				System.out.println(name+ " "+ val);
			}
			else if (name.equals("col") && this.col != val)
			{
				System.out.println(name+ " "+ val);
			}
		}
	}


}
}
