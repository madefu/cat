package com.dianping.cat.consumer.transaction.model;

import java.util.Formattable;
import java.util.Formatter;

import com.dianping.cat.consumer.transaction.model.transform.DefaultXmlBuilder;

public abstract class BaseEntity<T> implements IEntity<T>, Formattable {

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
      boolean compact = (precision == 0);
      DefaultXmlBuilder builder = new DefaultXmlBuilder(compact);

      formatter.format("%s", builder.buildXml(this));
   }

   @Override
   public String toString() {
      return new DefaultXmlBuilder().buildXml(this);
   }
}
