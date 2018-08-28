package com.dianping.cat.consumer.metric.model.entity;

import static com.dianping.cat.consumer.metric.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.metric.model.Constants.ENTITY_STATISTICS_ITEM;

import com.dianping.cat.consumer.metric.model.BaseEntity;
import com.dianping.cat.consumer.metric.model.IVisitor;

public class StatisticsItem extends BaseEntity<StatisticsItem> {
   private String m_id;

   private int m_count;

   public StatisticsItem() {
   }

   public StatisticsItem(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitStatisticsItem(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof StatisticsItem) {
         StatisticsItem _o = (StatisticsItem) obj;

         if (!equals(getId(), _o.getId())) {
            return false;
         }

         return true;
      }

      return false;
   }

   public int getCount() {
      return m_count;
   }

   public String getId() {
      return m_id;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_id == null ? 0 : m_id.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(StatisticsItem other) {
      assertAttributeEquals(other, ENTITY_STATISTICS_ITEM, ATTR_ID, m_id, other.getId());

      m_count = other.getCount();
   }

   public StatisticsItem setCount(int count) {
      m_count = count;
      return this;
   }

   public StatisticsItem setId(String id) {
      m_id = id;
      return this;
   }

}
