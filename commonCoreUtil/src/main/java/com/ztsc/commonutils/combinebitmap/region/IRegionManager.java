package com.ztsc.commonutils.combinebitmap.region;

import android.graphics.Region;

public interface IRegionManager {
    Region[] calculateRegion(int size, int subSize, int gap, int count);
}
