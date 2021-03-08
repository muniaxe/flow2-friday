package facades;

import entities.Customer;
import entities.ItemType;
import entities.Order;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class ShopFacade {

    private static ShopFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private ShopFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static ShopFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ShopFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Customer createCustomer(Customer c) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return c;
    }

    public ItemType createItemType(ItemType i) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(i);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return i;
    }

    public Order createOrder(Customer c) {
        EntityManager em = emf.createEntityManager();
        Order tmpOrder = new Order();
        try {
            em.getTransaction().begin();
            em.persist(tmpOrder);
            c.addOrder(tmpOrder);
            em.getTransaction().commit();
        } finally {
            em.close();
        } return tmpOrder;
    }

    public Customer getCustomerById(long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE id = :id", Customer.class);
        Customer tmpCustomer = (Customer) query.setParameter("id", id);
        return tmpCustomer;
    }

    public ItemType getItemTypeById(long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<ItemType> query = em.createQuery("SELECT i from ItemType i WHERE id = :id", ItemType.class);
        ItemType tmpItemType = (ItemType) query.setParameter("id", id);
        return tmpItemType;
    }

    public List<Customer> getAllCustomers() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
        List<Customer> cList = query.getResultList();
        return cList;
    }

}
