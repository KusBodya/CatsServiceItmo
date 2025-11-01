package ru.Bodyaaaa;

import models.Animal;
import models.Master;
import CRUD.Dao;

public class Main {
    public static void main(String[] args) {

        Dao<Master> masterDao = new Dao<>(Master.class);
        Dao<Animal> animalDao = new Dao<>(Animal.class);

        Master master = new Master("Иван", "30");

        Animal cat = new Animal("Барсик", "5", "Дворняга", master);
        Animal dog = new Animal("Рекс", "3", "Овчарка", master);

        masterDao.save(master);
        animalDao.save(cat);
        animalDao.save(dog);


        cat.getFriends().add(dog);
        dog.getFriends().add(cat);
        animalDao.update(cat);
        animalDao.update(dog);


    }
}