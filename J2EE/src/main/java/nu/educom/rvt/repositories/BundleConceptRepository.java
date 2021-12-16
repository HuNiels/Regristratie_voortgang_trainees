package nu.educom.rvt.repositories;

import java.util.List;

import org.hibernate.Session;
import nu.educom.rvt.models.BundleConcept;

public class BundleConceptRepository {

	protected Session session;
	
	public BundleConceptRepository(Session session) {
		super();
		this.session = session;
	}

	public void create(BundleConcept bundleConcept) throws DatabaseException {
		if (!session.isOpen() || !session.getTransaction().isActive()) {
			throw new DatabaseException("Create called on an DB transaction that is not open");
		}
		session.save(bundleConcept);
	}
	
	public List<BundleConcept> createMulti(List<BundleConcept> crs) throws DatabaseException {
		for ( int i=0; i<crs.size(); i++ ) {
			int bundleId = (int)session.save(crs.get(i));
			session.save(crs.get(i));
			crs.get(i).setId(bundleId);
			if ( i % 20 == 0 ) { 
				//flush a batch of inserts and release memory:
				session.flush();
				session.clear();
			}
		}
		return crs;
	}
	
	public List<BundleConcept> readAll() throws DatabaseException {
		return HibernateSession.loadAllData(BundleConcept.class, session);
	}
	
	public BundleConcept readById(int id) throws DatabaseException {
		return session.get(BundleConcept.class, id);
	}
	
//	public List<BundleConcept> readByConceptId(int concept_id) throws DatabaseException {
//		return (List<BundleConcept>) session
//					.createQuery("from bundle_concept where concept_id =:concept_id", BundleConcept.class)
//					.setParameter("concept_id", concept_id)
//					.getResultList();
//	} This function isn't necessary yet, but I believe it will be in the future so it's already built. 
	
	public void update(BundleConcept bundleConcept) throws DatabaseException {
		session.saveOrUpdate(bundleConcept);
	}
	
	protected void delete() throws DatabaseException {
		
	}
	
}
	
	

