package com.dianping.cat.home.alert.thirdparty.entity;

import com.dianping.cat.home.alert.thirdparty.BaseEntity;
import com.dianping.cat.home.alert.thirdparty.IVisitor;

public class Par extends BaseEntity<Par> {
   private String m_id;

   public Par() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitPar(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Par) {
         Par _o = (Par) obj;

         if (!equals(getId(), _o.getId())) {
            return false;
         }


         return true;
      }

      return false;
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
   public void mergeAttributes(Par other) {
      if (other.getId() != null) {
         m_id = other.getId();
      }
   }

   public Par setId(String id) {
      m_id = id;
      return this;
   }

}
