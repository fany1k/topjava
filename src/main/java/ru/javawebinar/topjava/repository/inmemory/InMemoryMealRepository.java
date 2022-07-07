package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;

@Repository

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> userMealsMap = new ConcurrentHashMap<>();


    private final AtomicInteger counter = new AtomicInteger(0);

    {
        userMealsMap.put(SecurityUtil.authUserId(), new HashMap<>());
        MealsUtil.meals.forEach(meal -> save(meal, USER_ID));


    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals;
        meals = userMealsMap.containsKey(userId) ? userMealsMap.get(userId) : new ConcurrentHashMap<>();
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }

        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = userMealsMap.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = userMealsMap.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> meals = userMealsMap.get(SecurityUtil.authUserId());
        meals.values().forEach(System.out::println);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}

