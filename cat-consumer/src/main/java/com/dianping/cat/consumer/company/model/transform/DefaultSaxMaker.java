package com.dianping.cat.consumer.company.model.transform;

import static com.dianping.cat.consumer.company.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.company.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.company.model.Constants.ATTR_ORDER;
import static com.dianping.cat.consumer.company.model.Constants.ATTR_TITLE;

import org.xml.sax.Attributes;

import com.dianping.cat.consumer.company.model.entity.Company;
import com.dianping.cat.consumer.company.model.entity.Domain;
import com.dianping.cat.consumer.company.model.entity.ProductLine;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public Company buildCompany(Attributes attributes) {
      String name = attributes.getValue(ATTR_NAME);
      Company company = new Company();

      if (name != null) {
         company.setName(name);
      }

      return company;
   }

   @Override
   public Domain buildDomain(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      Domain domain = new Domain(id);

      return domain;
   }

   @Override
   public ProductLine buildProductLine(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String order = attributes.getValue(ATTR_ORDER);
      String title = attributes.getValue(ATTR_TITLE);
      ProductLine productLine = new ProductLine(id);

      if (order != null) {
         productLine.setOrder(convert(Double.class, order, 0.0));
      }

      if (title != null) {
         productLine.setTitle(title);
      }

      return productLine;
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
}
