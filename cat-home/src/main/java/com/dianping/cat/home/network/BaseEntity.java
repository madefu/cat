package com.dianping.cat.home.network;

import java.util.Formattable;
import java.util.FormattableFlags;
import java.util.Formatter;

import com.dianping.cat.home.network.transform.DefaultXmlBuilder;
import com.dianping.cat.home.network.transform.DefaultJsonBuilder;

public abstract class BaseEntity<T> implements IEntity<T>, Formattable {

   public static final String JSON = "%#.3s";

   public static final String JSON_COMPACT = "%#s";

   public static final String XML = "%.3s";
   
   public static final String XML_COMPACT = "%s";
   
   protected void assertAttributeEquals(Object instance, String entityName, String name, Object expectedValue, Object actualValue) {
      if (expectedValue == null && actualValue != null || expectedValue != null && !expectedValue.equals(actualValue)) {
         throw new IllegalArgumentException(String.format("Mismatched entity(%s) found! Same %s attribute is expected! %s: %s.", entityName, name, entityName, instance));
      }
   }

   protected boolean equals(Object o1, Object o2) {
      if (o1 == null) {
         return o2 == null;
      } else if (o2 == null) {
         return false;
      } else {
         return o1.equals(o2);
      }
   }

   @Override
   public void formatTo(Formatter formatter, int flags, int width, int precision) {
      boolean useJson = (flags & FormattableFlags.ALTERNATE) == FormattableFlags.ALTERNATE;
      boolean compact = (precision <= 0);
      
      if (useJson) {
         DefaultJsonBuilder builder = new DefaultJsonBuilder(compact);

         formatter.format("%s", builder.build(this));
      } else {
         DefaultXmlBuilder builder = new DefaultXmlBuilder(compact);

         formatter.format("%s", builder.buildXml(this));
      }
   }

   @Override
   public String toString() {
      return new DefaultXmlBuilder().buildXml(this);
   }
}
