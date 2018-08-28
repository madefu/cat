package com.dianping.cat.home.jar.entity;

import com.dianping.cat.home.jar.BaseEntity;
import com.dianping.cat.home.jar.IVisitor;

public class Jar extends BaseEntity<Jar> {
   private String m_id;

   private String m_version;

   public Jar() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitJar(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Jar) {
         Jar _o = (Jar) obj;

         if (!equals(getId(), _o.getId())) {
            return false;
         }

         if (!equals(getVersion(), _o.getVersion())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public String getId() {
      return m_id;
   }

   public String getVersion() {
      return m_version;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_id == null ? 0 : m_id.hashCode());
      hash = hash * 31 + (m_version == null ? 0 : m_version.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(Jar other) {
      if (other.getId() != null) {
         m_id = other.getId();
      }

      if (other.getVersion() != null) {
         m_version = other.getVersion();
      }
   }

   public Jar setId(String id) {
      m_id = id;
      return this;
   }

   public Jar setVersion(String version) {
      m_version = version;
      return this;
   }

}
