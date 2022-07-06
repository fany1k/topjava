package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {


    @Autowired
    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId){
        return repository.get(id, userId);
    }

    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public boolean delete(int id, int userId) {
        return repository.delete(id, userId);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}