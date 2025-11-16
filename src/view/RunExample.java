package view;

import controller.IController;

public class RunExample extends Command {
    private IController controller;

    public RunExample(String key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.setDisplayFlag(true);
            controller.allStep();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
