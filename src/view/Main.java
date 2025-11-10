package view;

import controller.Controller;
import controller.IController;
import model.adt.*;
import model.expression.*;
import model.state.ProgramState;
import model.statement.*;
import model.type.*;
import model.value.*;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) {

        // int v; v = 2; print(v);
        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );

        ProgramState prg1 = new ProgramState(new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<StringValue, BufferedReader>(),
                ex1);

        IRepository repo1 = new Repository(prg1, "log1.txt");
        IController ctr1 = new Controller(repo1);


        // int a; int b; a = 2 + 3 * 5; b = a + 1; print(b);
        IStatement ex2 = new CompoundStatement(
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

        ProgramState prg2 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<StringValue, BufferedReader>(),
                ex2
        );
        IRepository repo2 = new Repository(prg2, "log2.txt");
        IController ctr2 = new Controller(repo2);


        /*
        string varf;
        varf = "test.in";
        openRFile(varf);
        int varc;
        readFile(varf,varc); print(varc);
        readFile(varf,varc); print(varc);
        closeRFile(varf)
         */
        IStatement ex3 = createFileExample();

        ProgramState prg3 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<StringValue, BufferedReader>(),
                ex3
        );
        IRepository repo3 = new Repository(prg3, "log3.txt");
        IController ctr3 = new Controller(repo3);


        //(int a; int b; a=5; b=7; if (a < b) then print(true) else print(false))
        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntegerType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntegerType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new IntegerValue(5))),
                                new CompoundStatement(
                                        new AssignmentStatement("b", new ValueExpression(new IntegerValue(7))),
                                        new IfStatement(
                                                new RelationalExpression(
                                                        new VariableExpression("a"),
                                                        new VariableExpression("b"),
                                                        "<"
                                                ),
                                                new PrintStatement(new ValueExpression(new BooleanValue(true))),
                                                new PrintStatement(new ValueExpression(new BooleanValue(false)))
                                        )
                                )
                        )
                )
        );

        ProgramState prg4 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<StringValue, BufferedReader>(),
                ex4
        );

        IRepository repo4 =  new Repository(prg4, "log4.txt");
        IController ctr4 = new Controller(repo4);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.show();
    }
        private static IStatement createFileExample() {
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
}

