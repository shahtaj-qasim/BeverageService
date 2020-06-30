package de.uniba.dsg.jaxrs.Services;

import de.uniba.dsg.jaxrs.ExamplesApi;
import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Singleton;
import de.uniba.dsg.jaxrs.model.logic.Crate;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CrateService extends ExamplesApi {

   public static final CrateService instance = new CrateService();
    private DB database = Singleton.getInstance().database;

    public String getCrates(int page, int pageSize, double minPrice, double maxPrice, String query) throws IOException {

        List<Crate> cat;
        cat = database.crates.stream().filter(b -> b.getPrice() >= minPrice && b.getPrice() <= maxPrice && b.getBottle().getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());

        int pageStart = page * pageSize;
        int pageEnd = (page * pageSize) + pageSize;

        int sizeMin = Math.min(cat.size(), pageStart);
        int sizeMax = Math.min(cat.size(), pageEnd);

        System.out.println(database.crates.size());

        return database.listToJson(cat.subList(sizeMin, sizeMax ));
    }
    /**
     * Get Crate by Id
     * @author: Deepika Arneja
     * @return Crate
     */
    public Crate getCrateById(final int id) {
        return this.database.crates.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public Crate submitCrate(final Crate crate) {
        crate.setId(database.crates.stream().map(Crate::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
        database.crates.add(crate);
        System.out.println(database.crates);
        return crate;
    }

    public Crate updateCrate(Crate crate , int id) {
        Crate dbCrate = database.crates.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        if(crate == null || dbCrate == null) {
            return null;
        }

        dbCrate.setPrice(crate.getPrice());
        dbCrate.setInStock(crate.getInStock());
        dbCrate.setNoOfBottles(crate.getNoOfBottles());
        return dbCrate;
    }
}
