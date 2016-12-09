package hibernate;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateHelper {

	private static SessionFactory sf;
	private static String userTransaction;

	static {
		try {
			Configuration cfg = new Configuration().configure();// Initialize
			userTransaction = cfg.getProperty("jta.UserTransactionLookup");
			sf = cfg.buildSessionFactory();

		} catch (Exception ex) {
			//LoggerHelper.getLogger().error("Error Hibernate Initialization", ex);
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sf;
	}

	private static UserTransaction getUserTransaction() throws NamingException {
		return (UserTransaction) new InitialContext().lookup(userTransaction);
	}

	public static boolean isTxActive() throws Exception {
		return (HibernateHelper.getUserTransaction().getStatus() == Status.STATUS_ACTIVE);
		// return HibernateHelper.getSession().getTransaction().isActive();
	}

	public static boolean beginTx() throws Exception {

		boolean closeAfter = false;

		if (!HibernateHelper.isTxActive()) {

			if (HibernateHelper.getUserTransaction().getStatus() == Status.STATUS_MARKED_ROLLBACK
					|| HibernateHelper.getUserTransaction().getStatus() == Status.STATUS_ROLLEDBACK) {
//				LoggerHelper
//						.getLogger()
//						.warn("TRANSACTION_LOG|"+CommonUtil.getCallerClassNMethod()+"|Transaction was already started before the listener and is marked for rollback or rolled back from other thread,"
//								+ " so doing rollback to disassociate it with current thread");
				UserTransaction utx = HibernateHelper.getUserTransaction();
				utx.rollback();
			}

			closeAfter = true;
			HibernateHelper.getUserTransaction().begin();
			// HibernateHelper.getSession().beginTransaction();
		}

//		LoggerHelper.getLogger().debug("TRANSACTION_LOG|"+CommonUtil.getCallerClassNMethod() + " BEGIN : "+closeAfter);
		
		return closeAfter;
	}

	public static void commitTx(boolean closeAfter) throws Exception {
		
//		LoggerHelper.getLogger().debug("TRANSACTION_LOG|"+CommonUtil.getCallerClassNMethod() + " COMMIT : "+closeAfter);
		
		if (closeAfter) {
			UserTransaction utx = HibernateHelper.getUserTransaction();
			if (utx.getStatus() == Status.STATUS_ACTIVE) {
				utx.commit();
			}
			// HibernateHelper.getSession().getTransaction().commit();
		}
	}

	public static void rollbackTx(boolean closeAfter) {
		
//		LoggerHelper.getLogger().debug("TRANSACTION_LOG|"+CommonUtil.getCallerClassNMethod() + " ROLLBACK : "+closeAfter);
		
		if (closeAfter) {

			try {
				UserTransaction utx = HibernateHelper.getUserTransaction();

				if (utx.getStatus() == Status.STATUS_MARKED_ROLLBACK || utx.getStatus() == Status.STATUS_ROLLEDBACK) {
					return;
				}

				if (utx.getStatus() == Status.STATUS_ACTIVE) {
					utx.rollback();
				} else {

					throw new Exception(String.format(
							"Cannot rollback transaction with status %s",
							utx.getStatus()));
				}
			} catch (Exception e) {
//				LoggerHelper.log.error(
//						"Error while rolling back JTA transaction.", e);
			}
		}
	}

	public static Session getSession() {
		Session s = HibernateHelper.getSessionFactory().getCurrentSession();
		s.setFlushMode(org.hibernate.FlushMode.MANUAL);
		return s;
	}

	public static void closeSession() throws HibernateException {

		if (HibernateHelper.getSessionFactory() != null) {
			Session s = getSession();
			if (s != null) {
				// if (s.getTransaction().isActive()) {
				// s.clear();
				// }
				s.close();
			}
		}
	}

	public static void destroy() {
		/***
		 * Closes an hibernate {@link Session}, releasing its resources.
		 * 
		 * @throws HibernateException
		 *             if an hibernate error occurs
		 ***/
		try {
			if (!sf.isClosed()) {
				sf.close();
			}
		} catch (HibernateException he) {
//			LoggerHelper.log
//					.error("Error while closing hibernate session.", he);
		}
	}
}
