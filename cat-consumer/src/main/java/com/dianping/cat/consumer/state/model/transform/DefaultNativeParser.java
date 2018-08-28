package com.dianping.cat.consumer.state.model.transform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dianping.cat.consumer.state.model.IVisitor;
import com.dianping.cat.consumer.state.model.entity.Detail;
import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public class DefaultNativeParser implements IVisitor {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DataInputStream m_in;

   public DefaultNativeParser(InputStream in) {
      m_in = new DataInputStream(in);
   }

   public static StateReport parse(byte[] data) {
      return parse(new ByteArrayInputStream(data));
   }

   public static StateReport parse(InputStream in) {
      DefaultNativeParser parser = new DefaultNativeParser(in);
      StateReport stateReport = new StateReport();

      try {
         stateReport.accept(parser);
      } catch (RuntimeException e) {
         if (e.getCause() !=null && e.getCause() instanceof java.io.EOFException) {
            // ignore it
         } else {
            throw e;
         }
      }
      
      parser.m_linker.finish();
      return stateReport;
   }

   @Override
   public void visitDetail(Detail detail) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitDetailChildren(detail, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitDetailChildren(Detail detail, int _field, int _type) {
      switch (_field) {
         case 1:
            detail.setId(readLong());
            break;
         case 2:
            detail.setTotal(readLong());
            break;
         case 3:
            detail.setSize(readDouble());
            break;
         case 4:
            detail.setTotalLoss(readLong());
            break;
      }
   }

   @Override
   public void visitMachine(Machine machine) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitMachineChildren(machine, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitMachineChildren(Machine machine, int _field, int _type) {
      switch (_field) {
         case 1:
            machine.setIp(readString());
            break;
         case 2:
            machine.setTotal(readLong());
            break;
         case 3:
            machine.setTotalLoss(readLong());
            break;
         case 4:
            machine.setMaxTps(readDouble());
            break;
         case 5:
            machine.setAvgTps(readDouble());
            break;
         case 6:
            machine.setBlockTotal(readLong());
            break;
         case 7:
            machine.setBlockLoss(readLong());
            break;
         case 8:
            machine.setBlockTime(readLong());
            break;
         case 9:
            machine.setPigeonTimeError(readLong());
            break;
         case 10:
            machine.setNetworkTimeError(readLong());
            break;
         case 11:
            machine.setDump(readLong());
            break;
         case 12:
            machine.setDumpLoss(readLong());
            break;
         case 13:
            machine.setSize(readDouble());
            break;
         case 14:
            machine.setDelaySum(readDouble());
            break;
         case 15:
            machine.setDelayAvg(readDouble());
            break;
         case 16:
            machine.setDelayCount(readInt());
            break;
         case 33:
            if (_type == 1) { 
              ProcessDomain processDomain = new ProcessDomain();

              visitProcessDomain(processDomain);
              m_linker.onProcessDomain(machine, processDomain);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 ProcessDomain processDomain = new ProcessDomain();

                 visitProcessDomain(processDomain);
                 m_linker.onProcessDomain(machine, processDomain);
               }
            }
            break;
         case 34:
            if (_type == 1) { 
              Message message = new Message();

              visitMessage(message);
              m_linker.onMessage(machine, message);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Message message = new Message();

                 visitMessage(message);
                 m_linker.onMessage(machine, message);
               }
            }
            break;
      }
   }

   @Override
   public void visitMessage(Message message) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitMessageChildren(message, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitMessageChildren(Message message, int _field, int _type) {
      switch (_field) {
         case 1:
            message.setId(readLong());
            break;
         case 2:
            message.setTime(readDate());
            break;
         case 3:
            message.setTotal(readLong());
            break;
         case 4:
            message.setTotalLoss(readLong());
            break;
         case 5:
            message.setDump(readLong());
            break;
         case 6:
            message.setDumpLoss(readLong());
            break;
         case 7:
            message.setSize(readDouble());
            break;
         case 8:
            message.setDelaySum(readDouble());
            break;
         case 9:
            message.setDelayCount(readInt());
            break;
         case 10:
            message.setPigeonTimeError(readLong());
            break;
         case 11:
            message.setNetworkTimeError(readLong());
            break;
         case 12:
            message.setBlockTotal(readLong());
            break;
         case 13:
            message.setBlockLoss(readLong());
            break;
         case 14:
            message.setBlockTime(readLong());
            break;
      }
   }

   @Override
   public void visitProcessDomain(ProcessDomain processDomain) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitProcessDomainChildren(processDomain, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitProcessDomainChildren(ProcessDomain processDomain, int _field, int _type) {
      switch (_field) {
         case 1:
            processDomain.setName(readString());
            break;
         case 2:
            if (_type == 1) {
                  processDomain.addIp(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  processDomain.addIp(readString());
               }
            }
            break;
         case 3:
            processDomain.setTotal(readLong());
            break;
         case 4:
            processDomain.setTotalLoss(readLong());
            break;
         case 5:
            processDomain.setSize(readDouble());
            break;
         case 6:
            processDomain.setAvg(readDouble());
            break;
         case 33:
            if (_type == 1) { 
              Detail detail = new Detail();

              visitDetail(detail);
              m_linker.onDetail(processDomain, detail);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Detail detail = new Detail();

                 visitDetail(detail);
                 m_linker.onDetail(processDomain, detail);
               }
            }
            break;
      }
   }

   @Override
   public void visitStateReport(StateReport stateReport) {
      byte tag;

      if ((tag = readTag()) != -4) {
         throw new RuntimeException(String.format("Malformed payload, expected: %s, but was: %s!", -4, tag));
      }

      while ((tag = readTag()) != -1) {
         visitStateReportChildren(stateReport, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitStateReportChildren(StateReport stateReport, int _field, int _type) {
      switch (_field) {
         case 1:
            stateReport.setDomain(readString());
            break;
         case 2:
            stateReport.setStartTime(readDate());
            break;
         case 3:
            stateReport.setEndTime(readDate());
            break;
         case 33:
            if (_type == 1) { 
              Machine machine = new Machine();

              visitMachine(machine);
              m_linker.onMachine(stateReport, machine);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Machine machine = new Machine();

                 visitMachine(machine);
                 m_linker.onMachine(stateReport, machine);
               }
            }
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

   private long readLong() {
      try {
         return readVarint(64);
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
