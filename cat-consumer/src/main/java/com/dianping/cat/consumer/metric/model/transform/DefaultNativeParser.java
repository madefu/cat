package com.dianping.cat.consumer.metric.model.transform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dianping.cat.consumer.metric.model.IVisitor;
import com.dianping.cat.consumer.metric.model.entity.Abtest;
import com.dianping.cat.consumer.metric.model.entity.Group;
import com.dianping.cat.consumer.metric.model.entity.MetricItem;
import com.dianping.cat.consumer.metric.model.entity.MetricReport;
import com.dianping.cat.consumer.metric.model.entity.Point;
import com.dianping.cat.consumer.metric.model.entity.Segment;
import com.dianping.cat.consumer.metric.model.entity.Statistic;
import com.dianping.cat.consumer.metric.model.entity.StatisticsItem;

public class DefaultNativeParser implements IVisitor {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DataInputStream m_in;

   public DefaultNativeParser(InputStream in) {
      m_in = new DataInputStream(in);
   }

   public static MetricReport parse(byte[] data) {
      return parse(new ByteArrayInputStream(data));
   }

   public static MetricReport parse(InputStream in) {
      DefaultNativeParser parser = new DefaultNativeParser(in);
      MetricReport metricReport = new MetricReport();

      try {
         metricReport.accept(parser);
      } catch (RuntimeException e) {
         if (e.getCause() !=null && e.getCause() instanceof java.io.EOFException) {
            // ignore it
         } else {
            throw e;
         }
      }
      
      parser.m_linker.finish();
      return metricReport;
   }

   @Override
   public void visitAbtest(Abtest abtest) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitAbtestChildren(abtest, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitAbtestChildren(Abtest abtest, int _field, int _type) {
      switch (_field) {
         case 1:
            abtest.setRunId(readString());
            break;
         case 2:
            abtest.setName(readString());
            break;
         case 33:
            if (_type == 1) { 
              Group group = new Group();

              visitGroup(group);
              m_linker.onGroup(abtest, group);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Group group = new Group();

                 visitGroup(group);
                 m_linker.onGroup(abtest, group);
               }
            }
            break;
      }
   }

   @Override
   public void visitGroup(Group group) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitGroupChildren(group, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitGroupChildren(Group group, int _field, int _type) {
      switch (_field) {
         case 1:
            group.setName(readString());
            break;
         case 33:
            if (_type == 1) { 
              Point point = new Point();

              visitPoint(point);
              m_linker.onPoint(group, point);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Point point = new Point();

                 visitPoint(point);
                 m_linker.onPoint(group, point);
               }
            }
            break;
      }
   }

   @Override
   public void visitMetricItem(MetricItem metricItem) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitMetricItemChildren(metricItem, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitMetricItemChildren(MetricItem metricItem, int _field, int _type) {
      switch (_field) {
         case 1:
            metricItem.setId(readString());
            break;
         case 2:
            metricItem.setType(readString());
            break;
         case 3:
            if (_type == 1) {
                  metricItem.addDomain(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  metricItem.addDomain(readString());
               }
            }
            break;
         case 33:
            if (_type == 1) { 
              Abtest abtest = new Abtest();

              visitAbtest(abtest);
              m_linker.onAbtest(metricItem, abtest);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Abtest abtest = new Abtest();

                 visitAbtest(abtest);
                 m_linker.onAbtest(metricItem, abtest);
               }
            }
            break;
         case 34:
            if (_type == 1) { 
              Segment segment = new Segment();

              visitSegment(segment);
              m_linker.onSegment(metricItem, segment);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Segment segment = new Segment();

                 visitSegment(segment);
                 m_linker.onSegment(metricItem, segment);
               }
            }
            break;
      }
   }

   @Override
   public void visitMetricReport(MetricReport metricReport) {
      byte tag;

      if ((tag = readTag()) != -4) {
         throw new RuntimeException(String.format("Malformed payload, expected: %s, but was: %s!", -4, tag));
      }

      while ((tag = readTag()) != -1) {
         visitMetricReportChildren(metricReport, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitMetricReportChildren(MetricReport metricReport, int _field, int _type) {
      switch (_field) {
         case 1:
            metricReport.setProduct(readString());
            break;
         case 2:
            metricReport.setStartTime(readDate());
            break;
         case 3:
            metricReport.setEndTime(readDate());
            break;
         case 33:
            if (_type == 1) { 
              MetricItem metricItem = new MetricItem();

              visitMetricItem(metricItem);
              m_linker.onMetricItem(metricReport, metricItem);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 MetricItem metricItem = new MetricItem();

                 visitMetricItem(metricItem);
                 m_linker.onMetricItem(metricReport, metricItem);
               }
            }
            break;
         case 34:
            if (_type == 1) { 
              Statistic statistic = new Statistic();

              visitStatistic(statistic);
              m_linker.onStatistic(metricReport, statistic);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Statistic statistic = new Statistic();

                 visitStatistic(statistic);
                 m_linker.onStatistic(metricReport, statistic);
               }
            }
            break;
      }
   }

   @Override
   public void visitPoint(Point point) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitPointChildren(point, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitPointChildren(Point point, int _field, int _type) {
      switch (_field) {
         case 1:
            point.setId(readInt());
            break;
         case 2:
            point.setCount(readInt());
            break;
         case 3:
            point.setSum(readDouble());
            break;
         case 4:
            point.setAvg(readDouble());
            break;
      }
   }

   @Override
   public void visitSegment(Segment segment) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitSegmentChildren(segment, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitSegmentChildren(Segment segment, int _field, int _type) {
      switch (_field) {
         case 1:
            segment.setId(readInt());
            break;
         case 2:
            segment.setCount(readInt());
            break;
         case 3:
            segment.setSum(readDouble());
            break;
         case 4:
            segment.setAvg(readDouble());
            break;
      }
   }

   @Override
   public void visitStatistic(Statistic statistic) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitStatisticChildren(statistic, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitStatisticChildren(Statistic statistic, int _field, int _type) {
      switch (_field) {
         case 1:
            statistic.setId(readString());
            break;
         case 33:
            if (_type == 1) { 
              StatisticsItem statisticsItem = new StatisticsItem();

              visitStatisticsItem(statisticsItem);
              m_linker.onStatisticsItem(statistic, statisticsItem);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 StatisticsItem statisticsItem = new StatisticsItem();

                 visitStatisticsItem(statisticsItem);
                 m_linker.onStatisticsItem(statistic, statisticsItem);
               }
            }
            break;
      }
   }

   @Override
   public void visitStatisticsItem(StatisticsItem statisticsItem) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitStatisticsItemChildren(statisticsItem, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitStatisticsItemChildren(StatisticsItem statisticsItem, int _field, int _type) {
      switch (_field) {
         case 1:
            statisticsItem.setId(readString());
            break;
         case 2:
            statisticsItem.setCount(readInt());
            break;
      }
   }

   private java.util.Date readDate() {
      try {
         return new java.util.Date(readVarint(64));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private double readDouble() {
      try {
         return Double.longBitsToDouble(readVarint(64));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private int readInt() {
      try {
         return (int) readVarint(32);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private String readString() {
      try {
         return m_in.readUTF();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private byte readTag() {
      try {
         return m_in.readByte();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   protected long readVarint(final int length) throws IOException {
      int shift = 0;
      long result = 0;

      while (shift < length) {
         final byte b = m_in.readByte();
         result |= (long) (b & 0x7F) << shift;
         if ((b & 0x80) == 0) {
            return result;
         }
         shift += 7;
      }

      throw new RuntimeException("Malformed variable int " + length + "!");
   }
}
