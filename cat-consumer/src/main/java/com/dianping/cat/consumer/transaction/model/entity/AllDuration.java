package com.dianping.cat.consumer.transaction.model.entity;

import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_ALL_DURATION;

import com.dianping.cat.consumer.transaction.model.BaseEntity;
import com.dianping.cat.consumer.transaction.model.IVisitor;

public class AllDuration extends BaseEntity<AllDuration> {
   private int m_value;

   private int m_count;

   public AllDuration() {
   }

   public AllDuration(int value) {
      m_value = value;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitAllDuration(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof AllDuration) {
         AllDuration _o = (AllDuration) obj;

         if (getValue() != _o.getValue()) {
            return false;
         }

         return true;
      }

      return false;
   }

   public int getCount() {
      return m_count;
   }

   public int getValue() {
      return m_value;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + m_value;

      return hash;
   }

   public AllDuration incCount() {
      m_count++;
      return this;
   }

   public AllDuration incCount(int count) {
      m_count += count;
      return this;
   }

   @Override
   public void mergeAttributes(AllDuration other) {
      assertAttributeEquals(other, ENTITY_ALL_DURATION, ATTR_VALUE, m_value, other.getValue());

      m_count = other.getCount();
   }

   public AllDuration setCount(int count) {
      m_count = count;
      return this;
   }

   public AllDuration setValue(int value) {
      m_value = value;
      return this;
   }

}
