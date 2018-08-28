package com.dianping.cat.configuration.app.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.configuration.app.BaseEntity;
import com.dianping.cat.configuration.app.IVisitor;

public class AppConfig extends BaseEntity<AppConfig> {
   private Map<String, ConfigItem> m_configItems = new LinkedHashMap<String, ConfigItem>();

   private Map<Integer, Code> m_codes = new LinkedHashMap<Integer, Code>();

   private Map<Integer, Command> m_commands = new LinkedHashMap<Integer, Command>();

   public AppConfig() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitAppConfig(this);
   }

   public AppConfig addCode(Code code) {
      m_codes.put(code.getId(), code);
      return this;
   }

   public AppConfig addCommand(Command command) {
      m_commands.put(command.getId(), command);
      return this;
   }

   public AppConfig addConfigItem(ConfigItem configItem) {
      m_configItems.put(configItem.getId(), configItem);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof AppConfig) {
         AppConfig _o = (AppConfig) obj;

         if (!equals(getConfigItems(), _o.getConfigItems())) {
            return false;
         }

         if (!equals(getCodes(), _o.getCodes())) {
            return false;
         }

         if (!equals(getCommands(), _o.getCommands())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public Code findCode(Integer id) {
      return m_codes.get(id);
   }

   public Command findCommand(Integer id) {
      return m_commands.get(id);
   }

   public ConfigItem findConfigItem(String id) {
      return m_configItems.get(id);
   }

   public Code findOrCreateCode(Integer id) {
      Code code = m_codes.get(id);

      if (code == null) {
         synchronized (m_codes) {
            code = m_codes.get(id);

            if (code == null) {
               code = new Code(id);
               m_codes.put(id, code);
            }
         }
      }

      return code;
   }

   public Command findOrCreateCommand(Integer id) {
      Command command = m_commands.get(id);

      if (command == null) {
         synchronized (m_commands) {
            command = m_commands.get(id);

            if (command == null) {
               command = new Command(id);
               m_commands.put(id, command);
            }
         }
      }

      return command;
   }

   public ConfigItem findOrCreateConfigItem(String id) {
      ConfigItem configItem = m_configItems.get(id);

      if (configItem == null) {
         synchronized (m_configItems) {
            configItem = m_configItems.get(id);

            if (configItem == null) {
               configItem = new ConfigItem(id);
               m_configItems.put(id, configItem);
            }
         }
      }

      return configItem;
   }

   public Map<Integer, Code> getCodes() {
      return m_codes;
   }

   public Map<Integer, Command> getCommands() {
      return m_commands;
   }

   public Map<String, ConfigItem> getConfigItems() {
      return m_configItems;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_configItems == null ? 0 : m_configItems.hashCode());
      hash = hash * 31 + (m_codes == null ? 0 : m_codes.hashCode());
      hash = hash * 31 + (m_commands == null ? 0 : m_commands.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(AppConfig other) {
   }

   public Code removeCode(Integer id) {
      return m_codes.remove(id);
   }

   public Command removeCommand(Integer id) {
      return m_commands.remove(id);
   }

   public ConfigItem removeConfigItem(String id) {
      return m_configItems.remove(id);
   }

}
