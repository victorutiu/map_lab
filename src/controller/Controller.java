package controller;


import exceptions.*;
import model.state.ProgramState;
import model.statement.IStatement;
import model.adt.MyIStack;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import model.value.IValue;
import model.value.RefValue;

public class Controller implements IController {

    private IRepository repository;
    private boolean displayFlag = true;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

    private void oneStepForAllProg(List<ProgramState> programStateList) throws Exception {
        programStateList.forEach(prg -> {
            try {
                repository.loggingProgramStateExec(prg);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });

        List<Callable<ProgramState>> callList = programStateList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (() -> {
                    return p.oneStep();
                })).collect(Collectors.toList());

        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }).filter(p -> p != null).collect(Collectors.toList());

        programStateList.addAll(newProgramList);

        programStateList.forEach(prg -> {
            try {
                repository.loggingProgramStateExec(prg);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });

        repository.setProgramStates(programStateList);
    }
    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedProgramStates(repository.getProgramStates());

        while(programStateList.size() > 0) {
            ProgramState firstProgramState = programStateList.get(0);
            Map<Integer, IValue> heapContent = firstProgramState.getHeap().getContent();

            List<Integer> roots = programStateList.stream()
                    .flatMap(prg -> getAddressFromSymbolTable(
                            prg.getSymbolTable().getContent().values()).stream()).collect(Collectors.toList());

            List<Integer> reachable = getReachableAddresses(roots, heapContent);

            Map<Integer, IValue> newHeap = safeGarbageCollector(reachable, heapContent);

            firstProgramState.getHeap().setContent(newHeap);

            oneStepForAllProg(programStateList);

            programStateList = removeCompletedProgramStates(repository.getProgramStates());
        }

        executor.shutdownNow();

        repository.setProgramStates(programStateList);
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

    private List<ProgramState> removeCompletedProgramStates(List<ProgramState> programStates) {
        return programStates.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }
}
