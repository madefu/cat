package com.dianping.cat.status.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class DiskInfo extends BaseEntity<DiskInfo> {
   private List<DiskVolumeInfo> m_diskVolumes = new ArrayList<DiskVolumeInfo>();

   public DiskInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDisk(this);
   }

   public DiskInfo addDiskVolume(DiskVolumeInfo diskVolume) {
      m_diskVolumes.add(diskVolume);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof DiskInfo) {
         DiskInfo _o = (DiskInfo) obj;

         if (!equals(getDiskVolumes(), _o.getDiskVolumes())) {
            return false;
         }


         return true;
      }

      return false;
   }

   public DiskVolumeInfo findDiskVolume(String id) {
      for (DiskVolumeInfo diskVolume : m_diskVolumes) {
         if (!equals(diskVolume.getId(), id)) {
            continue;
         }

         return diskVolume;
      }

      return null;
   }

   public List<DiskVolumeInfo> getDiskVolumes() {
      return m_diskVolumes;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      for (DiskVolumeInfo e : m_diskVolumes) {
         hash = hash * 31 + (e == null ? 0 :e.hashCode());
      }


      return hash;
   }

   @Override
   public void mergeAttributes(DiskInfo other) {
   }

   public DiskVolumeInfo removeDiskVolume(String id) {
      int len = m_diskVolumes.size();

      for (int i = 0; i < len; i++) {
         DiskVolumeInfo diskVolume = m_diskVolumes.get(i);

         if (!equals(diskVolume.getId(), id)) {
            continue;
         }

         return m_diskVolumes.remove(i);
      }

      return null;
   }

}
