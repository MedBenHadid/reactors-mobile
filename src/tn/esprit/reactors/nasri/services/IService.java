/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.services;

import java.util.ArrayList;

/**
 *
 * @author nasri
 */
public interface IService<T> {
    boolean add(T entity);
    boolean delete(int id);
    boolean update(T entity);
    ArrayList<T> getAll();;
}
