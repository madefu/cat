package com.dianping.cat.home.router.entity;

import com.dianping.cat.home.router.BaseEntity;
import com.dianping.cat.home.router.IVisitor;

public class DefaultServer extends BaseEntity<DefaultServer> {
   private String m_id;

   private double m_weight = 1d;

   private int m_port;

   private boolean m_enable;

   public DefaultServer() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDefaultServer(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof DefaultServer) {
         DefaultServer _o = (DefaultServer) obj;

         if (!equals(getId(), _o.getId())) {
            return false;
         }

         if (getWeight() != _o.getWeight()) {
            return false;
         }

         if (getPort() != _o.getPort()) {
            return false;
         }

         if (getEnable() != _o.getEnable()) {
            return false;
         }


         return true;
      }

      return false;
   }

   public boolean getEnable() {
      return m_enable;
   }

   public String getId() {
      return m_id;
   }

   public int getPort() {
      return m_port;
   }

   public double getWeight() {
      return m_weight;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_id == null ? 0 : m_id.hashCode());
      hash = hash * 31 + (int) (Double.doubleToLongBits(m_weight) ^ (Double.doubleToLongBits(m_weight) >>> 32));
      hash = hash * 31 + m_port;
      hash = hash * 31 + (m_enable ? 1 : 0);

      return hash;
   }

   public boolean isEnable() {
      return m_enable;
   }

   @Override
   public void mergeAttributes(DefaultServer other) {
      if (other.getId() != null) {
         m_id = other.getId();
      }

      m_weight = other.getWeight();

      m_port = other.getPort();

      m_enable = other.getEnable();
   }

   public DefaultServer setEnable(boolean enable) {
      m_enable = enable;
      return this;
   }

   public DefaultServer setId(String id) {
      m_id = id;
      return this;
   }

   public DefaultServer setPort(int port) {
      m_port = port;
      return this;
   }

   public DefaultServer setWeight(double weight) {
      m_weight = weight;
      return this;
   }

}
