package com.dianping.cat.home.heartbeat.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.home.heartbeat.BaseEntity;
import com.dianping.cat.home.heartbeat.IVisitor;

public class HeartbeatDisplayPolicy extends BaseEntity<HeartbeatDisplayPolicy> {
   private Map<String, Group> m_groups = new LinkedHashMap<String, Group>();

   public HeartbeatDisplayPolicy() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitHeartbeatDisplayPolicy(this);
   }

   public HeartbeatDisplayPolicy addGroup(Group group) {
      m_groups.put(group.getId(), group);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof HeartbeatDisplayPolicy) {
         HeartbeatDisplayPolicy _o = (HeartbeatDisplayPolicy) obj;

         if (!equals(getGroups(), _o.getGroups())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public Group findGroup(String id) {
      return m_groups.get(id);
   }

   public Map<String, Group> getGroups() {
      return m_groups;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_groups == null ? 0 : m_groups.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(HeartbeatDisplayPolicy other) {
   }

   public Group removeGroup(String id) {
      return m_groups.remove(id);
   }

}
