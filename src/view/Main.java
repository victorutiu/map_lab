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
                new MyHeap(),
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
                new MyHeap(),
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
                new MyHeap(),
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
                new MyHeap(),
                ex4
        );

        IRepository repo4 =  new Repository(prg4, "log4.txt");
        IController ctr4 = new Controller(repo4);


        IStatement ex5 = new CompoundStatement(
                                new VariableDeclarationStatement("v", new RefType(new IntegerType())),
                                new CompoundStatement(
                                        new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                                        new CompoundStatement(
                                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntegerType()))),
                                                new CompoundStatement(
                                                        new NewStatement("a", new VariableExpression("v")),
                                                        new CompoundStatement(
                                                            new PrintStatement(new VariableExpression("v")),
                                                            new PrintStatement(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                );


        ProgramState prg5 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<StringValue, BufferedReader>(),
                new MyHeap(),
                ex5
        );

        IRepository repo5 = new Repository(prg5, "log5.txt");
        IController ctr5 = new Controller(repo5);


        IStatement ex6 = new CompoundStatement(
                                new VariableDeclarationStatement("v", new RefType(new IntegerType())),
                                new CompoundStatement(
                                        new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                                        new CompoundStatement(
                                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntegerType()))),
                                                new CompoundStatement(
                                                        new NewStatement("a", new VariableExpression("v")),
                                                        new CompoundStatement(
                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                                new PrintStatement(
                                                                        new ArithmeticExpression(
                                                                            new ReadHeapExpression(
                                                                                new ReadHeapExpression(
                                                                                        new VariableExpression("a")
                                                                                )
                                                                            ),
                                                                        new ValueExpression(new IntegerValue(5)),
                                                                        1
                                                                        )
                                                                )
                                                         )
                                                )
                                        )
                                )
                    );


        ProgramState prg6 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<StringValue, BufferedReader>(),
                new MyHeap(),
                ex6
        );

        IRepository repo6 = new Repository(prg6, "log6.txt");
        IController ctr6 = new Controller(repo6);


        IStatement ex7 = new CompoundStatement(
                                new VariableDeclarationStatement("v", new RefType(new IntegerType())),
                                new CompoundStatement(
                                        new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new CompoundStatement(
                                                        new WriteHeapStatement("v", new ValueExpression(new IntegerValue(30))),
                                                        new PrintStatement(
                                                            new ArithmeticExpression(
                                                                new ReadHeapExpression(new VariableExpression("v")),
                                                                new ValueExpression(new IntegerValue(5)),
                                                                1
                                                            )
                                                        )
                                                )
                                        )
                                )
                        );


        ProgramState prg7 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<StringValue, BufferedReader>(),
                new MyHeap(),
                ex7
        );

        IRepository repo7 = new Repository(prg7, "log7.txt");
        IController ctr7 = new Controller(repo7);



        IStatement ex8 = new CompoundStatement(
                                new VariableDeclarationStatement("v", new RefType(new IntegerType())),
                                new CompoundStatement(
                                        new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                                        new CompoundStatement(
                                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntegerType()))),
                                                new CompoundStatement(
                                                        new NewStatement("a", new VariableExpression("v")),
                                                        new CompoundStatement(
                                                                new NewStatement("v", new ValueExpression(new IntegerValue(30))),
                                                                new PrintStatement(
                                                                    new ReadHeapExpression(
                                                                        new ReadHeapExpression(
                                                                                new VariableExpression("a")
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );



        ProgramState prg8 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<StringValue, BufferedReader>(),
                new MyHeap(),
                ex8
        );
        IRepository repo8 = new Repository(prg8, "log8.txt");
        IController ctr8 = new Controller(repo8);

        IStatement exWhile = new CompoundStatement(
                                    new VariableDeclarationStatement("v", new IntegerType()),
                                    new CompoundStatement(
                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(4))),
                                        new CompoundStatement(
                                                new WhileStatement(
                                                        new RelationalExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntegerValue(0)),
                                                        ">"
                                                        ),
                                                        new CompoundStatement(
                                                                new PrintStatement(new VariableExpression("v")),
                                                                new AssignmentStatement(
                                                                "v",
                                                                    new ArithmeticExpression(
                                                                        new VariableExpression("v"),
                                                                        new ValueExpression(new IntegerValue(1)),
                                                                        2  // '-' operation
                                                                )
                                                        )
                                                )
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                );


        ProgramState prgW = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<>(),
                new MyHeap(),
                exWhile
        );

        IRepository repoW = new Repository(prgW, "logWhile.txt");
        IController ctrW = new Controller(repoW);

        IStatement exampleConcurrent = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a", new RefType(new IntegerType())),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntegerValue(22))),
                                        new CompoundStatement(

                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WriteHeapStatement("a", new ValueExpression(new IntegerValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),

                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(
                                                                new ReadHeapExpression(
                                                                        new VariableExpression("a")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        ProgramState prgConcurrent = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<>(),
                new MyHeap(),
                exampleConcurrent
        );

        IRepository repoConcurrent = new Repository(prgConcurrent, "logConcurrent.txt");
        Controller ctrConcurrent = new Controller(repoConcurrent);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExample("9", exWhile.toString(), ctrW));
        menu.addCommand(new RunExample("10", exampleConcurrent.toString(), ctrConcurrent));
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

