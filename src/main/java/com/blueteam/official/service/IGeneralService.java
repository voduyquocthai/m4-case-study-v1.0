package com.blueteam.official.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();

    Page<T> findAll(Pageable pageable);

    T save(T t);

    Optional<T> findById(Long id);

    void remove(Long id);
}
