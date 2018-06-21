package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * This class functions as an abstract class from which all table managers extend.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 */
public abstract class EntityRepository {
	public static SessionFactory sessionFactory;
	
	/**
	 * Sets up the session factory properly.
	 */
	public void setUp() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure()
				.build();
		
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}catch(Exception ex) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	/**
	 * Closes a session factory for good.
	 */
	public void exit() {
		sessionFactory.close();
	}
}
