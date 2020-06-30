package de.uniba.dsg.jaxrs.Services;
import de.uniba.dsg.jaxrs.ExamplesApi;
import de.uniba.dsg.jaxrs.model.Singleton;
import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.logic.Bottle;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BottleService extends ExamplesApi {

    public static final BottleService instance = new BottleService();
    private DB database = Singleton.getInstance().database;

    public String getBottles(int page, int pageSize, double minPrice, double maxPrice, String query) throws IOException {

        List<Bottle> bot;
        bot = database.bottles.stream().filter(b -> b.getPrice() >= minPrice && b.getPrice() <= maxPrice && b.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());

        int pageStart = page * pageSize;
        int pageEnd = (page * pageSize) + pageSize;

        int sizeMin = Math.min(bot.size(), pageStart);
        int sizeMax = Math.min(bot.size(), pageEnd);

        System.out.println(database.bottles.size());

        return database.listToJson(bot.subList(sizeMin, sizeMax ));
    }
    /**
     * Get Bottle by Id
     * @author: Deepika Arneja
     * @return Bottle
     */
    public Bottle getBottleById(final int id) {
        return this.database.bottles.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public Bottle submitBottle(final Bottle bottle) {
        bottle.setId(database.bottles.stream().map(Bottle::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
        database.bottles.add(bottle);
        System.out.println(database.bottles);
        return bottle;
    }

    public Bottle updateBottle(Bottle bot , int botId) {
        Bottle dbBot = database.bottles.stream().filter(c -> c.getId() == botId).findFirst().orElse(null);
        if(bot == null || dbBot == null) {
            return null;
        }
        dbBot.setPrice(bot.getPrice());
        dbBot.setInStock(bot.getInStock());
        return dbBot;
    }

}
