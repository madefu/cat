package com.dianping.cat.home.bug;

public interface IEntity<T> {
   public void accept(IVisitor visitor);

   public void mergeAttributes(T other);

}
