package com.ChiriChat.DataAccessObject;

import java.util.List;

/**
 * Created by danny on 27/05/14.
 */
public interface DataAccessObject<T> {

    public T insert(T dto) throws Exception;
    public boolean update(T dto) throws Exception;
    public boolean delete(T dto) throws Exception;
    public T read(int id) throws Exception;

    public List<T> getAll() throws Exception;
}
