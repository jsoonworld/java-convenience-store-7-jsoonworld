package store.repository;

import java.util.List;

public interface Repository<T> {
    List<T> loadAll();
}
