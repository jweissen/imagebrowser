package net.weissenburger.producebrowser.imageviewer.loader;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Jon Weissenburger on 3/8/18.
 */

public class ProduceQueryTest {

    ProduceQuery query;

    @Before
    public void setup() {

    }

    @Test
    public void testConstructorQuery() {
        String initialQuery = "tomatoes";
        query = new ProduceQuery(initialQuery);

        assertTrue(query.getQuery().equals(initialQuery));
    }

    @Test
    public void testOverrideQuery() {
        query = new ProduceQuery("");

        String newQuery = "more tomatoes";
        query.setQuery(newQuery);

        assertTrue(query.getQuery().equals(newQuery));
    }



}
