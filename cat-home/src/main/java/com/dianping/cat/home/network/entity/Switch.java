package com.dianping.cat.home.network.entity;

import com.dianping.cat.home.network.BaseEntity;
import com.dianping.cat.home.network.IVisitor;

public class Switch extends BaseEntity<Switch> {
   private String m_name;

   private Integer m_x;

   private Integer m_y;

   private Integer m_state;

   public Switch() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitSwitch(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Switch) {
         Switch _o = (Switch) obj;

         if (!equals(getName(), _o.getName())) {
            return false;
         }

         if (!equals(getX(), _o.getX())) {
            return false;
         }

         if (!equals(getY(), _o.getY())) {
            return false;
         }

         if (!equals(getState(), _o.getState())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public String getName() {
      return m_name;
   }

   public Integer getState() {
      return m_state;
   }

   public Integer getX() {
      return m_x;
   }

   public Integer getY() {
      return m_y;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_name == null ? 0 : m_name.hashCode());
      hash = hash * 31 + (m_x == null ? 0 : m_x.hashCode());
      hash = hash * 31 + (m_y == null ? 0 : m_y.hashCode());
      hash = hash * 31 + (m_state == null ? 0 : m_state.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(Switch other) {
      if (other.getName() != null) {
         m_name = other.getName();
      }

      if (other.getX() != null) {
         m_x = other.getX();
      }

      if (other.getY() != null) {
         m_y = other.getY();
      }

      if (other.getState() != null) {
         m_state = other.getState();
      }
   }

   public Switch setName(String name) {
      m_name = name;
      return this;
   }

   public Switch setState(Integer state) {
      m_state = state;
      return this;
   }

   public Switch setX(Integer x) {
      m_x = x;
      return this;
   }

   public Switch setY(Integer y) {
      m_y = y;
      return this;
   }

}
