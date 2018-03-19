package net.weissenburger.producebrowser.imageviewer.loader;

import net.weissenburger.producebrowser.imageviewer.model.IProduce;

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

    @Test
    public void testGenerateNextPageQuery() {
        query = new ProduceQuery("tomatoes");
        IProduceQuery nextQuery = query.nextPage();

        assertTrue(nextQuery.isNextPageQuery());
        assertTrue(nextQuery.getPage() == 2);


    }


}
