/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.List;

/**
 *
 * @author daoducdanh
 */
public interface BaseDAL<T> {
    public List<T> findAll();
    public T findById(String id);
    public boolean insert(T t);
    public boolean update(T t);
    public boolean delete(String id);
    public String generateId();
}
