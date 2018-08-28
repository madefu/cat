package com.dianping.cat.configuration.app.comparison.entity;

import com.dianping.cat.configuration.app.comparison.BaseEntity;
import com.dianping.cat.configuration.app.comparison.IVisitor;

public class Item extends BaseEntity<Item> {
   private String m_id;

   private String m_command;

   public Item() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitItem(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Item) {
         Item _o = (Item) obj;

         if (!equals(getId(), _o.getId())) {
            return false;
         }

         if (!equals(getCommand(), _o.getCommand())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public String getCommand() {
      return m_command;
   }

   public String getId() {
      return m_id;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_id == null ? 0 : m_id.hashCode());
      hash = hash * 31 + (m_command == null ? 0 : m_command.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(Item other) {
      if (other.getId() != null) {
         m_id = other.getId();
      }

      if (other.getCommand() != null) {
         m_command = other.getCommand();
      }
   }

   public Item setCommand(String command) {
      m_command = command;
      return this;
   }

   public Item setId(String id) {
      m_id = id;
      return this;
   }

}
