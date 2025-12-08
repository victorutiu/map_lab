package model.adt;

import java.util.HashMap;
import java.util.Map;
import exceptions.DictionaryException;
import exceptions.VariableAlreadyExistsException;
import exceptions.VariableNotExistsException;

public class MyDictionary<Key, Value> implements MyIDictionary<Key, Value> {

    private Map<Key, Value> dictionary = new HashMap<>();

    @Override
    public void put(Key key, Value value) throws Exception {
        if (dictionary.containsKey(key)) {
            throw new VariableAlreadyExistsException("Variable '" + key + "' already exists in the dictionary.");
        }
        dictionary.put(key, value);
    }

    @Override
    public void update(Key key, Value value) throws Exception{
        if (!dictionary.containsKey(key)) {
            throw new VariableNotExistsException("Variable '" + key + "' does not exist in the dictionary.");
        }
        dictionary.put(key, value);
    }

    @Override
    public Value lookup(Key key) throws Exception{
        if (!dictionary.containsKey(key)) {
            throw new VariableNotExistsException("Variable '" + key + "' does not exists in the dictionary.");
        }
        return dictionary.get(key);
    }

    @Override
    public boolean isDefined(Key key) {
        return dictionary.containsKey(key);
    }

    @Override
    public void remove(Key key) throws Exception {
        if (!dictionary.containsKey(key)) {
            throw new DictionaryException("Key not found: " + key);
        }
        dictionary.remove(key);
    }

    @Override
    public Map<Key, Value> getContent() {
        return dictionary;
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public MyIDictionary<Key, Value> cloneDictionary() {
        MyDictionary<Key, Value> newDictionary = new MyDictionary<>();

        for (Key key : this.dictionary.keySet()) {
            newDictionary.dictionary.put(key, this.dictionary.get(key));
        }

        return newDictionary;
    }

}
