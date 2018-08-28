package com.dianping.cat.home.jar.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.home.jar.BaseEntity;
import com.dianping.cat.home.jar.IVisitor;

public class JarReport extends BaseEntity<JarReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Map<String, Domain> m_domains = new LinkedHashMap<String, Domain>();

   public JarReport() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitJarReport(this);
   }

   public JarReport addDomain(Domain domain) {
      m_domains.put(domain.getId(), domain);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof JarReport) {
         JarReport _o = (JarReport) obj;

         if (!equals(getDomain(), _o.getDomain())) {
            return false;
         }

         if (!equals(getStartTime(), _o.getStartTime())) {
            return false;
         }

         if (!equals(getEndTime(), _o.getEndTime())) {
            return false;
         }

         if (!equals(getDomains(), _o.getDomains())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public Domain findDomain(String id) {
      return m_domains.get(id);
   }

   public Domain findOrCreateDomain(String id) {
      Domain domain = m_domains.get(id);

      if (domain == null) {
         synchronized (m_domains) {
            domain = m_domains.get(id);

            if (domain == null) {
               domain = new Domain(id);
               m_domains.put(id, domain);
            }
         }
      }

      return domain;
   }

   public String getDomain() {
      return m_domain;
   }

   public Map<String, Domain> getDomains() {
      return m_domains;
   }

   public java.util.Date getEndTime() {
      return m_endTime;
   }

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_domain == null ? 0 : m_domain.hashCode());
      hash = hash * 31 + (m_startTime == null ? 0 : m_startTime.hashCode());
      hash = hash * 31 + (m_endTime == null ? 0 : m_endTime.hashCode());
      hash = hash * 31 + (m_domains == null ? 0 : m_domains.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(JarReport other) {
      if (other.getDomain() != null) {
         m_domain = other.getDomain();
      }

      if (other.getStartTime() != null) {
         m_startTime = other.getStartTime();
      }

      if (other.getEndTime() != null) {
         m_endTime = other.getEndTime();
      }
   }

   public Domain removeDomain(String id) {
      return m_domains.remove(id);
   }

   public JarReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public JarReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public JarReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
