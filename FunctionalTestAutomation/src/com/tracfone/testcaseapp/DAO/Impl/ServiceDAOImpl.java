package com.tracfone.testcaseapp.DAO.Impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tracfone.testcaseapp.DAO.ServiceDAO;
import com.tracfone.testcaseapp.Entity.Brand;
import com.tracfone.testcaseapp.Entity.Channel;
import com.tracfone.testcaseapp.Entity.EnvDetail;
import com.tracfone.testcaseapp.Entity.EsnReference;
import com.tracfone.testcaseapp.Entity.Flow;
import com.tracfone.testcaseapp.Entity.PinReference;
import com.tracfone.testcaseapp.Entity.SimReference;
import com.tracfone.testcaseapp.Entity.Testcase;
import com.tracfone.testcaseapp.Entity.TestcaseExecution;

public class ServiceDAOImpl implements ServiceDAO {

	public void addTestCase(Testcase testcase) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.persist(testcase);
			et.commit();
			em.close();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}

	public List<EsnReference> getCompatibleData() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query query = em.createQuery("SELECT e FROM EsnReference e");
		List<EsnReference> result = query.getResultList();
		if (result != null && result.size() > 0) {
			Iterator<EsnReference> itr = result.iterator();
			while (itr.hasNext()) {
				EsnReference esnRef = itr.next();
				if (esnRef.getPinReferences() != null && esnRef.getPinReferences().size() > 0) {
					Iterator<PinReference> pinItr = esnRef.getPinReferences().iterator();
					while (pinItr.hasNext()) {
						pinItr.next();
					}
				}
				if (esnRef.getSimReferences() != null && esnRef.getSimReferences().size() > 0) {
					Iterator<SimReference> simItr = esnRef.getSimReferences().iterator();
					while (simItr.hasNext()) {
						simItr.next();
					}
				}
			}
		}
		et.commit();
		em.close();
		return result;
	}

	public void addENVDetails(EnvDetail envDetail) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(envDetail);
		et.commit();
		em.close();
	}

	public List<Testcase> retrieveAllTestCases() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query query = em.createQuery("SELECT e FROM Testcase e");
		List<Testcase> result = query.getResultList();
		et.commit();
		em.close();
		return result;
	}

	public List<Testcase> retrieveScenarioTestCases(int flowID) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query query = em.createQuery("SELECT e FROM Testcase e where e.flowid = " + flowID);
		List<Testcase> result = query.getResultList();
		et.commit();
		em.close();
		return result;
	}

	public String getEnvContext(String envName, int serviceID) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query query = em.createQuery("SELECT e.envcontext FROM EnvDetail e where e.id.envname = '" + envName // +
																												// "'");
				+ "' and e.id.domain = " + serviceID);
		String result = (String) query.getSingleResult();
		et.commit();
		em.close();
		return result;
	}

	public List<EnvDetail> getEnvDetails() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query query = em.createQuery("SELECT e FROM EnvDetail e");
		List<EnvDetail> result = query.getResultList();
		et.commit();
		em.close();
		return result;
	}

	public List<Flow> getFlowDetails() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query query = em.createQuery("SELECT e FROM Flow e");
		List<Flow> result = query.getResultList();
		et.commit();
		em.close();
		return result;
	}

	public List<Brand> getBrandDetails() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query query = em.createQuery("SELECT e FROM Brand e");
		List<Brand> result = query.getResultList();
		et.commit();
		em.close();
		return result;
	}

	public List<Channel> getSourceSystemDetails() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query query = em.createQuery("SELECT e FROM Channel e");
		List<Channel> result = query.getResultList();
		et.commit();
		em.close();
		return result;
	}

	public String generateESN(String partNum) {
		Connection conn = null;
		CallableStatement  stmt = null;
		String esn = "" ;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@devdb.tracfone.com:6016/CLFYSITZ", "corecbo" , "abc123");
			stmt=conn.prepareCall ("{? = call GET_TEST_ESN(?)}");
		    stmt.registerOutParameter (1, Types.VARCHAR);
		    stmt.setString(2, partNum );       
		    stmt.execute(); 
		    esn =stmt.getString (1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return esn;
	}
	
	public String generateSIM(String partNum) {
		Connection conn = null;
		CallableStatement  stmt = null;
		String sim = "" ;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@devdb.tracfone.com:6016/CLFYSITZ", "corecbo" , "abc123");
			stmt=conn.prepareCall ("{? = call GET_TEST_SIM(?)}");
		    stmt.registerOutParameter (1, Types.VARCHAR);
		    stmt.setString(2, partNum );       
		    stmt.execute(); 
		    sim =stmt.getString (1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sim;
	}
	
	public String generatePIN(String partNum) {
		Connection conn = null;
		CallableStatement  stmt = null;
		String pin = "" ;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@devdb.tracfone.com:6016/CLFYSITZ", "corecbo" , "abc123");
			stmt=conn.prepareCall ("{? = call GET_TEST_PIN(?)}");
		    stmt.registerOutParameter (1, Types.VARCHAR);
		    stmt.setString(2, partNum );       
		    stmt.execute(); 
		    pin =stmt.getString (1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pin;
	}

	public void addTestCaseExecution(TestcaseExecution execution) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(execution);
		et.commit();
		em.close();
	}

	public Testcase getTestCase(String testCaseName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testCasePersistance");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Query query = em.createQuery("SELECT e FROM Testcase e where e.name = '" + testCaseName + "'");
		Testcase tc = (Testcase) query.getSingleResult();
		et.commit();
		em.close();
		return tc;
	}

}
