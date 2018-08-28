package com.dianping.cat.configuration.app.transform;

import static com.dianping.cat.configuration.app.Constants.ENTITY_APP_CONFIG;
import static com.dianping.cat.configuration.app.Constants.ENTITY_CODE;
import static com.dianping.cat.configuration.app.Constants.ENTITY_COMMAND;
import static com.dianping.cat.configuration.app.Constants.ENTITY_CONFIG_ITEM;
import static com.dianping.cat.configuration.app.Constants.ENTITY_ITEM;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.dianping.cat.configuration.app.IEntity;
import com.dianping.cat.configuration.app.entity.AppConfig;
import com.dianping.cat.configuration.app.entity.Code;
import com.dianping.cat.configuration.app.entity.Command;
import com.dianping.cat.configuration.app.entity.ConfigItem;
import com.dianping.cat.configuration.app.entity.Item;

public class DefaultSaxParser extends DefaultHandler {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DefaultSaxMaker m_maker = new DefaultSaxMaker();

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private IEntity<?> m_entity;

   private StringBuilder m_text = new StringBuilder();

   public static AppConfig parse(InputStream in) throws SAXException, IOException {
      return parseEntity(AppConfig.class, new InputSource(removeBOM(in)));
   }

   public static AppConfig parse(Reader reader) throws SAXException, IOException {
      return parseEntity(AppConfig.class, new InputSource(removeBOM(reader)));
   }

   public static AppConfig parse(String xml) throws SAXException, IOException {
      return parseEntity(AppConfig.class, new InputSource(new StringReader(removeBOM(xml))));
   }

   @SuppressWarnings("unchecked")
   private static <T extends IEntity<?>> T parseEntity(Class<T> type, InputSource is) throws SAXException, IOException {
      try {
         DefaultSaxParser handler = new DefaultSaxParser();
         SAXParserFactory factory = SAXParserFactory.newInstance();

         factory.setValidating(false);
         factory.setFeature("http://xml.org/sax/features/validation", false);

         factory.newSAXParser().parse(is, handler);
         return (T) handler.getEntity();
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException("Unable to get SAX parser instance!", e);
      }
   }

   public static <T extends IEntity<?>> T parseEntity(Class<T> type, InputStream in) throws SAXException, IOException {
      return parseEntity(type, new InputSource(removeBOM(in)));
   }

   public static <T extends IEntity<?>> T parseEntity(Class<T> type, String xml) throws SAXException, IOException {
      return parseEntity(type, new InputSource(new StringReader(removeBOM(xml))));
   }

   // to remove Byte Order Mark(BOM) at the head of windows utf-8 file
   @SuppressWarnings("unchecked")
   private static <T> T removeBOM(T obj) throws IOException {
      if (obj instanceof String) {
         String str = (String) obj;

         if (str.length() != 0 && str.charAt(0) == 0xFEFF) {
            return (T) str.substring(1);
         } else {
            return obj;
         }
      } else if (obj instanceof InputStream) {
         BufferedInputStream in = new BufferedInputStream((InputStream) obj);

         in.mark(3);

         if (in.read() != 0xEF || in.read() != 0xBB || in.read() != 0xBF) {
            in.reset();
         }

         return (T) in;
      } else if (obj instanceof Reader) {
         BufferedReader in = new BufferedReader((Reader) obj);

         in.mark(1);

         if (in.read() != 0xFEFF) {
            in.reset();
         }

         return (T) in;
      } else {
         return obj;
      }
   }

   @SuppressWarnings("unchecked")
   protected <T> T convert(Class<T> type, String value, T defaultValue) {
      if (value == null || value.length() == 0) {
         return defaultValue;
      }

      if (type == Boolean.class) {
         return (T) Boolean.valueOf(value);
      } else if (type == Integer.class) {
         return (T) Integer.valueOf(value);
      } else if (type == Long.class) {
         return (T) Long.valueOf(value);
      } else if (type == Short.class) {
         return (T) Short.valueOf(value);
      } else if (type == Float.class) {
         return (T) Float.valueOf(value);
      } else if (type == Double.class) {
         return (T) Double.valueOf(value);
      } else if (type == Byte.class) {
         return (T) Byte.valueOf(value);
      } else if (type == Character.class) {
         return (T) (Character) value.charAt(0);
      } else {
         return (T) value;
      }
   }

