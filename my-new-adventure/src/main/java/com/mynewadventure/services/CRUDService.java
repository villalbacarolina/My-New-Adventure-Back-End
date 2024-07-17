package com.mynewadventure.services;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class CRUDService<T> {

    protected abstract JpaRepository<T, Long> getRepository();

    public List<T> get() {
        return getRepository().findAll();
    }

    public Optional<T> get(Long id) {
        return getRepository().findById(id);
    }

    public void create(T t) {
        getRepository().save(t);
    }

    public void edit(T t) {
        getRepository().save(t);
    }

    public void delete(Long id) {
        getRepository().deleteById(id);
    }
}
