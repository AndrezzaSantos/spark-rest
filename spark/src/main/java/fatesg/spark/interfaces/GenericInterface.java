package fatesg.spark.interfaces;

import java.util.List;

public interface GenericInterface<E> {
	
	void creat(final E entity);
	E read(final Integer id);
    void update(final E entity);
    void delete(final Integer id);
    
    void create(final List<E> entities);
    List<E> read(final List<Integer> ids);
    void update(final List<E> entities);
    void delete(final List<Integer> ids);

}
