package com.dianping.cat.status.model.entity;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class RuntimeInfo extends BaseEntity<RuntimeInfo> {
   private long m_startTime;

   private long m_upTime;

   private String m_javaVersion;

   private String m_userName;

   private String m_userDir;

   private String m_javaClasspath;

   public RuntimeInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitRuntime(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof RuntimeInfo) {
         RuntimeInfo _o = (RuntimeInfo) obj;

         if (getStartTime() != _o.getStartTime()) {
            return false;
         }

         if (getUpTime() != _o.getUpTime()) {
            return false;
         }

         if (!equals(getJavaVersion(), _o.getJavaVersion())) {
            return false;
         }

         if (!equals(getUserName(), _o.getUserName())) {
            return false;
         }

         if (!equals(getUserDir(), _o.getUserDir())) {
            return false;
         }

         if (!equals(getJavaClasspath(), _o.getJavaClasspath())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public String getJavaClasspath() {
      return m_javaClasspath;
   }

   public String getJavaVersion() {
      return m_javaVersion;
   }

   public long getStartTime() {
      return m_startTime;
   }

   public long getUpTime() {
      return m_upTime;
   }

   public String getUserDir() {
      return m_userDir;
   }

   public String getUserName() {
      return m_userName;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (int) (m_startTime ^ (m_startTime >>> 32));
      hash = hash * 31 + (int) (m_upTime ^ (m_upTime >>> 32));
      hash = hash * 31 + (m_javaVersion == null ? 0 : m_javaVersion.hashCode());
      hash = hash * 31 + (m_userName == null ? 0 : m_userName.hashCode());
      hash = hash * 31 + (m_userDir == null ? 0 : m_userDir.hashCode());
      hash = hash * 31 + (m_javaClasspath == null ? 0 : m_javaClasspath.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(RuntimeInfo other) {
      m_startTime = other.getStartTime();

      m_upTime = other.getUpTime();

      if (other.getJavaVersion() != null) {
         m_javaVersion = other.getJavaVersion();
      }

      if (other.getUserName() != null) {
         m_userName = other.getUserName();
      }
   }

   public RuntimeInfo setJavaClasspath(String javaClasspath) {
      m_javaClasspath = javaClasspath;
      return this;
   }

   public RuntimeInfo setJavaVersion(String javaVersion) {
      m_javaVersion = javaVersion;
      return this;
   }

   public RuntimeInfo setStartTime(long startTime) {
      m_startTime = startTime;
      return this;
   }

   public RuntimeInfo setUpTime(long upTime) {
      m_upTime = upTime;
      return this;
   }

   public RuntimeInfo setUserDir(String userDir) {
      m_userDir = userDir;
      return this;
   }

   public RuntimeInfo setUserName(String userName) {
      m_userName = userName;
      return this;
   }

}
