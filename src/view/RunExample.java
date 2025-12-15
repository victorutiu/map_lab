package view;

import controller.IController;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.state.ProgramState;
import model.statement.IStatement;
import model.type.IType;

public class RunExample extends Command {
    private IController controller;

    public RunExample(String key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            ProgramState program = controller.getRepository().getProgramStates().get(0);
            IStatement originalProgram = program.getOriginalProgram();

            MyIDictionary<String, IType> typeEnvironment = new MyDictionary<>();
            originalProgram.typecheck(typeEnvironment);
            controller.setDisplayFlag(true);
            controller.allStep();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
