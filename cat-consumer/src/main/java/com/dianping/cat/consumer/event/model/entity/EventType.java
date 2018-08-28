package com.dianping.cat.consumer.event.model.entity;

import static com.dianping.cat.consumer.event.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_TYPE;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.event.model.BaseEntity;
import com.dianping.cat.consumer.event.model.IVisitor;

public class EventType extends BaseEntity<EventType> {
   private String m_id;

   private long m_totalCount;

   private long m_failCount;

   private double m_failPercent;

   private String m_successMessageUrl;

   private String m_failMessageUrl;

   private Map<String, EventName> m_names = new LinkedHashMap<String, EventName>();

   private double m_tps;

   public EventType() {
   }

   public EventType(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitType(this);
   }

   public EventType addName(EventName name) {
      m_names.put(name.getId(), name);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof EventType) {
         EventType _o = (EventType) obj;

         if (!equals(getId(), _o.getId())) {
            return false;
         }

         return true;
      }

      return false;
   }

   public EventName findName(String id) {
      return m_names.get(id);
   }

   public EventName findOrCreateName(String id) {
      EventName name = m_names.get(id);

      if (name == null) {
         synchronized (m_names) {
            name = m_names.get(id);

            if (name == null) {
               name = new EventName(id);
               m_names.put(id, name);
            }
         }
      }

      return name;
   }

   public long getFailCount() {
      return m_failCount;
   }

   public String getFailMessageUrl() {
      return m_failMessageUrl;
   }

   public double getFailPercent() {
      return m_failPercent;
   }

   public String getId() {
      return m_id;
   }

   public Map<String, EventName> getNames() {
      return m_names;
   }

   public String getSuccessMessageUrl() {
      return m_successMessageUrl;
   }

   public long getTotalCount() {
      return m_totalCount;
   }

   public double getTps() {
      return m_tps;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_id == null ? 0 : m_id.hashCode());

      return hash;
   }

   public EventType incFailCount() {
      m_failCount++;
      return this;
   }

   public EventType incFailCount(long failCount) {
      m_failCount += failCount;
      return this;
   }

   public EventType incTotalCount() {
      m_totalCount++;
      return this;
   }

   public EventType incTotalCount(long totalCount) {
      m_totalCount += totalCount;
      return this;
   }

   @Override
   public void mergeAttributes(EventType other) {
      assertAttributeEquals(other, ENTITY_TYPE, ATTR_ID, m_id, other.getId());

      m_totalCount = other.getTotalCount();

      m_failCount = other.getFailCount();

      m_failPercent = other.getFailPercent();

      m_tps = other.getTps();
   }

   public EventName removeName(String id) {
      return m_names.remove(id);
   }

   public EventType setFailCount(long failCount) {
      m_failCount = failCount;
      return this;
   }

   public EventType setFailMessageUrl(String failMessageUrl) {
      m_failMessageUrl = failMessageUrl;
      return this;
   }

   public EventType setFailPercent(double failPercent) {
      m_failPercent = failPercent;
      return this;
   }

   public EventType setId(String id) {
      m_id = id;
      return this;
   }

   public EventType setSuccessMessageUrl(String successMessageUrl) {
      m_successMessageUrl = successMessageUrl;
      return this;
   }

   public EventType setTotalCount(long totalCount) {
      m_totalCount = totalCount;
      return this;
   }

   public EventType setTps(double tps) {
      m_tps = tps;
      return this;
   }

}
