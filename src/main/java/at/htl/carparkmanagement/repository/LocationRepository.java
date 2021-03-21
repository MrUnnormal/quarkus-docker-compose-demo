package at.htl.carparkmanagement.repository;

import at.htl.carparkmanagement.entity.Contract;
import at.htl.carparkmanagement.entity.Location;
import at.htl.carparkmanagement.entity.Parkingspot;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class LocationRepository implements PanacheRepository<Location> {

    @Transactional
    public Location find(Long id) {
        try {
            return findById(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    @Transactional
    public Location add(Location location) {
        if(location.getId() != null) {
            return null;
        }
        this.persist(location);
        return location;
    }

    @Transactional
    public Location update(Location location) {
        if(location.getId() != null) {
            Location update = this.find(location.getId());
            update.setName(location.getName());
            update.setZipcode(location.getZipcode());
            return update;
        }
        return this.add(location);
    }

    public List<Location> getLocationList() {
        return this.findAll().list();
    }

    @Transactional
    public boolean deleteLocation(Long id) {

        Location delete = this.find(id);
        if(delete == null) { return false; }
        this.delete(delete);
        return true;
    }

//    public LocationRepository() {
//
//    }
//
//    /*public List<Location> getLocationList() {
//        return Collections.unmodifiableList(locationList);
//    }*/
//
//    @Transactional
//    public List<Location> getLocationList() {
//        TypedQuery<Location> query = entityManager.createQuery("SELECT l FROM Location l", Location.class);
//        return Collections.unmodifiableList(query.getResultList());
//    }
//
//    /*public Location findById(Long id) {
//        return this.getLocationList()
//                .stream().filter(l -> l.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//    }*/
//
//    @Transactional
//    public Location findById(Long id) {
//        return entityManager.find(Location.class, id);
//    }
//
//    /*public boolean addLocation(Location location) {
//        if(this.findById(location.getId()) == null) {
//            locationList.add(location);
//            logger.log(Logger.Level.INFO, "add location with id: " + location.getId());
//            return true;
//        }
//        return false;
//    }*/
//
//    @Transactional
//    public Location addLocation(Location newItem) {
//        if(newItem.getId() != null) {
//            return null;
//        }
//        entityManager.persist(newItem);
//        logger.log(Logger.Level.INFO, "add location with id: " + newItem.getId());
//        return newItem;
//    }
//
//    /*public boolean updateLocation(Location updated) {
//        Location toUpdate = this.findById(updated.getId());
//        if(toUpdate == null) {
//            this.addLocation(updated);
//            return false;
//        }
//        toUpdate.setName(updated.getName());
//        toUpdate.setZipcode(updated.getZipcode());
//        logger.log(Logger.Level.INFO, "update location with id: " + updated.getId());
//        return true;
//    }*/
//
//    @Transactional
//    public Location updateLocation(Location updated) {
//        if(updated.getId() == null) {
//            return this.addLocation(updated);
//        }
//        Location toUpdate = this.findById(updated.getId());
//        entityManager.merge(updated);
//        logger.log(Logger.Level.INFO, "update location with id: " + updated.getId());
//        return updated;
//    }
//
//    /*public boolean deleteLocation(Location location) {
//        return locationList.remove(location);
//    }*/
//
//    @Transactional
//    public boolean deleteLocation(Location location) {
//        if(location == null) { return false; }
//        if(!entityManager.contains(location)) {
//            location = entityManager.merge(location);
//        }
//        entityManager.remove(location);
//        return true;
//    }
//
//    // This method is only to be used in Unittests
//    public void clearList() {
//        logger.log(Logger.Level.WARN, "Clear LocationRepository list");
//        entityManager.clear();
//    }
}