   @Override
   public void characters(char[] ch, int start, int length) throws SAXException {
      m_text.append(ch, start, length);
   }

   @Override
   public void endDocument() throws SAXException {
      m_linker.finish();
   }

   @Override
   public void endElement(String uri, String localName, String qName) throws SAXException {
      if (uri == null || uri.length() == 0) {
         m_objs.pop();
         m_tags.pop();

      }

      m_text.setLength(0);
   }

   private IEntity<?> getEntity() {
      return m_entity;
   }

   protected String getText() {
      return m_text.toString();
   }

   private void parseForAppConfig(AppConfig parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_CONFIG_ITEM.equals(qName)) {
         ConfigItem configItem = m_maker.buildConfigItem(attributes);

         m_linker.onConfigItem(parentObj, configItem);
         m_objs.push(configItem);
      } else if (ENTITY_CODE.equals(qName)) {
         Code code = m_maker.buildCode(attributes);

         m_linker.onCode(parentObj, code);
         m_objs.push(code);
      } else if (ENTITY_COMMAND.equals(qName)) {
         Command command = m_maker.buildCommand(attributes);

         m_linker.onCommand(parentObj, command);
         m_objs.push(command);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under app-config!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForCode(Code parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseForCommand(Command parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_CODE.equals(qName)) {
         Code code = m_maker.buildCode(attributes);

         m_linker.onCode(parentObj, code);
         m_objs.push(code);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under command!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForConfigItem(ConfigItem parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_ITEM.equals(qName)) {
         Item item = m_maker.buildItem(attributes);

         m_linker.onItem(parentObj, item);
         m_objs.push(item);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under config-item!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForItem(Item parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseRoot(String qName, Attributes attributes) throws SAXException {
      if (ENTITY_APP_CONFIG.equals(qName)) {
         AppConfig appConfig = m_maker.buildAppConfig(attributes);

         m_entity = appConfig;
         m_objs.push(appConfig);
         m_tags.push(qName);
      } else if (ENTITY_CONFIG_ITEM.equals(qName)) {
         ConfigItem configItem = m_maker.buildConfigItem(attributes);

         m_entity = configItem;
         m_objs.push(configItem);
         m_tags.push(qName);
      } else if (ENTITY_ITEM.equals(qName)) {
         Item item = m_maker.buildItem(attributes);

         m_entity = item;
         m_objs.push(item);
         m_tags.push(qName);
      } else if (ENTITY_COMMAND.equals(qName)) {
         Command command = m_maker.buildCommand(attributes);

         m_entity = command;
         m_objs.push(command);
         m_tags.push(qName);
      } else if (ENTITY_CODE.equals(qName)) {
         Code code = m_maker.buildCode(attributes);

         m_entity = code;
         m_objs.push(code);
         m_tags.push(qName);
      } else {
         throw new SAXException("Unknown root element(" + qName + ") found!");
      }
   }

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (uri == null || uri.length() == 0) {
         if (m_objs.isEmpty()) { // root
            parseRoot(qName, attributes);
         } else {
            Object parent = m_objs.peek();
            String tag = m_tags.peek();

            if (parent instanceof AppConfig) {
               parseForAppConfig((AppConfig) parent, tag, qName, attributes);
            } else if (parent instanceof ConfigItem) {
               parseForConfigItem((ConfigItem) parent, tag, qName, attributes);
            } else if (parent instanceof Item) {
               parseForItem((Item) parent, tag, qName, attributes);
            } else if (parent instanceof Command) {
               parseForCommand((Command) parent, tag, qName, attributes);
            } else if (parent instanceof Code) {
               parseForCode((Code) parent, tag, qName, attributes);
            } else {
               throw new RuntimeException(String.format("Unknown entity(%s) under %s!", qName, parent.getClass().getName()));
            }
         }

         m_text.setLength(0);
        } else {
         throw new SAXException(String.format("Namespace(%s) is not supported by %s.", uri, this.getClass().getName()));
      }
   }
}
