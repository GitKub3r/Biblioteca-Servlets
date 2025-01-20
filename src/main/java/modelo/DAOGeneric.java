package modelo;

import java.util.List;

public class DAOGeneric<T, ID> implements EntityKit{
    Class<T> myClass;
    Class<ID> myClassID;

    public DAOGeneric(Class<T> myClass, Class<ID> myClassID) {
        this.myClass = myClass;
        this.myClassID = myClassID;
    }

    public List<T> getAll() {
        return entityManager.createQuery("FROM " + myClass.getSimpleName()).getResultList();
    }

    public T getById(ID id) {
        return entityManager.find(myClass, id);
    }

    public void add(T entity) {
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }

    public void update(T entity) {
        transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
    }

    public void delete(T entity) {
        transaction.begin();
        entityManager.remove(entity);
        transaction.commit();
    }

    @Override
    public String toString() {
        return "DAOGeneric{" +
                "myClass=" + myClass.getSimpleName() +
                ", myClassID=" + myClassID +
                '}';
    }
}
