package org.sandbox

import org.hibernate.Session

/**
 * Created by aistomin on 11/09/16.
 *
 * Test that demonstrates the huge "IN" list problem.
 */
class JdbcTest extends GroovyTestCase {

    void testInClause() {
        MyUser.withSession { final Session session ->
            final query = session.createSQLQuery(
                "SELECT id FROM my_user WHERE id IN (:ids)"
            )
            query.setParameterList('ids', uniqueIds(32767)) // works
//            query.setParameterList('ids', uniqueIds(32768)) // fails
            return query.list()
        }
    }

    private Set<Integer> uniqueIds(final Integer count) {
        final Set<Integer> ids = []
        final random = new Random()
        while (ids.size() < count) {
            ids.add(random.nextInt(1000000))
        }
        return ids
    }
}
