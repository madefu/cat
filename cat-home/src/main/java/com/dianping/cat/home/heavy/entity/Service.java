package com.dianping.cat.home.heavy.entity;

import static com.dianping.cat.home.heavy.Constants.ATTR_KEY;
import static com.dianping.cat.home.heavy.Constants.ENTITY_SERVICE;

import com.dianping.cat.home.heavy.BaseEntity;
import com.dianping.cat.home.heavy.IVisitor;

public class Service extends BaseEntity<Service> {
   private String m_domain;

   private String m_name;

   private String m_logview;

   private long m_count;

   private String m_key;

   public Service() {
   }

   public Service(String key) {
      m_key = key;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitService(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Service) {
         Service _o = (Service) obj;

         if (!equals(getKey(), _o.getKey())) {
            return false;
         }

         return true;
      }

      return false;
   }

   public long getCount() {
      return m_count;
   }

   public String getDomain() {
      return m_domain;
   }

   public String getKey() {
      return m_key;
   }

   public String getLogview() {
      return m_logview;
   }

   public String getName() {
      return m_name;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_key == null ? 0 : m_key.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(Service other) {
      assertAttributeEquals(other, ENTITY_SERVICE, ATTR_KEY, m_key, other.getKey());

      if (other.getDomain() != null) {
         m_domain = other.getDomain();
      }

      if (other.getName() != null) {
         m_name = other.getName();
      }

      if (other.getLogview() != null) {
         m_logview = other.getLogview();
      }

      m_count = other.getCount();
   }

   public Service setCount(long count) {
      m_count = count;
      return this;
   }

   public Service setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public Service setKey(String key) {
      m_key = key;
      return this;
   }

   public Service setLogview(String logview) {
      m_logview = logview;
      return this;
   }

   public Service setName(String name) {
      m_name = name;
      return this;
   }

}
