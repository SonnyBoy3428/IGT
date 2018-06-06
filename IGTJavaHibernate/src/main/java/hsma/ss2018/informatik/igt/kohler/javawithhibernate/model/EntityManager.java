package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public abstract class EntityManager {
	public SessionFactory sessionFactory;
	
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
	
	public void exit() {
		sessionFactory.close();
	}
}