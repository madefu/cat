package com.dianping.cat.consumer.problem.model.entity;

import static com.dianping.cat.consumer.problem.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_DURATION;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.consumer.problem.model.BaseEntity;
import com.dianping.cat.consumer.problem.model.IVisitor;

public class Duration extends BaseEntity<Duration> {
   private int m_value;

   private int m_count;

   private List<String> m_messages = new ArrayList<String>();

   public Duration() {
   }

   public Duration(int value) {
      m_value = value;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDuration(this);
   }

   public Duration addMessage(String message) {
      m_messages.add(message);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Duration) {
         Duration _o = (Duration) obj;

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

   public List<String> getMessages() {
      return m_messages;
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

   public Duration incCount() {
      m_count++;
      return this;
   }

   public Duration incCount(int count) {
      m_count += count;
      return this;
   }

   @Override
   public void mergeAttributes(Duration other) {
      assertAttributeEquals(other, ENTITY_DURATION, ATTR_VALUE, m_value, other.getValue());

      m_count = other.getCount();
   }

   public Duration setCount(int count) {
      m_count = count;
      return this;
   }

   public Duration setValue(int value) {
      m_value = value;
      return this;
   }

}
