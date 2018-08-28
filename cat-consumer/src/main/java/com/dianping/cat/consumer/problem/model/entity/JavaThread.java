package com.dianping.cat.consumer.problem.model.entity;

import static com.dianping.cat.consumer.problem.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_THREAD;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.problem.model.BaseEntity;
import com.dianping.cat.consumer.problem.model.IVisitor;

public class JavaThread extends BaseEntity<JavaThread> {
   private String m_groupName;

   private String m_name;

   private String m_id;

   private Map<Integer, Segment> m_segments = new LinkedHashMap<Integer, Segment>();

   public JavaThread() {
   }

   public JavaThread(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitThread(this);
   }

   public JavaThread addSegment(Segment segment) {
      m_segments.put(segment.getId(), segment);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof JavaThread) {
         JavaThread _o = (JavaThread) obj;

         if (!equals(getId(), _o.getId())) {
            return false;
         }

         return true;
      }

      return false;
   }

   public Segment findSegment(Integer id) {
      return m_segments.get(id);
   }

   public Segment findOrCreateSegment(Integer id) {
      Segment segment = m_segments.get(id);

      if (segment == null) {
         synchronized (m_segments) {
            segment = m_segments.get(id);

            if (segment == null) {
               segment = new Segment(id);
               m_segments.put(id, segment);
            }
         }
      }

      return segment;
   }

   public String getGroupName() {
      return m_groupName;
   }

   public String getId() {
      return m_id;
   }

   public String getName() {
      return m_name;
   }

   public Map<Integer, Segment> getSegments() {
      return m_segments;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_id == null ? 0 : m_id.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(JavaThread other) {
      assertAttributeEquals(other, ENTITY_THREAD, ATTR_ID, m_id, other.getId());

      if (other.getGroupName() != null) {
         m_groupName = other.getGroupName();
      }

      if (other.getName() != null) {
         m_name = other.getName();
      }
   }

   public Segment removeSegment(Integer id) {
      return m_segments.remove(id);
   }

   public JavaThread setGroupName(String groupName) {
      m_groupName = groupName;
      return this;
   }

   public JavaThread setId(String id) {
      m_id = id;
      return this;
   }

   public JavaThread setName(String name) {
      m_name = name;
      return this;
   }

}
