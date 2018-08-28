package com.dianping.cat.consumer.matrix.model.transform;

import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_MAX;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_MIN;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_TOTALTIME;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_TYPE;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_URL;

import org.xml.sax.Attributes;

import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public String buildDomain(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Matrix buildMatrix(Attributes attributes) {
      String type = attributes.getValue(ATTR_TYPE);
      String name = attributes.getValue(ATTR_NAME);
      String count = attributes.getValue(ATTR_COUNT);
      String totalTime = attributes.getValue(ATTR_TOTALTIME);
      String url = attributes.getValue(ATTR_URL);
      Matrix matrix = new Matrix(name);

      if (type != null) {
         matrix.setType(type);
      }

      if (count != null) {
         matrix.setCount(convert(Integer.class, count, 0));
      }

      if (totalTime != null) {
         matrix.setTotalTime(convert(Long.class, totalTime, 0L));
      }

      if (url != null) {
         matrix.setUrl(url);
      }

      return matrix;
   }

   @Override
   public MatrixReport buildMatrixReport(Attributes attributes) {
      String domain = attributes.getValue(ATTR_DOMAIN);
      String startTime = attributes.getValue(ATTR_STARTTIME);
      String endTime = attributes.getValue(ATTR_ENDTIME);
      MatrixReport matrixReport = new MatrixReport(domain);

      if (startTime != null) {
         matrixReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         matrixReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return matrixReport;
   }

   @Override
   public Ratio buildRatio(Attributes attributes) {
      String type = attributes.getValue(ATTR_TYPE);
      String min = attributes.getValue(ATTR_MIN);
      String max = attributes.getValue(ATTR_MAX);
      String totalCount = attributes.getValue(ATTR_TOTALCOUNT);
      String totalTime = attributes.getValue(ATTR_TOTALTIME);
      String url = attributes.getValue(ATTR_URL);
      Ratio ratio = new Ratio(type);

      if (min != null) {
         ratio.setMin(convert(Integer.class, min, 0));
      }

      if (max != null) {
         ratio.setMax(convert(Integer.class, max, 0));
      }

      if (totalCount != null) {
         ratio.setTotalCount(convert(Integer.class, totalCount, 0));
      }

      if (totalTime != null) {
         ratio.setTotalTime(convert(Long.class, totalTime, 0L));
      }

      if (url != null) {
         ratio.setUrl(url);
      }

      return ratio;
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
}
