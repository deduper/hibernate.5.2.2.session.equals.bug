package deduper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * These tests demonstrate that SessionImpl.equals(Object) incorrectly implements the expected 
 * behavior that the Java Language Specification prescribes for overriding Object.equals(Object).
 */
public class SessionEqualsBugTestCase {

	private SessionFactory sf;

    private Session aSession;

	@Before
	public void setup() {
		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
			// Add in any settings that are specific to your test. See resources/hibernate.properties for the defaults.
			.applySetting( "hibernate.show_sql", "true" )
			.applySetting( "hibernate.format_sql", "true" )
			.applySetting( "hibernate.hbm2ddl.auto", "update" )
			.applySetting( "hibernate.current_session_context_class", "org.hibernate.context.internal.ThreadLocalSessionContext" ); //

		Metadata metadata = new MetadataSources( srb.build() )
		// Add your entities here.
		//	.addAnnotatedClass( Foo.class )
			.buildMetadata();

		sf = metadata.buildSessionFactory();

        aSession = sf.getCurrentSession( );

	}

	// Add your tests, using standard JUnit.

    @Test
    public void testReferencesIdentityTrue( ) throws Exception {

        assertTrue( aSession == aSession );

    }

    @Test
    public void testReferencesIdentityNegatedFalse( ) throws Exception {

        assertFalse( aSession != aSession );

    }

    @Test
    public void testDeepEqualsNegatedTrue( ) throws Exception {

        assertTrue( !aSession.equals(aSession) );

    }

    @Test
    public void testDeepEqualsFalse( ) throws Exception {
     
        assertFalse( aSession.equals( aSession ) );

    }

    @Test
    public void testDeepEqualsNot( ) throws Exception {        

        assertNotEquals( aSession, aSession );

    }

    @Ignore("testDeepEquals() would fail without the '@Ignore'. However, this is the very test that demonstrates the bug the clearest.")
	@Test
	public void testDeepEquals() throws Exception {
        
        assertEquals( aSession, aSession );

	}

}
