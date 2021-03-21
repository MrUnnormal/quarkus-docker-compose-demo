package at.htl.carparkmanagement.repository;

import at.htl.carparkmanagement.entity.Contract;
import at.htl.carparkmanagement.entity.Location;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class ContractRepository implements PanacheRepository<Contract> {

    @Inject
    ParkingspotRepository parkingspotRepository;

    @Inject
    CustomerRepository customerRepository;

    @Transactional
    public Contract find(Long id) {
        try {
            return this.findById(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    @Transactional
    public Contract add(Contract contract) {
        if(contract.getId() != null) {
            return null;
        }
        parkingspotRepository.add(contract.getParkingspot());
        customerRepository.add(contract.getCustomer());
        this.persist(contract);
        return contract;
    }

    @Transactional
    public Contract update(Contract contract) {
        if(contract.getId() != null) {
            Contract update = this.find(contract.getId());
            update.setParkingspot(contract.getParkingspot());
            update.setCustomer(contract.getCustomer());
            update.setEndDate(contract.getEndDate());
            update.setStartDate(contract.getStartDate());
            update.setPayDate(contract.getPayDate());
            return update;
        }
        return this.add(contract);
    }

    @Transactional
    public boolean deleteContract(Long id) {
        Contract delete = this.find(id);
        if(delete == null) { return false; }
        this.delete(delete);
        return true;
    }

    /*
    @Inject
    Logger logger;

    @Inject
    EntityManager entityManager;

    @Transactional
    public List<Contract> getContractList() {
        TypedQuery<Contract> query = entityManager.createQuery("SELECT c FROM Contract c", Contract.class);
        return Collections.unmodifiableList(query.getResultList());
    }

    @Transactional
    public Contract findById(Long id) {
        if(id == null) { return null; }
        return entityManager.find(Contract.class, id);
    }

    @Transactional
    public Contract addContract(Contract newItem) {
        if(newItem.getId() != null) { return null; }
        if(newItem.getCustomer() != null && newItem.getCustomer().getId() == null) {
            newItem.setCustomer(customerRepository.addCustomer(newItem.getCustomer()));
        }
        if(newItem.getParkingspot() != null && newItem.getParkingspot().getId() == null) {
            newItem.setParkingspot(parkingspotRepository.addParkingspot(newItem.getParkingspot()));
        }
        entityManager.persist(newItem);
        logger.log(Logger.Level.INFO, "add contract with id: " + newItem.getId());
        return newItem;
    }

    @Transactional
    public Contract updateContract(Contract updated) {
        if(updated.getId() == null) {
            return this.addContract(updated);
        }
        entityManager.merge(updated);
        logger.log(Logger.Level.INFO, "update contract with id: " + updated.getId());
        return updated;
    }

    @Transactional
    public boolean deleteContract(Contract toBeDeleted) {
        if(toBeDeleted == null) { return false; }
        if(!entityManager.contains(toBeDeleted)) {
            toBeDeleted = entityManager.merge(toBeDeleted);
        }
        entityManager.remove(toBeDeleted);
        return true;
    }

    // This method is only to be used in Unittests
    public void clearList() {
        logger.log(Logger.Level.WARN, "Clear ContractRepository list");
        entityManager.clear();
    }*/
}
