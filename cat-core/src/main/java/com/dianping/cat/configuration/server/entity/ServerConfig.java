package com.dianping.cat.configuration.server.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.configuration.server.BaseEntity;
import com.dianping.cat.configuration.server.IVisitor;

public class ServerConfig extends BaseEntity<ServerConfig> {
   private Boolean m_localMode = true;

   private Boolean m_jobMachine = false;

   private StorageConfig m_storage;

   private ConsumerConfig m_consumer;

   private ConsoleConfig m_console;

   private Boolean m_alertMachine = false;

   private Boolean m_hdfsMachine = true;

   private Boolean m_sendMachine = true;

   private Ldap m_ldap;

   private Map<String, Property> m_properties = new LinkedHashMap<String, Property>();

   public ServerConfig() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitConfig(this);
   }

   public ServerConfig addProperty(Property property) {
      m_properties.put(property.getName(), property);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof ServerConfig) {
         ServerConfig _o = (ServerConfig) obj;

         if (!equals(getLocalMode(), _o.getLocalMode())) {
            return false;
         }

         if (!equals(getJobMachine(), _o.getJobMachine())) {
            return false;
         }

         if (!equals(getStorage(), _o.getStorage())) {
            return false;
         }

         if (!equals(getConsumer(), _o.getConsumer())) {
            return false;
         }

         if (!equals(getConsole(), _o.getConsole())) {
            return false;
         }

         if (!equals(getAlertMachine(), _o.getAlertMachine())) {
            return false;
         }

         if (!equals(getHdfsMachine(), _o.getHdfsMachine())) {
            return false;
         }

         if (!equals(getSendMachine(), _o.getSendMachine())) {
            return false;
         }

         if (!equals(getLdap(), _o.getLdap())) {
            return false;
         }

         if (!equals(getProperties(), _o.getProperties())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public Property findProperty(String name) {
      return m_properties.get(name);
   }

   public Boolean getAlertMachine() {
      return m_alertMachine;
   }

   public ConsoleConfig getConsole() {
      return m_console;
   }

   public ConsumerConfig getConsumer() {
      return m_consumer;
   }

   public Boolean getHdfsMachine() {
      return m_hdfsMachine;
   }

   public Boolean getJobMachine() {
      return m_jobMachine;
   }

   public Ldap getLdap() {
      return m_ldap;
   }

   public Boolean getLocalMode() {
      return m_localMode;
   }

   public Map<String, Property> getProperties() {
      return m_properties;
   }

   public Boolean getSendMachine() {
      return m_sendMachine;
   }

   public StorageConfig getStorage() {
      return m_storage;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_localMode == null ? 0 : m_localMode.hashCode());
      hash = hash * 31 + (m_jobMachine == null ? 0 : m_jobMachine.hashCode());
      hash = hash * 31 + (m_storage == null ? 0 : m_storage.hashCode());
      hash = hash * 31 + (m_consumer == null ? 0 : m_consumer.hashCode());
      hash = hash * 31 + (m_console == null ? 0 : m_console.hashCode());
      hash = hash * 31 + (m_alertMachine == null ? 0 : m_alertMachine.hashCode());
      hash = hash * 31 + (m_hdfsMachine == null ? 0 : m_hdfsMachine.hashCode());
      hash = hash * 31 + (m_sendMachine == null ? 0 : m_sendMachine.hashCode());
      hash = hash * 31 + (m_ldap == null ? 0 : m_ldap.hashCode());
      hash = hash * 31 + (m_properties == null ? 0 : m_properties.hashCode());

      return hash;
   }

   public boolean isAlertMachine() {
      return m_alertMachine != null && m_alertMachine.booleanValue();
   }

   public boolean isHdfsMachine() {
      return m_hdfsMachine != null && m_hdfsMachine.booleanValue();
   }

   public boolean isJobMachine() {
      return m_jobMachine != null && m_jobMachine.booleanValue();
   }

   public boolean isLocalMode() {
      return m_localMode != null && m_localMode.booleanValue();
   }

   public boolean isSendMachine() {
      return m_sendMachine != null && m_sendMachine.booleanValue();
   }

   @Override
   public void mergeAttributes(ServerConfig other) {
      if (other.getLocalMode() != null) {
         m_localMode = other.getLocalMode();
      }

      if (other.getJobMachine() != null) {
         m_jobMachine = other.getJobMachine();
      }

      if (other.getAlertMachine() != null) {
         m_alertMachine = other.getAlertMachine();
      }

      if (other.getHdfsMachine() != null) {
         m_hdfsMachine = other.getHdfsMachine();
      }

      if (other.getSendMachine() != null) {
         m_sendMachine = other.getSendMachine();
      }
   }

   public Property removeProperty(String name) {
      return m_properties.remove(name);
   }

   public ServerConfig setAlertMachine(Boolean alertMachine) {
      m_alertMachine = alertMachine;
      return this;
   }

   public ServerConfig setConsole(ConsoleConfig console) {
      m_console = console;
      return this;
   }

   public ServerConfig setConsumer(ConsumerConfig consumer) {
      m_consumer = consumer;
      return this;
   }

   public ServerConfig setHdfsMachine(Boolean hdfsMachine) {
      m_hdfsMachine = hdfsMachine;
      return this;
   }

   public ServerConfig setJobMachine(Boolean jobMachine) {
      m_jobMachine = jobMachine;
      return this;
   }

   public ServerConfig setLdap(Ldap ldap) {
      m_ldap = ldap;
      return this;
   }

   public ServerConfig setLocalMode(Boolean localMode) {
      m_localMode = localMode;
      return this;
   }

   public ServerConfig setSendMachine(Boolean sendMachine) {
      m_sendMachine = sendMachine;
      return this;
   }

   public ServerConfig setStorage(StorageConfig storage) {
      m_storage = storage;
      return this;
   }

}
