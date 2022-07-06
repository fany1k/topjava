package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id, int userId) {
        log.info("MealRestController - get");
        return service.get(id, userId);
    }

    public Meal save(Meal meal, int userId) {
        log.info("MealRestController - save");
        return service.save(meal, userId);
    }

    public boolean delete(int id, int userId) {
        log.info("MealRestController - delete");
        return service.delete(id, userId);
    }

    public Collection<Meal> getAll(int userId) {
        log.info("MealRestController - getAll");
        return service.getAll(userId);
    }
}