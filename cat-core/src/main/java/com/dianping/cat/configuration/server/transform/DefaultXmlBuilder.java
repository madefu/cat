package com.dianping.cat.configuration.server.transform;

import static com.dianping.cat.configuration.server.Constants.ATTR_ALERT_MACHINE;
import static com.dianping.cat.configuration.server.Constants.ATTR_BASE_DIR;
import static com.dianping.cat.configuration.server.Constants.ATTR_DEFAULT_DOMAIN;
import static com.dianping.cat.configuration.server.Constants.ATTR_DEFAULT_SERVICE_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_DEFAULT_SQL_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_DEFAULT_URL_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_HDFS_DISABLED;
import static com.dianping.cat.configuration.server.Constants.ATTR_HDFS_MACHINE;
import static com.dianping.cat.configuration.server.Constants.ATTR_ID;
import static com.dianping.cat.configuration.server.Constants.ATTR_JOB_MACHINE;
import static com.dianping.cat.configuration.server.Constants.ATTR_LDAPURL;
import static com.dianping.cat.configuration.server.Constants.ATTR_LOCAL_BASE_DIR;
import static com.dianping.cat.configuration.server.Constants.ATTR_LOCAL_LOGIVEW_STORAGE_TIME;
import static com.dianping.cat.configuration.server.Constants.ATTR_LOCAL_MODE;
import static com.dianping.cat.configuration.server.Constants.ATTR_LOCAL_REPORT_STORAGE_TIME;
import static com.dianping.cat.configuration.server.Constants.ATTR_MAX_HDFS_STORAGE_TIME;
import static com.dianping.cat.configuration.server.Constants.ATTR_MAX_SIZE;
import static com.dianping.cat.configuration.server.Constants.ATTR_NAME;
import static com.dianping.cat.configuration.server.Constants.ATTR_SEND_MACHINE;
import static com.dianping.cat.configuration.server.Constants.ATTR_SERVER_URI;
import static com.dianping.cat.configuration.server.Constants.ATTR_SERVICE_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_SHOW_CAT_DOMAIN;
import static com.dianping.cat.configuration.server.Constants.ATTR_SQL_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_UPLOAD_THREAD;
import static com.dianping.cat.configuration.server.Constants.ATTR_URL_THRESHOLD;
import static com.dianping.cat.configuration.server.Constants.ATTR_VALUE;
import static com.dianping.cat.configuration.server.Constants.ELEMENT_REMOTE_SERVERS;
import static com.dianping.cat.configuration.server.Constants.ENTITY_CONFIG;
import static com.dianping.cat.configuration.server.Constants.ENTITY_CONSOLE;
import static com.dianping.cat.configuration.server.Constants.ENTITY_CONSUMER;
import static com.dianping.cat.configuration.server.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.configuration.server.Constants.ENTITY_HDFS;
import static com.dianping.cat.configuration.server.Constants.ENTITY_LDAP;
import static com.dianping.cat.configuration.server.Constants.ENTITY_LONG_CONFIG;
import static com.dianping.cat.configuration.server.Constants.ENTITY_PROPERTIES;
import static com.dianping.cat.configuration.server.Constants.ENTITY_PROPERTY;
import static com.dianping.cat.configuration.server.Constants.ENTITY_STORAGE;

import java.lang.reflect.Array;
import java.util.Collection;

import com.dianping.cat.configuration.server.IEntity;
import com.dianping.cat.configuration.server.IVisitor;
import com.dianping.cat.configuration.server.entity.ConsoleConfig;
import com.dianping.cat.configuration.server.entity.ConsumerConfig;
import com.dianping.cat.configuration.server.entity.Domain;
import com.dianping.cat.configuration.server.entity.HdfsConfig;
import com.dianping.cat.configuration.server.entity.Ldap;
import com.dianping.cat.configuration.server.entity.LongConfig;
import com.dianping.cat.configuration.server.entity.Property;
import com.dianping.cat.configuration.server.entity.ServerConfig;
import com.dianping.cat.configuration.server.entity.StorageConfig;

public class DefaultXmlBuilder implements IVisitor {

