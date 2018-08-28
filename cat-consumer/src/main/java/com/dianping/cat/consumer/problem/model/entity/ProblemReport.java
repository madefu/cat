package com.dianping.cat.consumer.problem.model.entity;

import static com.dianping.cat.consumer.problem.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_PROBLEM_REPORT;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.dianping.cat.consumer.problem.model.BaseEntity;
import com.dianping.cat.consumer.problem.model.IVisitor;

public class ProblemReport extends BaseEntity<ProblemReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Map<String, Machine> m_machines = new LinkedHashMap<String, Machine>();

   private Set<String> m_domainNames = new LinkedHashSet<String>();

   private Set<String> m_ips = new LinkedHashSet<String>();

   public ProblemReport() {
   }

   public ProblemReport(String domain) {
      m_domain = domain;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitProblemReport(this);
   }

   public ProblemReport addDomain(String domain) {
      m_domainNames.add(domain);
      return this;
   }

   public ProblemReport addIp(String ip) {
      m_ips.add(ip);
      return this;
   }

   public ProblemReport addMachine(Machine machine) {
      m_machines.put(machine.getIp(), machine);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof ProblemReport) {
         ProblemReport _o = (ProblemReport) obj;

         if (!equals(getDomain(), _o.getDomain())) {
            return false;
         }

         return true;
      }

      return false;
   }

   public Machine findMachine(String ip) {
      return m_machines.get(ip);
   }

   public Machine findOrCreateMachine(String ip) {
      Machine machine = m_machines.get(ip);

      if (machine == null) {
         synchronized (m_machines) {
            machine = m_machines.get(ip);

            if (machine == null) {
               machine = new Machine(ip);
               m_machines.put(ip, machine);
            }
         }
      }

      return machine;
   }

   public String getDomain() {
      return m_domain;
   }

   public Set<String> getDomainNames() {
      return m_domainNames;
   }

   public java.util.Date getEndTime() {
      return m_endTime;
   }

   public Set<String> getIps() {
      return m_ips;
   }

   public Map<String, Machine> getMachines() {
      return m_machines;
   }

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_domain == null ? 0 : m_domain.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(ProblemReport other) {
      assertAttributeEquals(other, ENTITY_PROBLEM_REPORT, ATTR_DOMAIN, m_domain, other.getDomain());

      if (other.getStartTime() != null) {
         m_startTime = other.getStartTime();
      }

      if (other.getEndTime() != null) {
         m_endTime = other.getEndTime();
      }
   }

   public Machine removeMachine(String ip) {
      return m_machines.remove(ip);
   }

   public ProblemReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public ProblemReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public ProblemReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
