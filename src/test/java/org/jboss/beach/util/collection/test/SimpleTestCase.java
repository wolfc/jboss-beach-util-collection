package org.jboss.beach.util.collection.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author <a href="cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class SimpleTestCase
{
   @Test
   public void testNull()
   {
      MockParent parent = new MockParent();

      try
      {
         parent.getChildren().add(null);
         fail("Expected NullPointerException");
      }
      catch(NullPointerException e)
      {
         // expected
      }

      assertEquals(0, parent.getChildren().size());
   }

   @Test
   public void testTwoParents()
   {
      MockParent parent1 = new MockParent();

      MockParent parent2 = new MockParent();

      MockChild child = new MockChild();

      boolean success = parent1.getChildren().add(child);
      assertTrue(success);

      try
      {
         parent2.getChildren().add(child);
         fail("Expected RuntimeException");
      }
      catch(RuntimeException e)
      {
         // expected
      }

      assertEquals(0, parent2.getChildren().size());
   }
}
