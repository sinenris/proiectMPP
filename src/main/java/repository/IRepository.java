package repository;

//General repo with CRUD ops.
public interface IRepository<ID, T> {
    //Return the size of the list of entities.
    int size();
    //Save a T entity.
    void save(T entity);
    //Delete an entity by ID.
    void delete(ID id);
    //Update an entity.
    void update(ID id, T entity);
    //Find and return an entity by ID.
    T findOne(ID id);
    //Returns all entities.
    Iterable<T> findAll();
}