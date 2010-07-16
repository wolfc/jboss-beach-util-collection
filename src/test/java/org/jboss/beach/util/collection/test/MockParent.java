/*
 * JBoss, Home of Professional Open Source
 * Copyright (c) 2010, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.beach.util.collection.test;

import org.jboss.beach.util.collection.ChildrenList;
import org.jboss.beach.util.collection.Parent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class MockParent implements Parent<MockParent,MockChild>
{
   private List<MockChild> children;

   private static class ReadOnlyList<MockChild> extends ArrayList<MockChild>
   {
      @Override
      public boolean remove(Object o)
      {
         throw new UnsupportedOperationException("remove(" + o + ")");
      }
   }

   public MockParent()
   {
      this(false);
   }

   public MockParent(boolean denyRemovals)
   {
      if(denyRemovals)
      {
         children = new ChildrenList<MockChild, MockParent>(this, new ReadOnlyList());
      }
      else
      {
         children = new ChildrenList<MockChild, MockParent>(this);
      }
   }

   @Override
   public Collection<MockChild> getChildren()
   {
      return children;
   }
}
