package controller;


import exceptions.*;
import model.state.ProgramState;
import model.statement.IStatement;
import model.adt.MyIStack;
import repository.IRepository;

import java.util.*;

import model.value.IValue;
import model.value.RefValue;

public class Controller implements IController {

    private IRepository repository;
    private boolean displayFlag = true;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

    public ProgramState oneStep(ProgramState state) throws Exception {
        MyIStack<IStatement> stack = state.getExecutionStack();

        if (stack.isEmpty()) {
            throw new StackIsEmptyException("Program state stack is empty");
        }

        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allStep() throws Exception {
        ProgramState program = repository.getCurrentProgram();
        repository.loggingProgramStateExec();

        while(!program.getExecutionStack().isEmpty()) {
            oneStep(program);

            List<Integer> roots = getAddressFromSymbolTable(program.getSymbolTable().getContent().values());
            List<Integer> reachable = getReachableAddresses(roots,program.getHeap().getContent());
            Map<Integer, IValue> newHeap = safeGarbageCollector(reachable, program.getHeap().getContent());
            program.getHeap().setContent(newHeap);

            repository.loggingProgramStateExec();

            if (displayFlag) {
                System.out.println(program.toString());
            }
        }

    }

    public void displayCurrentProgram() {
        ProgramState program = repository.getCurrentProgram();
        System.out.println(program);
    }

    private List<Integer> getAddressFromSymbolTable(Collection<IValue> symbolTableValues) {
        List<Integer> result = new ArrayList<>();

        for (IValue v : symbolTableValues) {
            if (v instanceof RefValue) {
                RefValue ref = (RefValue) v;
                result.add(ref.getAddress());
            }
        }

        return result;
    }

    private List<Integer> getReachableAddresses(List<Integer> roots,  Map<Integer, IValue> heap) {
        List<Integer> reachable = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        stack.addAll(roots);

        while (!stack.isEmpty()) {
            int address = stack.pop();

            if (!reachable.contains(address)) {
                reachable.add(address);

                IValue val = heap.get(address);
                if (val instanceof RefValue) {
                    int inner = ((RefValue) val).getAddress();
                    if (!reachable.contains(inner)) {
                        stack.push(inner);
                    }
                }
            }
        }
        return reachable;
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> reachable, Map<Integer, IValue> heap) {
        Map<Integer, IValue> newHeap = new HashMap<>();

        for (Map.Entry<Integer, IValue> entry : heap.entrySet()) {
            if (reachable.contains(entry.getKey())) {
                newHeap.put(entry.getKey(), entry.getValue());
            }
        }

        return newHeap;
    }
}
