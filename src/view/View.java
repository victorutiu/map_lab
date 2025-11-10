package view;

import controller.Controller;
import controller.IController;
import exceptions.*;
import model.adt.*;
import model.expression.*;
import model.state.ProgramState;
import model.statement.*;
import model.type.*;
import model.value.*;
import repository.IRepository;
import repository.Repository;

import java.util.Scanner;

public class View {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        int option = -1;

        while (option != 0) {
            System.out.println("\nChoose a program to run:");
            System.out.println("1. Example 1");
            System.out.println("2. Example 2");
            System.out.println("3. Example 3");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> runProgram(createExample1());
                case 2 -> runProgram(createExample2());
                case 3 -> runProgram(createExample3());
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option!");
            }
        }
    }

    public void runProgram(IStatement program) {
        try {
            ProgramState state = new ProgramState(
                    new MyStack<>(),
                    new MyDictionary<>(),
                    new MyList<>(),
                    new MyDictionary<>(),
                    program
            );

            IRepository repository = new Repository(state,"log.txt");
            IController controller = new Controller(repository);
            controller.setDisplayFlag(true);
            controller.allStep();

        } catch (Exception e) {
            System.out.println("Execution error: " + e.getMessage());
        }

    }

    public IStatement createFileExample() {
        IStatement declareVarf = new VariableDeclarationStatement("varf", new StringType());

        IStatement assignVarf = new AssignmentStatement("varf", new ValueExpression(new StringValue("src/test.in")));

        IStatement openFile = new OpenRFileStatement(new VariableExpression("varf"));

        IStatement declareVarc = new VariableDeclarationStatement("varc", new IntegerType());

        IStatement readVarc1 = new ReadFileStatement(new VariableExpression("varf"), "varc");

        IStatement printVarc1 = new PrintStatement(new VariableExpression("varc"));

        IStatement readVarc2 = new ReadFileStatement(new VariableExpression("varf"), "varc");

        IStatement printVarc2 = new PrintStatement(new VariableExpression("varc"));

        IStatement closeFile = new CloseRFileStatement(new VariableExpression("varf"));

        return new CompoundStatement(declareVarf, new CompoundStatement(assignVarf,
                                                       new CompoundStatement(openFile,
                                                           new CompoundStatement(declareVarc,
                                                                   new CompoundStatement(readVarc1,
                                                                           new CompoundStatement(printVarc1,
                                                                                   new CompoundStatement(readVarc2,
                                                                                           new CompoundStatement(printVarc2, closeFile)
                                                                                   )
                                                                           )
                                                                   )
                                                           )
                                                      )
                                                  )
              );
    }
    private IStatement createExample1() {
        // int v; v = 2; print(v);
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
    }

    private IStatement createExample2() {
        // int a; int b; a = 2 + 3 * 5; b = a + 1; print(b);
        return new CompoundStatement(
                new VariableDeclarationStatement("a", new IntegerType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntegerType()),
                        new CompoundStatement(
                                new AssignmentStatement("a",
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntegerValue(2)),
                                                new ArithmeticExpression(
                                                        new ValueExpression(new IntegerValue(3)),
                                                        new ValueExpression(new IntegerValue(5)),
                                                        3 // '*'
                                                ),
                                                1 // '+'
                                        )
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement("b",
                                                new ArithmeticExpression(
                                                        new VariableExpression("a"),
                                                        new ValueExpression(new IntegerValue(1)),
                                                        1 // '+'
                                                )
                                        ),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
    }

    private IStatement createExample3() {
        // bool a; int v; a = true; if (a) then v = 2 else v = 3; print(v);
        return new CompoundStatement(
                new VariableDeclarationStatement("a", new BooleanType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntegerType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
    }
}

