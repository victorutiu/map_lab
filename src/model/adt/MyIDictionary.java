package model.adt;

import exceptions.DictionaryException;

import java.util.Map;

public interface MyIDictionary<Key, Value> {
    void put(Key key, Value value) throws Exception;
    void update(Key key, Value value) throws Exception;
    Value lookup(Key key) throws Exception;
    boolean isDefined(Key key);
    void remove(Key key) throws Exception;
    Map<Key, Value> getContent();
}