   private IVisitor m_visitor = this;

   private int m_level;

   private StringBuilder m_sb;

   private boolean m_compact;

   public DefaultXmlBuilder() {
      this(false);
   }

   public DefaultXmlBuilder(boolean compact) {
      this(compact, new StringBuilder(4096));
   }

   public DefaultXmlBuilder(boolean compact, StringBuilder sb) {
      m_compact = compact;
      m_sb = sb;
      m_sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
   }

   public String buildXml(IEntity<?> entity) {
      entity.accept(m_visitor);
      return m_sb.toString();
   }

   protected void endTag(String name) {
      m_level--;

      indent();
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected String escape(Object value) {
      return escape(value, false);
   }
   
   protected String escape(Object value, boolean text) {
      if (value == null) {
         return null;
      }

      String str = toString(value);
      int len = str.length();
      StringBuilder sb = new StringBuilder(len + 16);

      for (int i = 0; i < len; i++) {
         final char ch = str.charAt(i);

         switch (ch) {
         case '<':
            sb.append("&lt;");
            break;
         case '>':
            sb.append("&gt;");
            break;
         case '&':
            sb.append("&amp;");
            break;
         case '"':
            if (!text) {
               sb.append("&quot;");
               break;
            }
         default:
            sb.append(ch);
            break;
         }
      }

      return sb.toString();
   }
   
   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void startTag(String name) {
      startTag(name, false, null);
   }
   
   protected void startTag(String name, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, closed, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, false, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, Object text, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      if (dynamicAttributes != null) {
         for (java.util.Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            m_sb.append(' ').append(e.getKey()).append("=\"").append(escape(e.getValue())).append('"');
         }
      }

      if (text != null && closed) {
         m_sb.append('>');
         m_sb.append(escape(text, true));
         m_sb.append("</").append(name).append(">\r\n");
      } else {
         if (closed) {
            m_sb.append('/');
         } else {
            m_level++;
         }
   
         m_sb.append(">\r\n");
      }
   }

   @SuppressWarnings("unchecked")
   protected String toString(Object value) {
      if (value instanceof String) {
         return (String) value;
      } else if (value instanceof Collection) {
         Collection<Object> list = (Collection<Object>) value;
         StringBuilder sb = new StringBuilder(32);
         boolean first = true;

         for (Object item : list) {
            if (first) {
               first = false;
            } else {
               sb.append(',');
            }

            if (item != null) {
               sb.append(item);
            }
         }

         return sb.toString();
      } else if (value.getClass().isArray()) {
         int len = Array.getLength(value);
         StringBuilder sb = new StringBuilder(32);
         boolean first = true;

         for (int i = 0; i < len; i++) {
            Object item = Array.get(value, i);

            if (first) {
               first = false;
            } else {
               sb.append(',');
            }

            if (item != null) {
               sb.append(item);
            }
         }
		
         return sb.toString();
      }
 
      return String.valueOf(value);
   }

   protected void tagWithText(String name, String text, Object... nameValues) {
      if (text == null) {
         return;
      }
      
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      m_sb.append(">");
      m_sb.append(escape(text, true));
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected void element(String name, String text, String defaultValue, boolean escape) {
      if (text == null || text.equals(defaultValue)) {
         return;
      }
      
      indent();
      
      m_sb.append('<').append(name).append(">");
      
      if (escape) {
         m_sb.append(escape(text, true));
      } else {
         m_sb.append("<![CDATA[").append(text).append("]]>");
      }
      
      m_sb.append("</").append(name).append(">\r\n");
   }

   @Override
   public void visitConfig(ServerConfig config) {
      startTag(ENTITY_CONFIG, null, ATTR_LOCAL_MODE, config.getLocalMode(), ATTR_JOB_MACHINE, config.getJobMachine(), ATTR_ALERT_MACHINE, config.getAlertMachine(), ATTR_HDFS_MACHINE, config.getHdfsMachine(), ATTR_SEND_MACHINE, config.getSendMachine());

      if (config.getStorage() != null) {
         config.getStorage().accept(m_visitor);
      }

      if (config.getConsumer() != null) {
         config.getConsumer().accept(m_visitor);
      }

      if (config.getConsole() != null) {
         config.getConsole().accept(m_visitor);
      }

      if (config.getLdap() != null) {
         config.getLdap().accept(m_visitor);
      }

      if (!config.getProperties().isEmpty()) {
         for (Property property : config.getProperties().values()) {
            property.accept(m_visitor);
         }
      }

      endTag(ENTITY_CONFIG);
   }

   @Override
   public void visitConsole(ConsoleConfig console) {
      startTag(ENTITY_CONSOLE, null, ATTR_DEFAULT_DOMAIN, console.getDefaultDomain(), ATTR_SHOW_CAT_DOMAIN, console.getShowCatDomain());

      element(ELEMENT_REMOTE_SERVERS, console.getRemoteServers(), null,  true);

      endTag(ENTITY_CONSOLE);
   }

   @Override
   public void visitConsumer(ConsumerConfig consumer) {
      startTag(ENTITY_CONSUMER, null);

      if (consumer.getLongConfig() != null) {
         consumer.getLongConfig().accept(m_visitor);
      }

      endTag(ENTITY_CONSUMER);
   }

   @Override
   public void visitDomain(Domain domain) {
      startTag(ENTITY_DOMAIN, true, null, ATTR_NAME, domain.getName(), ATTR_URL_THRESHOLD, domain.getUrlThreshold(), ATTR_SQL_THRESHOLD, domain.getSqlThreshold(), ATTR_SERVICE_THRESHOLD, domain.getServiceThreshold());
   }

   @Override
   public void visitHdfs(HdfsConfig hdfs) {
      startTag(ENTITY_HDFS, true, null, ATTR_ID, hdfs.getId(), ATTR_MAX_SIZE, hdfs.getMaxSize(), ATTR_SERVER_URI, hdfs.getServerUri(), ATTR_BASE_DIR, hdfs.getBaseDir());
   }

   @Override
   public void visitLdap(Ldap ldap) {
      startTag(ENTITY_LDAP, true, null, ATTR_LDAPURL, ldap.getLdapUrl());
   }

   @Override
   public void visitLongConfig(LongConfig longConfig) {
      startTag(ENTITY_LONG_CONFIG, null, ATTR_DEFAULT_URL_THRESHOLD, longConfig.getDefaultUrlThreshold(), ATTR_DEFAULT_SQL_THRESHOLD, longConfig.getDefaultSqlThreshold(), ATTR_DEFAULT_SERVICE_THRESHOLD, longConfig.getDefaultServiceThreshold());

      if (!longConfig.getDomains().isEmpty()) {
         for (Domain domain : longConfig.getDomains().values()) {
            domain.accept(m_visitor);
         }
      }

      endTag(ENTITY_LONG_CONFIG);
   }

   @Override
   public void visitProperty(Property property) {
      startTag(ENTITY_PROPERTY, true, null, ATTR_NAME, property.getName(), ATTR_VALUE, property.getValue());
   }

   @Override
   public void visitStorage(StorageConfig storage) {
      startTag(ENTITY_STORAGE, null, ATTR_LOCAL_BASE_DIR, storage.getLocalBaseDir(), ATTR_HDFS_DISABLED, storage.getHdfsDisabled(), ATTR_UPLOAD_THREAD, storage.getUploadThread(), ATTR_MAX_HDFS_STORAGE_TIME, storage.getMaxHdfsStorageTime(), ATTR_LOCAL_REPORT_STORAGE_TIME, storage.getLocalReportStorageTime(), ATTR_LOCAL_LOGIVEW_STORAGE_TIME, storage.getLocalLogivewStorageTime());

      if (!storage.getHdfses().isEmpty()) {
         for (HdfsConfig hdfs : storage.getHdfses().values()) {
            hdfs.accept(m_visitor);
         }
      }

      if (!storage.getProperties().isEmpty()) {
         startTag(ENTITY_PROPERTIES);

         for (Property property : storage.getProperties().values()) {
            property.accept(m_visitor);
         }

         endTag(ENTITY_PROPERTIES);
      }

      endTag(ENTITY_STORAGE);
   }
}
