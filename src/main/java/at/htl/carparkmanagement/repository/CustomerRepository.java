package at.htl.carparkmanagement.repository;

import at.htl.carparkmanagement.entity.Contract;
import at.htl.carparkmanagement.entity.Customer;
import at.htl.carparkmanagement.entity.Location;
import at.htl.carparkmanagement.entity.Parkingspot;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    @Transactional
    public Customer find(Long id) {
        try {
            return this.findById(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    @Transactional
    public Customer add(Customer customer) {
        if(customer.getId() != null) {
            return null;
        }
        this.persist(customer);
        return customer;
    }

    @Transactional
    public Customer update(Customer customer) {
        if(customer.getId() != null) {
            Customer update = this.find(customer.getId());
            update.setFirstname(customer.getFirstname());
            update.setLastname(customer.getLastname());
            update.setIsPrivat(customer.getIsPrivat());
            return update;
        }
        return this.add(customer);
    }

    public List<Customer> getCustomerList() {
        return this.findAll().list();
    }

    @Transactional
    public boolean deleteCustomer(Long id) {
        Customer delete = this.find(id);
        if(delete == null) { return false; }
        this.delete(delete);
        return true;
    }

    /*@Inject
    Logger logger;

    @Inject
    EntityManager entityManager;


    public CustomerRepository() {
    }
    public List<Customer> getCustomerList() {
        TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
        return Collections.unmodifiableList(query.getResultList());
    }

    @Transactional
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }

    @Transactional
    public Customer addCustomer(Customer newItem) {
        if(newItem.getId() != null) { return null; }
        entityManager.persist(newItem);
        logger.log(Logger.Level.INFO, "add customer with id: " + newItem.getId());
        return newItem;
    }

    @Transactional
    public Customer updateCustomer(Customer updated) {
        if(updated.getId() == null) {
            return this.addCustomer(updated);
        }
        entityManager.merge(updated);
        logger.log(Logger.Level.INFO, "update customer with id: " + updated.getId());
        return updated;
    }

    @Transactional
    public boolean deleteCustomer(Customer toBeDeleted) {
        if(toBeDeleted == null) { return false; }
        if(!entityManager.contains(toBeDeleted)) {
            toBeDeleted = entityManager.merge(toBeDeleted);
        }
        entityManager.remove(toBeDeleted);
        return true;
    }

    // This method is only to be used in Unittests
    @Transactional
    public void clearList() {
        logger.log(Logger.Level.WARN, "Clear CustomerRepository list");
        entityManager.clear();
    }*/
}
