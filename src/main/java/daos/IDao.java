package daos;

public interface IDao<T> {
    T darDeAlta(T t);
    T buscar(int id);
    void eliminar(int id);
    void modificar(T t);
}
