package com.dianping.cat.configuration.server.entity;

import com.dianping.cat.configuration.server.BaseEntity;
import com.dianping.cat.configuration.server.IVisitor;

public class ConsoleConfig extends BaseEntity<ConsoleConfig> {
   private String m_defaultDomain = "Cat";

   private Boolean m_showCatDomain = true;

   private String m_remoteServers;

   public ConsoleConfig() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitConsole(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof ConsoleConfig) {
         ConsoleConfig _o = (ConsoleConfig) obj;

         if (!equals(getDefaultDomain(), _o.getDefaultDomain())) {
            return false;
         }

         if (!equals(getShowCatDomain(), _o.getShowCatDomain())) {
            return false;
         }

         if (!equals(getRemoteServers(), _o.getRemoteServers())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public String getDefaultDomain() {
      return m_defaultDomain;
   }

   public String getRemoteServers() {
      return m_remoteServers;
   }

   public Boolean getShowCatDomain() {
      return m_showCatDomain;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_defaultDomain == null ? 0 : m_defaultDomain.hashCode());
      hash = hash * 31 + (m_showCatDomain == null ? 0 : m_showCatDomain.hashCode());
      hash = hash * 31 + (m_remoteServers == null ? 0 : m_remoteServers.hashCode());

      return hash;
   }

   public boolean isShowCatDomain() {
      return m_showCatDomain != null && m_showCatDomain.booleanValue();
   }

   @Override
   public void mergeAttributes(ConsoleConfig other) {
      if (other.getDefaultDomain() != null) {
         m_defaultDomain = other.getDefaultDomain();
      }

      if (other.getShowCatDomain() != null) {
         m_showCatDomain = other.getShowCatDomain();
      }
   }

   public ConsoleConfig setDefaultDomain(String defaultDomain) {
      m_defaultDomain = defaultDomain;
      return this;
   }

   public ConsoleConfig setRemoteServers(String remoteServers) {
      m_remoteServers = remoteServers;
      return this;
   }

   public ConsoleConfig setShowCatDomain(Boolean showCatDomain) {
      m_showCatDomain = showCatDomain;
      return this;
   }

}
