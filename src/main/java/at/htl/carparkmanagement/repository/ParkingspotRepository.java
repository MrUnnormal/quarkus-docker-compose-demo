package at.htl.carparkmanagement.repository;

import at.htl.carparkmanagement.entity.Location;
import at.htl.carparkmanagement.entity.Parkingspot;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class ParkingspotRepository implements PanacheRepository<Parkingspot> {

    @Inject
    LocationRepository locationRepository;

    @Transactional
    public Parkingspot find(Long id) {
        try {
            return this.findById(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    @Transactional
    public Parkingspot add(Parkingspot parkingspot) {
        if(parkingspot.getId() != null) {
            return null;
        }
        locationRepository.add(parkingspot.getLocation());
        this.persist(parkingspot);
        return parkingspot;
    }

    @Transactional
    public Parkingspot update(Parkingspot parkingspot) {
        if(parkingspot.getId() != null) {
            Parkingspot update = this.find(parkingspot.getId());
            update.setLocation(parkingspot.getLocation());
            locationRepository.update(parkingspot.getLocation());
            update.setType(parkingspot.getType());
            update.setPosition(parkingspot.getPosition());
            update.setPricePerDay(parkingspot.getPricePerDay());
            return update;
        }
        return this.add(parkingspot);
    }

    public List<Parkingspot> getParkingspotList() {
        return this.findAll().list();
    }

    public List<Parkingspot> getFreeParkingspots(Location location) {
        return getEntityManager().createNamedQuery("Parkingspot.getFree", Parkingspot.class).setParameter("location", location).getResultList();
    }

    @Transactional
    public boolean deleteParkingspot(Long id) {
        Parkingspot delete = this.find(id);
        if(delete == null) { return false; }
        this.delete(delete);
        return true;
    }

    /*@Inject
    Logger logger;

    @Inject
    EntityManager entityManager;

    @Inject
    LocationRepository locationRepository;

    public ParkingspotRepository() {
    }

    @Transactional
    public List<Parkingspot> getParkingspotList() {
        TypedQuery<Parkingspot> query = entityManager.createQuery("SELECT p FROM Parkingspot p", Parkingspot.class);
        return Collections.unmodifiableList(query.getResultList());
    }

    @Transactional
    public Parkingspot updateParkingspot(Parkingspot updated) {
        if(updated.getId() == null) {
            return this.addParkingspot(updated);
        }
        Parkingspot toUpdate = this.findById(updated.getId());
        entityManager.merge(updated);
        logger.log(Logger.Level.INFO, "update parkingspot with id: " + updated.getId());
        return updated;
    }

    @Transactional
    public Parkingspot addParkingspot(Parkingspot newItem) {
        if(newItem.getId() != null) { return null; }
        if(newItem.getLocation().getId() == null) {
            newItem.setLocation(locationRepository.persist(newItem.getLocation()));
        }
        entityManager.persist(newItem);
        logger.log(Logger.Level.INFO, "add parkingspot with id: " + newItem.getId());
        return newItem;
    }

    @Transactional
    public boolean deleteParkingspot(Parkingspot toBeDeleted) {
        if(toBeDeleted == null) { return false; }
        if(!entityManager.contains(toBeDeleted)) {
            toBeDeleted = entityManager.merge(toBeDeleted);
        }
        entityManager.remove(toBeDeleted);
        return true;
    }

    @Transactional
    public Parkingspot findById(Long id) {
        return entityManager.find(Parkingspot.class, id);
    }

    @Transactional
    public List<Parkingspot> getFreeParkingspots(Location location) {
        if(location == null) {
            return null;
        }
        TypedQuery<Parkingspot> query =
                entityManager.createNamedQuery("Parkingspot.getFree", Parkingspot.class);
        query.setParameter("location", location);
        return query.getResultList();
    }

    @Transactional
    public void clearList() {
        logger.log(Logger.Level.WARN, "Clear ParkingspotRepository list");
        entityManager.clear();
    }*/
}
