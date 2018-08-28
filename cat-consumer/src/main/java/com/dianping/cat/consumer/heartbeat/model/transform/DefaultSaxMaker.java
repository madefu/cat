package com.dianping.cat.consumer.heartbeat.model.transform;

import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_MESSAGE_OVERFLOW;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_MESSAGE_PRODUCED;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_MESSAGE_SIZE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CLASSPATH;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_DAEMON_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_FREE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_HEAP_USAGE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_HTTP_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_MEMORY_FREE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_MINUTE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_NEW_GC_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_NONE_HEAP_USAGE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_OLD_GC_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_PATH;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_PIGEON_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_SYSTEM_LOAD_AVERAGE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_TOTAL;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_TOTAL_STARTED_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_USABLE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_VALUE;

import java.util.Map;
import org.xml.sax.Attributes;

import com.dianping.cat.consumer.heartbeat.model.entity.Detail;
import com.dianping.cat.consumer.heartbeat.model.entity.Disk;
import com.dianping.cat.consumer.heartbeat.model.entity.Extension;
import com.dianping.cat.consumer.heartbeat.model.entity.HeartbeatReport;
import com.dianping.cat.consumer.heartbeat.model.entity.Machine;
import com.dianping.cat.consumer.heartbeat.model.entity.Period;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public Detail buildDetail(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String value = attributes.getValue(ATTR_VALUE);
      Detail detail = new Detail(id);

      if (value != null) {
         detail.setValue(convert(Double.class, value, 0.0));
      }

      return detail;
   }

   @Override
   public Disk buildDisk(Attributes attributes) {
      String path = attributes.getValue(ATTR_PATH);
      String total = attributes.getValue(ATTR_TOTAL);
      String free = attributes.getValue(ATTR_FREE);
      String usable = attributes.getValue(ATTR_USABLE);
      Disk disk = new Disk(path);

      if (total != null) {
         disk.setTotal(convert(Long.class, total, 0L));
      }

      if (free != null) {
         disk.setFree(convert(Long.class, free, 0L));
      }

      if (usable != null) {
         disk.setUsable(convert(Long.class, usable, 0L));
      }

      return disk;
   }

   @Override
   public String buildDomain(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Extension buildExtension(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      Extension extension = new Extension(id);

      return extension;
   }

   @Override
   public HeartbeatReport buildHeartbeatReport(Attributes attributes) {
      String domain = attributes.getValue(ATTR_DOMAIN);
      String startTime = attributes.getValue(ATTR_STARTTIME);
      String endTime = attributes.getValue(ATTR_ENDTIME);
      HeartbeatReport heartbeatReport = new HeartbeatReport(domain);

      if (startTime != null) {
         heartbeatReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         heartbeatReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return heartbeatReport;
   }

   @Override
   public String buildIp(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Machine buildMachine(Attributes attributes) {
      String ip = attributes.getValue(ATTR_IP);
      String classpath = attributes.getValue(ATTR_CLASSPATH);
      Machine machine = new Machine(ip);

      if (classpath != null) {
         machine.setClasspath(classpath);
      }

      return machine;
   }

   @Override
   public Period buildPeriod(Attributes attributes) {
      String minute = attributes.getValue(ATTR_MINUTE);
      String threadCount = attributes.getValue(ATTR_THREAD_COUNT);
      String daemonCount = attributes.getValue(ATTR_DAEMON_COUNT);
      String totalStartedCount = attributes.getValue(ATTR_TOTAL_STARTED_COUNT);
      String catThreadCount = attributes.getValue(ATTR_CAT_THREAD_COUNT);
      String pigeonThreadCount = attributes.getValue(ATTR_PIGEON_THREAD_COUNT);
      String httpThreadCount = attributes.getValue(ATTR_HTTP_THREAD_COUNT);
      String newGcCount = attributes.getValue(ATTR_NEW_GC_COUNT);
      String oldGcCount = attributes.getValue(ATTR_OLD_GC_COUNT);
      String memoryFree = attributes.getValue(ATTR_MEMORY_FREE);
      String heapUsage = attributes.getValue(ATTR_HEAP_USAGE);
      String noneHeapUsage = attributes.getValue(ATTR_NONE_HEAP_USAGE);
      String systemLoadAverage = attributes.getValue(ATTR_SYSTEM_LOAD_AVERAGE);
      String catMessageProduced = attributes.getValue(ATTR_CAT_MESSAGE_PRODUCED);
      String catMessageOverflow = attributes.getValue(ATTR_CAT_MESSAGE_OVERFLOW);
      String catMessageSize = attributes.getValue(ATTR_CAT_MESSAGE_SIZE);
      Period period = new Period(minute == null ? 0 : convert(Integer.class, minute, 0));

      if (threadCount != null) {
         period.setThreadCount(convert(Integer.class, threadCount, 0));
      }

      if (daemonCount != null) {
         period.setDaemonCount(convert(Integer.class, daemonCount, 0));
      }

      if (totalStartedCount != null) {
         period.setTotalStartedCount(convert(Integer.class, totalStartedCount, 0));
      }

      if (catThreadCount != null) {
         period.setCatThreadCount(convert(Integer.class, catThreadCount, 0));
      }

      if (pigeonThreadCount != null) {
         period.setPigeonThreadCount(convert(Integer.class, pigeonThreadCount, 0));
      }

      if (httpThreadCount != null) {
         period.setHttpThreadCount(convert(Integer.class, httpThreadCount, 0));
      }

      if (newGcCount != null) {
         period.setNewGcCount(convert(Long.class, newGcCount, 0L));
      }

      if (oldGcCount != null) {
         period.setOldGcCount(convert(Long.class, oldGcCount, 0L));
      }

      if (memoryFree != null) {
         period.setMemoryFree(convert(Long.class, memoryFree, 0L));
      }

      if (heapUsage != null) {
         period.setHeapUsage(convert(Long.class, heapUsage, 0L));
      }

      if (noneHeapUsage != null) {
         period.setNoneHeapUsage(convert(Long.class, noneHeapUsage, 0L));
      }

      if (systemLoadAverage != null) {
         period.setSystemLoadAverage(toNumber(systemLoadAverage, "0.00", 0).doubleValue());
      }

      if (catMessageProduced != null) {
         period.setCatMessageProduced(convert(Long.class, catMessageProduced, 0L));
      }

      if (catMessageOverflow != null) {
         period.setCatMessageOverflow(convert(Long.class, catMessageOverflow, 0L));
      }

      if (catMessageSize != null) {
         period.setCatMessageSize(toNumber(catMessageSize, "0.00", 0).doubleValue());
      }

      Map<String, String> dynamicAttributes = period.getDynamicAttributes();
      int _length = attributes == null ? 0 : attributes.getLength();

      for (int i = 0; i < _length; i++) {
         String _name = attributes.getQName(i);
         String _value = attributes.getValue(i);

         dynamicAttributes.put(_name, _value);
      }

      dynamicAttributes.remove(ATTR_MINUTE);
      dynamicAttributes.remove(ATTR_THREAD_COUNT);
      dynamicAttributes.remove(ATTR_DAEMON_COUNT);
      dynamicAttributes.remove(ATTR_TOTAL_STARTED_COUNT);
      dynamicAttributes.remove(ATTR_CAT_THREAD_COUNT);
      dynamicAttributes.remove(ATTR_PIGEON_THREAD_COUNT);
      dynamicAttributes.remove(ATTR_HTTP_THREAD_COUNT);
      dynamicAttributes.remove(ATTR_NEW_GC_COUNT);
      dynamicAttributes.remove(ATTR_OLD_GC_COUNT);
      dynamicAttributes.remove(ATTR_MEMORY_FREE);
      dynamicAttributes.remove(ATTR_HEAP_USAGE);
      dynamicAttributes.remove(ATTR_NONE_HEAP_USAGE);
      dynamicAttributes.remove(ATTR_SYSTEM_LOAD_AVERAGE);
      dynamicAttributes.remove(ATTR_CAT_MESSAGE_PRODUCED);
      dynamicAttributes.remove(ATTR_CAT_MESSAGE_OVERFLOW);
      dynamicAttributes.remove(ATTR_CAT_MESSAGE_SIZE);

      return period;
   }

   @SuppressWarnings("unchecked")
   protected <T> T convert(Class<T> type, String value, T defaultValue) {
      if (value == null || value.length() == 0) {
         return defaultValue;
      }

      if (type == Boolean.class || type == Boolean.TYPE) {
         return (T) Boolean.valueOf(value);
      } else if (type == Integer.class || type == Integer.TYPE) {
         return (T) Integer.valueOf(value);
      } else if (type == Long.class || type == Long.TYPE) {
         return (T) Long.valueOf(value);
      } else if (type == Short.class || type == Short.TYPE) {
         return (T) Short.valueOf(value);
      } else if (type == Float.class || type == Float.TYPE) {
         return (T) Float.valueOf(value);
      } else if (type == Double.class || type == Double.TYPE) {
         return (T) Double.valueOf(value);
      } else if (type == Byte.class || type == Byte.TYPE) {
         return (T) Byte.valueOf(value);
      } else if (type == Character.class || type == Character.TYPE) {
         return (T) (Character) value.charAt(0);
      } else {
         return (T) value;
      }
   }

   protected java.util.Date toDate(String str, String format, java.util.Date defaultValue) {
      if (str == null || str.length() == 0) {
         return defaultValue;
      }

      try {
         return new java.text.SimpleDateFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse date(%s) in format(%s)!", str, format), e);
      }
   }

   protected Number toNumber(String str, String format, Number defaultValue) {
      if (str == null || str.length() == 0) {
         return defaultValue;
      }

      try {
         return new java.text.DecimalFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse number(%s) in format(%s)!", str, format), e);
      }
   }
}
