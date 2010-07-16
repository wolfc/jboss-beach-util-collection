package org.jboss.beach.util.collection.test;

import org.jboss.beach.util.collection.Child;
import org.jboss.beach.util.collection.Parent;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

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
      catch (NullPointerException e)
      {
         // expected
      }

      assertEquals(0, parent.getChildren().size());
   }

   /**
    * Ensures that adding a {@link Child} to a {@link Parent}
    * results in {@link Child#getParent()} retaining the correct reference
    */
   @Test
   public void hasParent()
   {
      // Create parent and child
      final MockParent parent = new MockParent();
      final MockChild child = new MockChild();

      // Add
      parent.getChildren().add(child);

      // Assert
      Assert.assertSame("Setting child to a parent should result in the child knowing it has a parent", child
            .getParent(), parent);
   }

   @Test
   public void testReadOnly()
   {
      MockParent parent1 = new MockParent(true);

      MockParent parent2 = new MockParent(true);

      MockChild child = mock(MockChild.class);

      doThrow(new IllegalArgumentException("don't like that parent")).when(child).setParent(eq(parent1));

      try
      {
         parent1.getChildren().add(child);
         fail("Expected IllegalArgumentException");
      }
      catch (IllegalArgumentException e)
      {
         // expected
      }
      catch (UnsupportedOperationException e)
      {
         fail("add is supposed to be supported (" + e + ")");
      }

      parent2.getChildren().add(child);

      assertEquals(1, parent2.getChildren().size());
      assertEquals(0, parent1.getChildren().size());
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
      catch (RuntimeException e)
      {
         // expected
      }

      assertEquals(0, parent2.getChildren().size());
   }

   @Test
   public void testSpoiledChild()
   {
      MockParent parent1 = new MockParent();

      MockParent parent2 = new MockParent();

      MockChild child = mock(MockChild.class);

      doThrow(new IllegalArgumentException("don't like that parent")).when(child).setParent(eq(parent1));

      try
      {
         parent1.getChildren().add(child);
         fail("Expected IllegalArgumentException");
      }
      catch (IllegalArgumentException e)
      {
         // expected
      }

      parent2.getChildren().add(child);
      
      assertEquals(1, parent2.getChildren().size());
      assertEquals(0, parent1.getChildren().size());
   }
}
