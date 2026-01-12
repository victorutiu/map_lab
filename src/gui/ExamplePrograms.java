package gui;

import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.*;

import java.util.ArrayList;
import java.util.List;

public class ExamplePrograms {

    public static List<IStatement> getAll() {
        List<IStatement> list = new ArrayList<>();

        // 1) int v; v = 2; print(v);
        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );

        // 2) int a; int b; a = 2 + 3 * 5; b = a + 1; print(b);
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

        // 3) file example: uses "src/test.in"
        IStatement ex3 = createFileExample();

        // 4) (int a; int b; a=5; b=7; if (a < b) then print(true) else print(false))
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

        // 5) ref int v; new(v,20); ref ref int a; new(a,v); print(v); print(a);
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

        // 6) ref int v; new(v,20); ref ref int a; new(a,v);
        //    print(rH(v)); print(rH(rH(a)) + 5);
        IStatement ex6 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(new IntegerType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntegerType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(
                                                        new ReadHeapExpression(new VariableExpression("v"))
                                                ),
                                                new PrintStatement(
                                                        new ArithmeticExpression(
                                                                new ReadHeapExpression(
                                                                        new ReadHeapExpression(new VariableExpression("a"))
                                                                ),
                                                                new ValueExpression(new IntegerValue(5)),
                                                                1 // '+'
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        // 7) ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v) + 5);
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
                                                        1 // '+'
                                                )
                                        )
                                )
                        )
                )
        );

        // 8) ref int v; new(v,20); ref ref int a; new(a,v); new(v,30); print(rH(rH(a)));
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
                                                                new ReadHeapExpression(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        // 9) while:
        // int v; v=4; while (v > 0) { print(v); v = v - 1; } print(v);
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
                                                                2 // '-'
                                                        )
                                                )
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        // 10) concurrent (fork)
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
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        list.add(ex1);
        list.add(ex2);
        list.add(ex3);
        list.add(ex4);
        list.add(ex5);
        list.add(ex6);
        list.add(ex7);
        list.add(ex8);
        list.add(exWhile);
        list.add(exampleConcurrent);

        return list;
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

        return new CompoundStatement(declareVarf,
                new CompoundStatement(assignVarf,
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
