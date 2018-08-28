package com.dianping.cat.consumer.top.model.transform;

import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_SEGMENT;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_TOP_REPORT;

import java.lang.reflect.Array;
import java.util.Collection;

import com.dianping.cat.consumer.top.model.IEntity;
import com.dianping.cat.consumer.top.model.IVisitor;
import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Error;
import com.dianping.cat.consumer.top.model.entity.Machine;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

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

   protected String toString(java.util.Date date, String format) {
      if (date != null) {
         return new java.text.SimpleDateFormat(format).format(date);
      } else {
         return null;
      }
   }

   protected String toString(Number number, String format) {
      if (number != null) {
         return new java.text.DecimalFormat(format).format(number);
      } else {
         return null;
      }
   }

   @Override
   public void visitDomain(Domain domain) {
      startTag(ENTITY_DOMAIN, null, ATTR_NAME, domain.getName());

      if (!domain.getSegments().isEmpty()) {
         for (Segment segment : domain.getSegments().values()) {
            segment.accept(m_visitor);
         }
      }

      endTag(ENTITY_DOMAIN);
   }

   @Override
   public void visitError(Error error) {
      startTag(ENTITY_ERROR, true, null, ATTR_ID, error.getId(), ATTR_COUNT, error.getCount());
   }

   @Override
   public void visitMachine(Machine machine) {
      startTag(ENTITY_MACHINE, true, null, ATTR_ID, machine.getId(), ATTR_COUNT, machine.getCount());
   }

   @Override
   public void visitSegment(Segment segment) {
      startTag(ENTITY_SEGMENT, null, ATTR_ID, segment.getId(), ATTR_ERROR, segment.getError(), ATTR_URL, segment.getUrl(), ATTR_URL_DURATION, toString(segment.getUrlDuration(), "0.00"), ATTR_SERVICE, segment.getService(), ATTR_SERVICE_DURATION, toString(segment.getServiceDuration(), "0.00"), ATTR_SQL, segment.getSql(), ATTR_SQL_DURATION, toString(segment.getSqlDuration(), "0.00"), ATTR_CALL, segment.getCall(), ATTR_CALL_DURATION, toString(segment.getCallDuration(), "0.00"), ATTR_CACHE, segment.getCache(), ATTR_CACHE_DURATION, toString(segment.getCacheDuration(), "0.00"), ATTR_URL_ERROR, segment.getUrlError(), ATTR_URL_SUM, toString(segment.getUrlSum(), "0.00"), ATTR_SERVICE_ERROR, segment.getServiceError(), ATTR_SERVICE_SUM, toString(segment.getServiceSum(), "0.00"), ATTR_SQL_ERROR, segment.getSqlError(), ATTR_SQL_SUM, toString(segment.getSqlSum(), "0.00"), ATTR_CALL_ERROR, segment.getCallError(), ATTR_CALL_SUM, toString(segment.getCallSum(), "0.00"), ATTR_CACHE_ERROR, segment.getCacheError(), ATTR_CACHE_SUM, toString(segment.getCacheSum(), "0.00"));

      if (!segment.getErrors().isEmpty()) {
         for (Error error : segment.getErrors().values()) {
            error.accept(m_visitor);
         }
      }

      if (!segment.getMachines().isEmpty()) {
         for (Machine machine : segment.getMachines().values()) {
            machine.accept(m_visitor);
         }
      }

      endTag(ENTITY_SEGMENT);
   }

   @Override
   public void visitTopReport(TopReport topReport) {
      startTag(ENTITY_TOP_REPORT, null, ATTR_DOMAIN, topReport.getDomain(), ATTR_STARTTIME, toString(topReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(topReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!topReport.getDomains().isEmpty()) {
         for (Domain domain : topReport.getDomains().values()) {
            domain.accept(m_visitor);
         }
      }

      endTag(ENTITY_TOP_REPORT);
   }
}
