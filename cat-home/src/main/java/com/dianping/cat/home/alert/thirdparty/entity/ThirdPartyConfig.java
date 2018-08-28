package com.dianping.cat.home.alert.thirdparty.entity;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.home.alert.thirdparty.BaseEntity;
import com.dianping.cat.home.alert.thirdparty.IVisitor;

public class ThirdPartyConfig extends BaseEntity<ThirdPartyConfig> {
   private List<Http> m_https = new ArrayList<Http>();

   private List<Socket> m_sockets = new ArrayList<Socket>();

   public ThirdPartyConfig() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitThirdPartyConfig(this);
   }

   public ThirdPartyConfig addHttp(Http http) {
      m_https.add(http);
      return this;
   }

   public ThirdPartyConfig addSocket(Socket socket) {
      m_sockets.add(socket);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof ThirdPartyConfig) {
         ThirdPartyConfig _o = (ThirdPartyConfig) obj;

         if (!equals(getHttps(), _o.getHttps())) {
            return false;
         }

         if (!equals(getSockets(), _o.getSockets())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public List<Http> getHttps() {
      return m_https;
   }

   public List<Socket> getSockets() {
      return m_sockets;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      for (Http e : m_https) {
         hash = hash * 31 + (e == null ? 0 :e.hashCode());
      }

      for (Socket e : m_sockets) {
         hash = hash * 31 + (e == null ? 0 :e.hashCode());
      }


      return hash;
   }

   @Override
   public void mergeAttributes(ThirdPartyConfig other) {
   }

}
